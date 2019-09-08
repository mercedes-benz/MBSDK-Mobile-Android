package com.daimler.mbmobilesdk.vehicleselection

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.PagerSnapHelper
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.app.foreground.ForegroundListener
import com.daimler.mbmobilesdk.socket.MyCarSocketAndroidViewModel
import com.daimler.mbmobilesdk.utils.CenteringItemDecoration
import com.daimler.mbmobilesdk.utils.extensions.*
import com.daimler.mbmobilesdk.utils.postToMainThread
import com.daimler.mbmobilesdk.utils.resizeBitmap
import com.daimler.mbmobilesdk.vehicledeletion.DeletableVehicle
import com.daimler.mbmobilesdk.views.GarageVehicleInformationView
import com.daimler.mbcommonkit.extensions.replace
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.vehicle.DoorLockState
import com.daimler.mbcarkit.business.model.vehicle.Tank
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.business.model.vehicle.image.ImageCropOption
import com.daimler.mbcarkit.business.model.vehicle.image.ImageManipulation
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImage
import com.daimler.mbcarkit.socket.observable.VehicleObserver
import com.daimler.mbnetworkkit.socket.message.Observables
import com.daimler.mbnetworkkit.socket.message.dispose
import com.daimler.mbnetworkkit.socket.message.observe
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf
import kotlin.math.max

internal class GarageViewModel(
    application: Application
) : MyCarSocketAndroidViewModel(application),
    VehicleGarageItemListener,
    GarageVehicleInformationView.GarageVehicleInformationListener,
    ForegroundListener {

    val loading = MutableLiveData<Boolean>()
    val vehicles = MutableLiveArrayList<BaseVehicleGarageItem>()
    val scrollPosition = mutableLiveDataOf(0)

    val informationContentAvailable = MutableLiveData<Boolean>()
    val pairingInformationVisible = MutableLiveData<Boolean>()
    val vehicleInformationVisible = MutableLiveData<Boolean>()
    val currentGarageVehicle = MutableLiveData<GarageVehicle>()

    val searchRetailerEvent = MutableLiveUnitEvent()
    val vehiclesLoadedEvent = MutableLiveEvent<Int>()
    val unselectVehicleEvent = MutableLiveUnitEvent()
    val vehicleSelectedEvent = MutableLiveEvent<VehicleSelectedEvent>()
    val addVehicleEvent = MutableLiveUnitEvent()
    val vehicleInfoEvent = MutableLiveEvent<GarageVehicle>()
    val userManagementEvent = MutableLiveEvent<GarageVehicle>()
    val showServicesEvent = MutableLiveEvent<GarageVehicle>()
    val showVacEvent = MutableLiveEvent<GarageVehicle>()
    val showNoRifSupportEvent = MutableLiveEvent<GarageVehicle>()
    val vehiclesNotLoadedError = MutableLiveUnitEvent()

    val fullyAssignedCarsAmount: Int
        get() = vehicles.value.count { it.isSelectableVehicle() }

    private val vehicleUpdateObserver = VehicleObserver.VehicleUpdate { updateVehicles() }
    private val tankObserver = VehicleObserver.Tank { updateTankForSelectedVehicle(it) }
    private val lockedObserver = VehicleObserver.VehicleData { updateLockedStateForSelectedVehicle(it.doorLockState.value) }

    init {
        loadVehicles()
        connect()
        MBMobileSDK.foregroundObservable().registerForegroundListener(this)
    }

    override fun onCleared() {
        super.onCleared()
        MBMobileSDK.foregroundObservable().unregisterForegroundListener(this)
    }

    fun getSnapHelper() = PagerSnapHelper()

    fun getItemDecoration() = CenteringItemDecoration(getDimensionPixelSize(R.dimen.garage_vehicle_width))

    fun onSnapPositionChanged(newPosition: Int) {
        postToMainThread { handleSnapPosition(newPosition) }
    }

    override fun onAppWentToForeground() {
        MBCarKit.selectedFinOrVin()?.let { finOrVin ->
            vehicles.value.forEach {
                it.updateSelection(it.isSameFinOrVin(finOrVin))
            }
            snapToSelectedVehicleIfPossible()
        } ?: selectFirstValidVehicleAndNotify()
    }

    override fun onAppWentToBackground() = Unit

    private fun handleSnapPosition(position: Int) {
        vehicles.value.apply {
            forEachIndexed { index, item -> item.visibleToUser = index == position }
            getOrNull(position)?.let {
                handleVisibleItem(it)
            } ?: MBLoggerKit.e("No garage item found at position $position.")
        }
    }

    /* Socket */

    override fun onRegisterObservers(observables: Observables) {
        observables.observe(vehicleUpdateObserver)
        observables.observe(tankObserver)
        observables.observe(lockedObserver)
    }

    override fun onDisposeObservers(observables: Observables) {
        observables.dispose(vehicleUpdateObserver)
        observables.dispose(tankObserver)
        observables.dispose(lockedObserver)
    }

    /* VehicleGarageItemListener */

    override fun onVehicleSelected(vehicle: GarageVehicle) {
        handleVehicleSelection(vehicle)
    }

    override fun onAssignVehicle() {
        addVehicleEvent.sendEvent()
    }

    override fun onContinueAssignment(vehicle: GarageVehicle) {
        handleNoRifOrNoVac(vehicle)
    }

    override fun onScrollToVehicle(itemId: String) {
        val index = vehicles.value.indexOfFirst { it.itemId == itemId }
        if (index != -1) {
            scrollPosition.postValue(index)
            handleSnapPosition(index)
        }
    }

    /* GarageVehicleInformationListener */

    override fun onVehicleInformationClicked(vehicle: GarageVehicle) {
        vehicleInfoEvent.sendEvent(vehicle)
    }

    override fun onManageServicesClicked(vehicle: GarageVehicle) {
        if (vehicle.isTrustLevelSufficient()) {
            showServicesEvent.sendEvent(vehicle)
        } else {
            handleNoRifOrNoVac(vehicle)
        }
    }

    override fun onUserManagementClicked(vehicle: GarageVehicle) {
        userManagementEvent.sendEvent(vehicle)
    }

    /* --- */

    fun deletableVehicles(): List<DeletableVehicle>? {
        val result = vehicles.value.filter {
            it.isVehicleType() && !it.isPendingType()
        }.mapNotNull { item ->
            item.finOrVin?.let { vin ->
                MBCarKit.loadVehicleByVin(vin)?.let {
                    DeletableVehicle(it.finOrVin, it.model)
                }
            }
        }
        return if (result.isNotEmpty()) result else null
    }

    fun vehicleAssigned(finOrVin: String, isLegacyVehicle: Boolean) {
        if (isLegacyVehicle) {
            updateVehicles()
            MBCarKit.loadVehicleByVin(finOrVin)?.let {
                onVehicleSelected(garageVehicleFromVehicle(it, VehicleStatus.initialState(finOrVin), true))
                snapToSelectedVehicleIfPossible()
            }
        } else {
            addOrUpdateAndSelectIfNecessary(MBCarKit.getAssigningGarageVehicle(finOrVin))
        }
    }

    fun vehicleDeleted(finOrVin: String) {
        addOrUpdateAndSelectIfNecessary(MBCarKit.getDeletingGarageVehicle(finOrVin))
    }

    private fun addOrUpdateAndSelectIfNecessary(vehicle: GarageVehicle) {
        addOrUpdateVehicleItem(vehicle, vehicles.value, true)
        vehicles.dispatchChange()
        if (MBCarKit.selectedFinOrVin() == null) {
            selectFirstValidVehicleAndNotify()
            snapToSelectedVehicleIfPossible()
        }
    }

    private fun loadVehicles() {
        onLoading()
        MBCarKit.loadGarageVehicles()
            .onComplete {
                onGarageLoaded(it)
            }.onFailure {
                vehiclesNotLoadedError.sendEvent()
            }.onAlways { _, _, _ ->
                onLoadingFinished()
            }
    }

    private fun updateVehicles() {
        MBCarKit.loadGarageVehicles()
            .onComplete {
                onGarageLoaded(it)
            }.onFailure {
                MBLoggerKit.re("Failed to load garage vehicles.", it)
            }
    }

    private fun onGarageLoaded(vehicles: List<GarageVehicle>) {
        synchronized(this.vehicles) {
            val initial = this.vehicles.value.isEmpty()

            // Remove unassigned vehicles.
            val vehiclesToRemove = this.vehicles.value
                .filter { item ->
                    item.isVehicleType() && vehicles.none { item.isSameFinOrVin(it.finOrVin) }
                }
            this.vehicles.value.removeAll(vehiclesToRemove)

            // Add/ update/ swap vehicles.
            vehicles.forEach {
                addOrUpdateVehicleItem(it, this.vehicles.value, !initial)
            }

            if (MBCarKit.selectedFinOrVin() == null) {
                selectFirstValidVehicleAndNotify()
            }

            if (initial) {
                this.vehicles.value.let {
                    addAddVehicleItem(it, it.size)
                }
            }
            this.vehicles.dispatchChange()
            if (initial) snapToSelectedVehicleIfPossible()
            vehiclesLoadedEvent.sendEvent(vehicles.size)
        }
    }

    private fun addOrUpdateVehicleItem(vehicle: GarageVehicle, items: MutableList<BaseVehicleGarageItem>, addWithOffset: Boolean) {
        val garageItem = items.find { it.isSameFinOrVin(vehicle.finOrVin) }
        garageItem?.let { item ->
            if (item.isItemTypeFor(vehicle)) {
                // Update.
                item.update(vehicle)
            } else {
                // Swap.
                val newItem = GarageItemFactory.createGarageVehicleItem(
                    vehicle, this@GarageViewModel)
                items.replace(item, newItem)
            }
        } ?: {
            // Add.
            val item = GarageItemFactory.createGarageVehicleItem(vehicle, this@GarageViewModel)
            if (addWithOffset) {
                val index = max(0, items.size - VEHICLE_ITEMS_POSITION_OFFSET)
                items.add(index, item)
            } else {
                items.add(item)
            }
            loadVehicleImage(vehicle)
        }()
    }

    private fun addAddVehicleItem(list: MutableList<BaseVehicleGarageItem>, position: Int) {
        list.add(position, GarageItemFactory.createAddVehicleItem(this))
    }

    private fun updateTankForSelectedVehicle(tank: Tank) {
        updateCurrentGarageVehicleIfAvailable { it.copy(tankLevel = tank.tankLevel()) }
    }

    private fun updateLockedStateForSelectedVehicle(lockState: DoorLockState?) {
        updateCurrentGarageVehicleIfAvailable { it.copy(locked = lockState?.isLocked()) }
    }

    private fun updateCurrentGarageVehicleIfAvailable(action: (GarageVehicle) -> GarageVehicle) {
        currentGarageVehicle.apply {
            value?.let { postValue(action(it)) }
        }
    }

    private fun snapToSelectedVehicleIfPossible() {
        if (vehicles.value.isEmpty()) return

        val selectedIndex = vehicles.value.indexOfFirst { it.isVehicleSelected() }
        if (selectedIndex in 0 until vehicles.value.size) {
            scrollPosition.postValue(selectedIndex)
            handleSnapPosition(selectedIndex)
        } else {
            scrollPosition.postValue(0)
            handleSnapPosition(0)
        }
    }

    private fun loadVehicleImage(vehicle: GarageVehicle) {
        VehicleImageLoader(vehicle.finOrVin).loadDefault(manipulation = ImageManipulation.Crop(ImageCropOption.BEST_EFFORT))
            .onComplete { images ->
                images.firstOrNull()?.let {
                    applyVehicleImage(vehicle, it)
                }
            }
            .onFailure { error ->
                MBLoggerKit.re("Failed to load vehicle image.", error)
                ContextCompat.getDrawable(getApplication(), R.drawable.garage_placeholder)?.let { imageDrawable ->
                    applyVehicleImage(vehicle, imageDrawable)
                }
            }
    }

    private fun applyVehicleImage(vehicle: GarageVehicle, image: VehicleImage) {
        image.imageBytes?.let {
            resizeBitmap(it, MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT)?.toBitmapDrawable(getApplication<Application>())?.let { drawable ->
                applyVehicleImage(vehicle, drawable)
            }
        }
    }

    private fun applyVehicleImage(vehicle: GarageVehicle, image: Drawable) {
        vehicles.value.firstOrNull {
            it.isSameVehicle(vehicle)
        }?.updateVehicleImage(image)
    }

    private fun handleVehicleSelection(vehicle: GarageVehicle) {
        vehicles.value.forEach {
            when {
                it.isVehicleSelected() -> it.updateSelection(false)
                it.isSameVehicle(vehicle) -> {
                    MBCarKit.selectVehicle(vehicle.finOrVin)
                    it.updateSelection(true)
                }
            }
        }
        MBCarKit.loadVehicleByVin(vehicle.finOrVin)?.let {
            vehicleSelectedEvent.sendEvent(VehicleSelectedEvent(it))
        }
    }

    private fun selectFirstValidVehicleAndNotify() {
        vehicles.value.find { it.isSelectableVehicle() }?.let { item ->
            item.ifIsSelectableVehicle { finOrVin ->
                MBCarKit.selectVehicle(finOrVin)
                item.updateSelection(true)
                MBCarKit.loadVehicleByVin(finOrVin)?.let {
                    vehicleSelectedEvent.sendEvent(VehicleSelectedEvent(it, true))
                }
            }
        } ?: unselectVehicleEvent.sendEvent()
    }

    private fun handleNoRifOrNoVac(vehicle: GarageVehicle) {
        vehicles.value.find { it.isSameVehicle(vehicle) }?.let { item ->
            item.setItemProcessing(true)
            MBIngressKit.refreshTokenIfRequired()
                .onComplete { token ->
                    val jwt = token.jwtToken.plainToken
                    MBCarKit.assignmentService().fetchRifability(jwt, vehicle.finOrVin)
                        .onComplete {
                            if (it.isRifable) {
                                showVacEvent.sendEvent(vehicle)
                            } else {
                                showNoRifSupportEvent.sendEvent(vehicle)
                            }
                        }
                        .onFailure {
                            MBLoggerKit.re("Failed to fetch rifability.", it)
                        }.onAlways { _, _, _ ->
                            item.setItemProcessing(false)
                        }
                }.onFailure {
                    MBLoggerKit.e("Failed to refresh token", throwable = it)
                    item.setItemProcessing(false)
                }
        }
    }

    private fun handleVisibleItem(item: BaseVehicleGarageItem) {
        when (item.type) {
            GarageItemType.ADD -> showAddVehicleContent()
            GarageItemType.FULL_ASSIGNED -> item.finOrVin?.let { showVehicleInformationContent(it) }
            GarageItemType.PENDING -> showPendingVehicleContent()
            GarageItemType.INSUFFICIENT -> item.finOrVin?.let { showVehicleInformationContent(it) }
        }
    }

    private fun showVehicleInformationContent(finOrVin: String) {
        pairingInformationVisible.postValue(false)
        MBCarKit.loadGarageVehicleByVin(finOrVin)?.let {
            informationContentAvailable.postValue(true)
            currentGarageVehicle.postValue(it)
            vehicleInformationVisible.postValue(true)
        }
    }

    private fun showAddVehicleContent() {
        informationContentAvailable.postValue(true)
        vehicleInformationVisible.postValue(false)
        pairingInformationVisible.postValue(true)
    }

    private fun showPendingVehicleContent() {
        informationContentAvailable.postValue(false)
        vehicleInformationVisible.postValue(false)
        pairingInformationVisible.postValue(false)
    }

    private fun onLoading() {
        loading.postValue(true)
    }

    private fun onLoadingFinished() {
        loading.postValue(false)
    }

    private companion object {

        private const val MAX_IMAGE_WIDTH = 480
        private const val MAX_IMAGE_HEIGHT = 270

        private const val VEHICLE_ITEMS_POSITION_OFFSET = 1
    }
}