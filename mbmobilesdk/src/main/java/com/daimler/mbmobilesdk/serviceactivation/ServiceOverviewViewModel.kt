package com.daimler.mbmobilesdk.serviceactivation

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.app.format
import com.daimler.mbmobilesdk.featuretoggling.FLAG_IDENTITY_CHECK_MODULE
import com.daimler.mbmobilesdk.featuretoggling.isFeatureToggleEnabled
import com.daimler.mbmobilesdk.serviceactivation.BaseServiceItem.Companion.TYPE_DEFAULT
import com.daimler.mbmobilesdk.serviceactivation.BaseServiceItem.Companion.TYPE_PURCHASE
import com.daimler.mbmobilesdk.socket.MyCarSocketAndroidViewModel
import com.daimler.mbmobilesdk.utils.extensions.*
import com.daimler.mbmobilesdk.vehicleselection.VehicleImageLoader
import com.daimler.mbcommonkit.extensions.replace
import com.daimler.mbcommonkit.extensions.replaceAt
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.services.*
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbcarkit.business.model.vehicle.image.Degrees
import com.daimler.mbcarkit.socket.observable.ServiceActivationObserver
import com.daimler.mbcarkit.socket.observable.VehicleObserver
import com.daimler.mbnetworkkit.socket.message.Observables
import com.daimler.mbnetworkkit.socket.message.dispose
import com.daimler.mbnetworkkit.socket.message.observe
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.eventbus.EventBus
import com.daimler.mbuikit.eventbus.Observes
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.getString
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf
import java.util.*

internal class ServiceOverviewViewModel(
    app: Application,
    private var vehicle: VehicleInfo,
    initialView: Boolean
) : MyCarSocketAndroidViewModel(app) {

    private val services = mutableListOf<Service>()

    val progressVisible = MutableLiveData<Boolean>()
    val activateServicesButtonVisible = mutableLiveDataOf(false)
    val readyButtonVisible = initialView
    val serviceItems = MutableLiveArrayList<BaseServiceItem>()

    val carTitle = mutableLiveDataOf(vehicle.model)
    val licensePlate = mutableLiveDataOf(vehicle.licensePlate)
    val carFin = mutableLiveDataOf(vehicle.finOrVin)
    val carImage = MutableLiveData<Bitmap>()
    val showFallbackImage = mutableLiveDataOf(false)

    // TODO error messages
    val serviceSelectedEvent = MutableLiveEvent<ServiceSelectionEvent>()
    val serviceNotChangeableEvent = MutableLiveEvent<String>()
    val purchaseServiceEvent = MutableLiveEvent<String>()
    val servicesNotAvailableError = MutableLiveEvent<String>()
    val serviceDesireError = MutableLiveEvent<String>()
    val finishEvent = MutableLiveUnitEvent()
    val errorEvent = MutableLiveEvent<String>()
    val showPreconditionsEvent = MutableLiveEvent<PreconditionsEvent>()
    val showAgreementsEvent = MutableLiveUnitEvent()
    val startJumioEvent = MutableLiveEvent<StartJumioEvent>()

    var jumioResult = false

    val servicesLoaded: Boolean get() = serviceItems.value.isNotEmpty()

    private val serviceSocketObserver =
        ServiceActivationObserver.ServiceStatus { onServicesChanged(it) }
    private val vehicleUpdateObserver =
        VehicleObserver.VehicleUpdate { onVehiclesChanged() }

    init {
        loadCarImage()
        EventBus.createStation(this)
        connect()
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.dismount(this)
        serviceItems.value.forEach { it.destroy() }
    }

    override fun onRegisterObservers(observables: Observables) {
        observables.observe(serviceSocketObserver)
        observables.observe(vehicleUpdateObserver)
    }

    override fun onDisposeObservers(observables: Observables) {
        observables.dispose(serviceSocketObserver)
        observables.dispose(vehicleUpdateObserver)
    }

    fun loadItems() {
        progressVisible.postValue(true)
        fetchServices()
    }

    fun onActivateAllClicked() {
        val items = services
        if (items.isNotEmpty()) {
            // TODO user locale?
            val locale = MBMobileSDK.userLocale()
            progressVisible.postValue(true)
            SoeAcceptanceCheck.areSoeAccepted(locale.format(), locale.countryCode)
                .onComplete { accepted ->
                    if (accepted) {
                        if (!isNeedToShowJumio()) {
                            activateAllServices()
                        } else {
                            startJumioEvent.sendEvent(StartJumioEvent())
                        }
                    } else {
                        showAgreementsEvent.sendEvent()
                    }
                }.onFailure {
                    MBLoggerKit.re("Failed to check for SOE acceptance.", it)
                    errorEvent.sendEvent(defaultErrorMessage(it))
                }.onAlways { _, _, _ -> progressVisible.postValue(false) }
        }
    }

    private fun isNeedToShowJumio() = // check trust level and call jumio if it's too small
        vehicle.trustLevel < TRUST_LEVEL_NEEDED && !jumioResult &&
            isFeatureToggleEnabled(FLAG_IDENTITY_CHECK_MODULE)

    private fun activateAllServices() {
        // request activation for all possible services
        val servicesToActivate = services
            .filter { it.isActivationAllowed() }
            .map { ServiceStatusDesire(it.id, true) }
        if (servicesToActivate.isNotEmpty()) requestServiceStatus(servicesToActivate, true, true)

        // prompt for the completion of missing data
        val pendingPreconditionServices = services
            .filter { it.hasPendingPreconditions() }
        if (pendingPreconditionServices.isNotEmpty()) {
            showPreconditionsEvent.sendEvent(PreconditionsEvent(vehicle.finOrVin,
                pendingPreconditionServices))
        }
    }

    fun onReadyClicked() = finishEvent.sendEvent()

    fun onLicensePlateEntered() {
        val licensePlate = licensePlate.value
        if (licensePlate == vehicle.licensePlate) return

        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBCarKit.vehicleService().updateLicensePlate(jwt,
                    Locale.getDefault().country, vehicle.finOrVin, licensePlate.orEmpty())
                    .onComplete { MBLoggerKit.d("Updated license plate to $licensePlate.") }
                    .onFailure { MBLoggerKit.re("Failed to update license plate.", it) }
            }.onFailure {
                MBLoggerKit.e("Failed to refresh token.", throwable = it)
                errorEvent.sendEvent(defaultErrorMessage(it))
            }
    }

    private fun loadCarImage() {
        VehicleImageLoader(vehicle.finOrVin).loadDefault(Degrees.DEGREES_40)
            .onComplete { images ->
                images.firstOrNull()?.let { image ->
                    image.imageBytes?.let {
                        showFallbackImage.postValue(false)
                        carImage.postValue(BitmapFactory.decodeByteArray(it, 0, it.size))
                    }
                }
            }
            .onFailure {
                MBLoggerKit.re("Could not load vehicle image.", it)
                showFallbackImage.postValue(true)
            }
    }

    private fun getServiceHint(service: Service): String? =
        when {
            service.isActivationPending() -> getString(R.string.activate_services_pending_active)
            service.isDeactivationPending() -> getString(R.string.activate_services_pending_inactive)
            service.allowedActions.contains(ServiceAction.PURCHASE_LICENSE) -> getString(R.string.activate_services_purchase_license)
            service.allowedActions.contains(ServiceAction.UPDATE_TRUST_LEVEL) -> getString(R.string.activate_services_trust_level_insufficient)
            service.allowedActions.size > 1 -> getString(R.string.activate_services_data_missing)
            else -> null
        }

    private fun fetchServices() {
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBCarKit.serviceService().fetchServices(jwt, vehicle.finOrVin,
                    ServiceGroupOption.CATEGORY)
                    .onComplete { services ->
                        MBLoggerKit.d("Found ${services.size} service groups.")
                        activateServicesButtonVisible.postValue(true)
                        serviceItems.value.clear()
                        this.services.clear()
                        val tmp = mutableListOf<BaseServiceItem>()
                        services.forEach { group ->
                            tmp.add(ServiceCategoryItem(group.group))
                            tmp.addAll(group.services.map {
                                mapServiceToServiceItem(it)
                            })
                            this.services.addAll(group.services)
                        }
                        serviceItems.addAllAndDispatch(tmp)
                    }.onFailure {
                        MBLoggerKit.re("Could not request services.", it)
                        servicesNotAvailableError.sendEvent(defaultErrorMessage(it))
                    }.onAlways { _, _, _ -> progressVisible.postValue(false) }
            }.onFailure {
                MBLoggerKit.e("Could not refresh token.", throwable = it)
                progressVisible.postValue(false)
            }
    }

    private fun updateServicesForIds(ids: List<Int>) {
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBCarKit.serviceService().fetchServices(jwt, vehicle.finOrVin,
                    ids, ServiceGroupOption.NONE)
                    .onComplete { groups ->
                        groups.forEach { group ->
                            val services = group.services
                            val items = serviceItems.value
                            services.forEach { service ->
                                this.services.replace<Service>(service) { it.id == service.id }
                                val index = items
                                    .indexOfFirst { item -> item.serviceId == service.id }
                                if (index >= 0) {
                                    val item = items[index]
                                    if (hasItemChangedType(item, service)) {
                                        MBLoggerKit.d("Swapping item type for service ${item.serviceId}")
                                        val newItem = mapServiceToServiceItem(service)
                                        items.replaceAt(index, newItem)
                                        serviceItems.postValue(items)
                                    } else {
                                        MBLoggerKit.d("Updating service item with id ${item.serviceId}")
                                        item.updateService(service, service.isActive(),
                                            service.isChangeable(), getServiceHint(service),
                                            service.hasDetails())
                                    }
                                }
                            }
                        }
                    }.onFailure {
                        MBLoggerKit.re("Failed to update services.", it)
                    }
            }.onFailure {
                MBLoggerKit.e("Could not refresh token.", throwable = it)
            }
    }

    private fun requestServiceStatus(
        desires: List<ServiceStatusDesire>,
        showGeneralProgress: Boolean,
        forceToggle: Boolean = false
    ) {
        MBLoggerKit.d("requestServiceStatus: $desires")
        setItemProgress(desires, true, forceToggle)
        if (showGeneralProgress) progressVisible.postValue(true)
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBCarKit.serviceService().requestServiceUpdate(
                    jwt, vehicle.finOrVin, desires
                ).onComplete {
                    MBLoggerKit.d("Updated services: $desires.")
                }.onFailure {
                    MBLoggerKit.re("Could not update service status: $desires.", it)
                    if (desires.size == 1) {
                        toggleItems(desires)
                    }
                }.onAlways { _, _, _ ->
                    setItemProgress(desires, false)
                    if (showGeneralProgress) progressVisible.postValue(false)
                }
            }.onFailure {
                MBLoggerKit.e("Could not refresh token.")
                setItemProgress(desires, false)
                if (showGeneralProgress) progressVisible.postValue(false)
            }
    }

    private fun onVehiclesChanged() {
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                MBCarKit.vehicleService().fetchVehicles(token.jwtToken.plainToken)
                    .onComplete { vehicles ->
                        vehicles.find {
                            it.finOrVin == vehicle.finOrVin
                        }?.let { updateVehicleInformation(it) }
                    }
                    .onFailure {
                        MBLoggerKit.re("Failed to load vehicles.", it)
                    }
            }
            .onFailure {
                MBLoggerKit.e("Failed to refresh token.", throwable = it)
            }
    }

    private fun onServicesChanged(update: ServiceActivationStatusUpdate) {
        val ids = update.updates.map { it.id }
        updateServicesForIds(ids)
    }

    private fun updateVehicleInformation(vehicle: VehicleInfo) {
        this.vehicle = vehicle
        carTitle.postValue(vehicle.model)
        licensePlate.postValue(vehicle.licensePlate)
        carFin.postValue(vehicle.finOrVin)
        loadCarImage()
    }

    private fun setItemProgress(
        desires: List<ServiceStatusDesire>,
        isProcessing: Boolean,
        forceToggle: Boolean = false
    ) {
        val ids = desires.map { it.serviceId }
        serviceItems.value
            .filterIsInstance(ServiceItem::class.java)
            .filter { ids.contains(it.service.id) }
            .forEach {
                it.setProgress(isProcessing)
                if (forceToggle) it.toggle()
            }
    }

    private fun toggleItems(desires: List<ServiceStatusDesire>) {
        val ids = desires.map { it.serviceId }
        serviceItems.value
            .filterIsInstance(ServiceItem::class.java)
            .filter { ids.contains(it.service.id) }
            .forEach { it.toggle() }
    }

    private fun mapServiceToServiceItem(service: Service): BaseServiceItem {
        @BaseServiceItem.ServiceItemType val type = serviceItemType(service)
        return when (type) {
            TYPE_PURCHASE -> ServicePurchaseItem(service, 0, null,
                service.hasDetails())
            else -> ServiceItem(service, 0, service.isActive(), service.isChangeable(),
                getServiceHint(service), service.hasDetails())
        }
    }

    private fun hasItemChangedType(item: BaseServiceItem, service: Service): Boolean =
        item.type != serviceItemType(service)

    @BaseServiceItem.ServiceItemType
    private fun serviceItemType(service: Service): Int =
        when {
            service.needsPurchase() -> TYPE_PURCHASE
            else -> TYPE_DEFAULT
        }

    @Observes
    @Suppress("UNUSED")
    private fun onServiceToggled(event: ServiceToggledEvent) {
        val serviceId = event.item.serviceId
        MBLoggerKit.d("Trying to toggle service with id $serviceId.")
        if (event.isChecked) {
            // check for jumio
            if (!event.item.service.prerequisiteChecks
                    .flatMap { it.actions }
                    .contains(ServiceAction.UPDATE_TRUST_LEVEL) && !jumioResult) {
                // TODO user locale?
                val locale = MBMobileSDK.userLocale()
                SoeAcceptanceCheck.areSoeAccepted(locale.format(), locale.countryCode)
                    .onComplete { accepted ->
                        if (accepted) {
                            requestServiceStatus(listOf(ServiceStatusDesire(serviceId, event.isChecked)), false)
                        } else {
                            event.item.toggle()
                            showAgreementsEvent.sendEvent()
                        }
                    }.onFailure {
                        MBLoggerKit.re("Failed to check for SOE acceptance.", it)
                        event.item.toggle()
                        errorEvent.sendEvent(defaultErrorMessage(it))
                    }
            } else {
                startJumioEvent.sendEvent(StartJumioEvent(event))
            }
        } else {
            requestServiceStatus(listOf(ServiceStatusDesire(serviceId, event.isChecked)), false)
        }
    }

    @Observes
    @Suppress("UNUSED")
    private fun onServiceSelected(event: ServiceSelectedEvent) {
        MBLoggerKit.d("Selected ${event.service}.")
        serviceSelectedEvent.sendEvent(ServiceSelectionEvent(event.service, vehicle))
    }

    @Observes
    @Suppress("UNUSED")
    private fun onPurchaseService(event: PurchaseServiceEvent) {
        MBLoggerKit.d("Purchase ${event.service}.")
        purchaseServiceEvent.sendEvent(event.service.shortName ?: event.service.name)
    }

    fun updateJumioResult(result: Boolean, event: ServiceToggledEvent?) {
        jumioResult = result
        if (jumioResult) {
            if (event != null) onServiceToggled(event)
            else onActivateAllClicked()
        }
    }

    internal data class ServiceSelectionEvent(val service: Service, val vehicle: VehicleInfo)

    internal data class PreconditionsEvent(val finOrVin: String, val services: List<Service>)

    internal data class StartJumioEvent(val event: ServiceToggledEvent? = null)

    companion object {
        const val TRUST_LEVEL_NEEDED = 3
    }
}