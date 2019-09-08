package com.daimler.mbmobilesdk.menu

import android.app.Application
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.vehicle.DoorLockState
import com.daimler.mbcarkit.business.model.vehicle.FuelType
import com.daimler.mbcarkit.business.model.vehicle.Tank
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbcarkit.business.model.vehicle.image.ImageCropOption
import com.daimler.mbcarkit.business.model.vehicle.image.ImageManipulation
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImage
import com.daimler.mbcarkit.socket.observable.VehicleObserver
import com.daimler.mbcommonkit.preferences.PreferenceObserver
import com.daimler.mbdeeplinkkit.MBDeepLinkKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.User
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.app.foreground.ForegroundListener
import com.daimler.mbmobilesdk.app.format
import com.daimler.mbmobilesdk.logic.UserTask
import com.daimler.mbmobilesdk.login.ProfileLogoutState
import com.daimler.mbmobilesdk.push.areNotificationsEnabledOnAppSettings
import com.daimler.mbmobilesdk.settings.SettingsManager
import com.daimler.mbmobilesdk.utils.extensions.*
import com.daimler.mbmobilesdk.utils.resizeBitmap
import com.daimler.mbmobilesdk.vehicleselection.GarageVehicle
import com.daimler.mbmobilesdk.vehicleselection.VehicleImageLoader
import com.daimler.mbmobilesdk.vehicleselection.isSelectableVehicle
import com.daimler.mbmobilesdk.views.MBProfileMenuHeader
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.socket.ConnectionState
import com.daimler.mbnetworkkit.socket.SocketConnectionListener
import com.daimler.mbnetworkkit.socket.message.Observables
import com.daimler.mbnetworkkit.socket.message.dispose
import com.daimler.mbnetworkkit.socket.message.observe
import com.daimler.mbuikit.components.viewmodels.MBBaseMenuViewModel
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf
import com.daimler.mbuikit.utils.extensions.toDefault

class MBMobileSDKViewModel(
    app: Application
) : MBBaseMenuViewModel(app), SocketConnectionListener, ForegroundListener {

    val titleVisible = MutableLiveData<Boolean>()
    val logoVisible = MutableLiveData<Boolean>()

    val userName = MutableLiveData<String>().apply { toDefault() }
    val userId = MutableLiveData<String>().apply { toDefault() }
    val profilePictureBytes = MutableLiveData<ByteArray>()

    val vehicleModel = MutableLiveData<String>().apply { toDefault() }
    val vehicleInfo = MutableLiveData<String>().apply { toDefault() }
    val vehicleTankLevel = MutableLiveData<Int>()
    val vehicleEnergyLevel = MutableLiveData<Int>()
    val vehicleFuelType = MutableLiveData<FuelType>()
    val vehicleLocked = MutableLiveData<Boolean>()
    val vehicleSelected = mutableLiveDataOf(false)
    val loadingData = mutableLiveDataOf(false)
    val vehicleImageChangedEvent = MutableLiveData<Bitmap>()
    val vehicleSelectedEvent = MutableLiveEvent<VehicleInfo>()

    internal val clickEvent = MutableLiveEvent<Int>()
    internal val userLoadedEvent = MutableLiveEvent<UserLoadingResult>()
    internal val garageVehiclesLoadedEvent = MutableLiveEvent<GarageVehiclesLoadingResult>()
    internal val logoutEvent = MutableLiveEvent<Boolean>()
    internal val showLogOverlayEvent = MutableLiveUnitEvent()

    private val tankObserver = VehicleObserver.Tank { updateTank(it) }
    private val lockedObserver = VehicleObserver.VehicleData { updateLockedState(it.doorLockState.value) }
    private val vehicleUpdateObserver = VehicleObserver.VehicleUpdate { updateMasterData() }

    private var socketObservables: Observables? = null
    private var vehicleFinOrVin: String? = null

    private val fcmTokenObserver = object : PreferenceObserver<String> {
        override fun onChanged(newValue: String) {
            handlePushRegistration(newValue)
        }
    }

    init {
        MBMobileSDK.foregroundObservable().registerForegroundListener(this)

        loadUser()
        loadVehicleData(true)
        loadAppsAndDeepLinks()
        connectToWebSocket()

        val tokenPref = MBMobileSDK.pushSettings().fcmToken
        tokenPref.observe(fcmTokenObserver)

        handlePushRegistration(tokenPref.get())
    }

    override fun onCleared() {
        super.onCleared()
        MBMobileSDK.foregroundObservable().unregisterForegroundListener(this)
        MBMobileSDK.pushSettings().fcmToken.stopObserving(fcmTokenObserver)
        disposeSocketObservables()
        MBCarKit.unregisterFromSocket(this)
    }

    fun getOnProfileClickedListener() =
        object : MBProfileMenuHeader.OnHeaderClickedListener {

            override fun onHeaderImageClicked() {
                clickEvent.sendEvent(EVENT_CLICK_PROFILE)
            }

            override fun onHeaderProfileClicked() {
                clickEvent.sendEvent(EVENT_CLICK_PROFILE)
            }

            override fun onVehicleClicked() {
                clickEvent.sendEvent(EVENT_CLICK_VEHICLE)
            }
        }

    fun onToolbarLogoLongClicked(): Boolean {
        return if (getApplication<Application>().applicationInfo.flags.and(ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            showLogOverlayEvent.sendEvent()
            true
        } else {
            false
        }
    }

    override fun onAppWentToForeground() {
        val selectedFinOrVin = MBCarKit.selectedFinOrVin()
        if (selectedFinOrVin != vehicleFinOrVin) {
            loadVehicleData(false)
        }
    }

    override fun onAppWentToBackground() = Unit

    override fun connectionStateChanged(connectionState: ConnectionState) {
        when (connectionState) {
            is ConnectionState.Connected -> applySocketObservables(connectionState.observables)
            is ConnectionState.Disconnected -> disposeSocketObservables()
        }
    }

    fun showToolbarLogo() {
        titleVisible.postValue(false)
        logoVisible.postValue(true)
    }

    fun showToolbarTitle(title: String?) {
        logoVisible.postValue(false)
        titleVisible.postValue(true)
        setToolbarTitle(title)
    }

    private fun applySocketObservables(observables: Observables) {
        socketObservables = observables
        socketObservables?.let {
            it.observe(tankObserver)
            it.observe(lockedObserver)
            it.observe(vehicleUpdateObserver)
        }
    }

    private fun disposeSocketObservables() {
        socketObservables?.let {
            it.dispose(tankObserver)
            it.dispose(lockedObserver)
            it.dispose(vehicleUpdateObserver)
        }
        socketObservables = null
    }

    internal fun onVehicleSelected() {
        loadVehicleData()
    }

    internal fun onUserChanged(user: User) {
        applyUserForMenuHeader(user, false)
    }

    internal fun onUserPictureChanged(pictureBytes: ByteArray) {
        profilePictureBytes.postValue(pictureBytes)
    }

    internal fun onLogoutStateChanged(state: ProfileLogoutState) {
        when (state) {
            ProfileLogoutState.STARTED -> unregisterFromPushes()
            ProfileLogoutState.FINISHED_SUCCESS -> finalizeLogout()
            ProfileLogoutState.FINISHED_ERROR -> finalizeLogout()
        }
    }

    private fun finalizeLogout() {
        if (!MBCarKit.isSocketDisposed()) MBCarKit.sendLogoutMessage()
        MBMobileSDK.destroy()
        logoutEvent.sendEvent(true)
    }

    private fun loadUser() {
        UserTask().fetchUser()
            .onComplete {
                MBMobileSDK.headerService().updateNetworkLocale(MBMobileSDK.userLocale().format())
                applyUserForMenuHeader(it.user, true)
                notifyUserResult(UserLoadingResult(it.user, it.wasRequested))
            }.onFailure { notifyUserResult(UserLoadingResult(wasRequested = true, error = it)) }
    }

    private fun loadVehicleData(isInitial: Boolean = false) {
        if (loadingVehicleData().not()) {
            MBCarKit.loadSelectedGarageVehicle()?.let {
                updateGarageVehicleData(it)
                loadVehicleImage(it)
            } ?: loadVehicleDataFromApi(isInitial)
        }
    }

    private fun loadingVehicleData(): Boolean {
        return loadingData.value == true
    }

    private fun loadAppsAndDeepLinks() {
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                MBDeepLinkKit.appService().fetchAppsAndDeepLinks(token.jwtToken.plainToken)
                    .onComplete { MBLoggerKit.d("Fetched apps and deep links.") }
                    .onFailure { MBLoggerKit.re("Failed to fetch apps and deep links.", it) }
            }.onFailure { MBLoggerKit.e("Could not fetch apps and deep links since token could not be refreshed.") }
    }

    private fun connectToWebSocket() {
        MBIngressKit.refreshTokenIfRequired()
            .onComplete {
                MBCarKit.connectToWebSocket(it.jwtToken.plainToken, this)
            }.onFailure {
                MBLoggerKit.e("Could not connect to socket since token could not be refreshed.", throwable = it)
            }
    }

    private fun updateGarageVehicleData(vehicle: GarageVehicle) {
        vehicleFinOrVin = vehicle.finOrVin
        loadingData.postValue(false)
        vehicleSelected.postValue(vehicle.selected)
        vehicleModel.postValue(vehicle.model)
        vehicleInfo.postValue(vehicle.licensePlate)
        vehicleLocked.postValue(vehicle.locked)
        vehicleTankLevel.postValue(vehicle.tankLevel)
        vehicleEnergyLevel.postValue(vehicle.energyLevel)
        vehicleFuelType.postValue(vehicle.fuelType)
    }

    private fun updateTank(tank: Tank) {
        vehicleTankLevel.postValue(tank.tankLevel())
    }

    private fun updateLockedState(doorLockState: DoorLockState?) {
        vehicleLocked.postValue(doorLockState?.isLocked())
    }

    private fun updateMasterData() {
        MBLoggerKit.d("Updating master data.")
        loadVehicleDataFromApi()
    }

    private fun garageVehicleUnselected() {
        vehicleSelected.postValue(false)
        vehicleModel.toDefault()
        vehicleInfo.toDefault()
        vehicleLocked.postValue(null)
        vehicleTankLevel.postValue(null)

        vehicleImageChangedEvent.postValue(
            BitmapFactory.decodeResource(
                getApplication<Application>().resources,
                R.drawable.img_vehicle_placeholder
            )
        )
    }

    fun unselectVehicleMenu() {
        garageVehicleUnselected()
        loadVehicleData(false)
    }

    private fun initialGarageVehicleDataLoading() {
        loadingData.postValue(true)
        garageVehicleUnselected()
    }

    private fun loadVehicleDataFromApi(isInitial: Boolean = false) {
        if (isInitial) initialGarageVehicleDataLoading()
        MBCarKit.loadGarageVehicles()
            .onComplete {
                applyVehicles(it)
                notifyGarageVehiclesResult(GarageVehiclesLoadingResult(it))
            }.onFailure {
                notifyGarageVehiclesResult(GarageVehiclesLoadingResult(error = it))
            }.onAlways { _, _, _ ->
                loadingData.postValue(false)
            }
    }

    private fun applyVehicles(
        vehicles: List<GarageVehicle>
    ) {
        if (vehicles.isNotEmpty()) {
            val selectedVehicle = MBCarKit.loadSelectedGarageVehicle()
            if (selectedVehicle == null) {
                vehicles.firstOrNull { it.isSelectableVehicle() }?.let {
                    selectVehicleAndNotify(it.finOrVin)
                    updateGarageVehicleData(it.copy(selected = true))
                    loadVehicleImage(it)
                }
            } else {
                updateGarageVehicleData(selectedVehicle)
                loadVehicleImage(selectedVehicle)
            }
        } else {
            garageVehicleUnselected()
        }
    }

    private fun loadVehicleImage(vehicle: GarageVehicle) {
        VehicleImageLoader(vehicle.finOrVin).loadDefault(manipulation = ImageManipulation.Crop(ImageCropOption.BEST_EFFORT))
            .onComplete { images ->
                images.firstOrNull()?.let { applyVehicleImage(it) }
            }
            .onFailure { error ->
                MBLoggerKit.re("Failed to load vehicle image.", error)
                BitmapFactory.decodeResource(getApplication<Application>().resources,
                    R.drawable.garage_placeholder)?.let {
                    notifyVehicleImage(it)
                }
            }
    }

    private fun applyVehicleImage(image: VehicleImage) {
        image.imageBytes?.let {
            resizeBitmap(it, MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT)?.let { bmp ->
                notifyVehicleImage(bmp)
            }
        }
    }

    private fun selectVehicleAndNotify(finOrVin: String) {
        MBCarKit.selectVehicle(finOrVin)
        MBCarKit.loadVehicleByVin(finOrVin)?.let {
            vehicleSelectedEvent.sendEvent(it)
        }
    }

    private fun notifyVehicleImage(image: Bitmap) {
        vehicleImageChangedEvent.postValue(image)
    }

    private fun handlePushRegistration(fcmToken: String) {
        if (isEligibleForPushes(fcmToken)) registerForPushes(fcmToken)
    }

    private fun isEligibleForPushes(fcmToken: String) =
        areNotificationsEnabledOnAppSettings() &&
            !fcmToken.isBlank() &&
            !MBMobileSDK.appIdentifier().isBlank()

    private fun registerForPushes(fcmToken: String) {
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                SettingsManager.pushSettingsHandler().enablePushNotifications(jwt, fcmToken)
                    .onComplete {
                        MBLoggerKit.d("Registered for push notifications.")
                    }.onFailure {
                        MBLoggerKit.re("Registering for push notifications failed.", it)
                    }
            }.onFailure {
                MBLoggerKit.e("Failed to refresh token.", throwable = it)
            }
    }

    private fun applyUserForMenuHeader(user: User, fetchProfilePicture: Boolean = false) {
        userName.postValue(user.formatName())
        userId.postValue(user.email)
        if (fetchProfilePicture) fetchProfilePicture()
        updateUserContextForFeatureToggling(user)
    }

    private fun updateUserContextForFeatureToggling(user: User) {
        MBMobileSDK.featureToggleService().swapUserContext(user.toUserContext())
            .onComplete { id ->
                MBLoggerKit.d("Swapped user context to $id.")
            }.onFailure { error ->
                MBLoggerKit.e("Failed to swap user context.", throwable = error)
            }
    }

    private fun fetchProfilePicture() {
        val token = MBIngressKit.authenticationService().getToken().jwtToken
        MBIngressKit.userService().fetchProfilePictureBytes(token.plainToken)
            .onComplete { profilePictureBytes.value = it }
            .onFailure { MBLoggerKit.e("Failed to fetch Profile Picture") }
    }

    private fun notifyUserResult(result: UserLoadingResult) = userLoadedEvent.sendEvent(result)

    private fun notifyGarageVehiclesResult(result: GarageVehiclesLoadingResult) = garageVehiclesLoadedEvent.sendEvent(result)

    private fun unregisterFromPushes() {
        val token = MBIngressKit.authenticationService().getToken().jwtToken.plainToken
        SettingsManager.pushSettingsHandler().disablePushNotifications(token)
    }

    /**
     * Represents the result of a [UserTask] that loads a valid [User] object.
     *
     * @param user the user object, if loaded successfully
     * @param wasRequested true, if the user object was requested through the network
     * @param error the error if one occurred during the process
     */
    internal data class UserLoadingResult(val user: User? = null, val wasRequested: Boolean, val error: Throwable? = null)

    internal data class GarageVehiclesLoadingResult(val vehicles: List<GarageVehicle>? = null, val error: ResponseError<out RequestError>? = null)

    internal companion object {
        const val EVENT_CLICK_PROFILE = 1
        const val EVENT_CLICK_VEHICLE = 2
        const val EVENT_MENU_ICON_CLICKED = 3

        private const val MAX_IMAGE_WIDTH = 480
        private const val MAX_IMAGE_HEIGHT = 270
    }
}