package com.daimler.mbcarkit

import android.annotation.SuppressLint
import com.daimler.mbcarkit.bluetooth.BluetoothSendToCarService
import com.daimler.mbcarkit.business.AccountLinkageService
import com.daimler.mbcarkit.business.AssignmentService
import com.daimler.mbcarkit.business.CarVehicleApiCommandManager
import com.daimler.mbcarkit.business.CommandCapabilitiesCache
import com.daimler.mbcarkit.business.GeofencingService
import com.daimler.mbcarkit.business.OnboardGeofencingService
import com.daimler.mbcarkit.business.OutletsService
import com.daimler.mbcarkit.business.SelectedVehicleStorage
import com.daimler.mbcarkit.business.SendToCarCache
import com.daimler.mbcarkit.business.SendToCarService
import com.daimler.mbcarkit.business.ServiceCache
import com.daimler.mbcarkit.business.ServiceService
import com.daimler.mbcarkit.business.SpeedAlertService
import com.daimler.mbcarkit.business.SpeedfenceService
import com.daimler.mbcarkit.business.UserManagementService
import com.daimler.mbcarkit.business.ValetProtectService
import com.daimler.mbcarkit.business.VehicleImageCache
import com.daimler.mbcarkit.business.VehicleImageService
import com.daimler.mbcarkit.business.VehicleService
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiService
import com.daimler.mbcarkit.business.model.command.VehicleCommand
import com.daimler.mbcarkit.business.model.command.VehicleCommandCallback
import com.daimler.mbcarkit.business.model.command.VehicleCommandError
import com.daimler.mbcarkit.business.model.command.VehicleCommandStatus
import com.daimler.mbcarkit.business.model.command.capabilities.CommandCapabilities
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbcarkit.business.model.services.ServiceResolvedPrecondition
import com.daimler.mbcarkit.business.model.services.ServiceStatus
import com.daimler.mbcarkit.business.model.services.ServiceUpdate
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.socket.CarKitMessageProcessor
import com.daimler.mbcarkit.socket.PinCommandVehicleApiStatusCallback
import com.daimler.mbcarkit.socket.ServiceMessageProcessor
import com.daimler.mbcarkit.socket.UserManagementCache
import com.daimler.mbcarkit.socket.VehicleCache
import com.daimler.mbcarkit.socket.VehicleStatusCache
import com.daimler.mbnetworkkit.socket.SocketService
import com.daimler.mbnetworkkit.socket.message.MessageProcessor
import com.daimler.mbprotokit.CarKitMessageParser
import com.daimler.mbprotokit.ServiceKitMessageParser

object MBCarKit {

    var bluetoothSendToCarService: BluetoothSendToCarService? = null

    @SuppressLint("StaticFieldLeak")
    private lateinit var vehicleRepository: VehicleService
    private lateinit var merchantsRepository: OutletsService
    private lateinit var assignmentService: AssignmentService
    private lateinit var serviceService: ServiceService
    private lateinit var speedAlertService: SpeedAlertService
    private lateinit var vehicleCache: VehicleCache
    private lateinit var vehicleStatusCache: VehicleStatusCache
    private lateinit var vehicleImageCache: VehicleImageCache
    private lateinit var serviceCache: ServiceCache
    private lateinit var imageService: VehicleImageService
    private lateinit var carVehicleApiCommandManager: CarVehicleApiCommandManager
    private lateinit var sendToCarService: SendToCarService
    private lateinit var commandVehicleApiService: CommandVehicleApiService
    private lateinit var selectedVehicleStorage: SelectedVehicleStorage
    private lateinit var geofencingService: GeofencingService
    private lateinit var onboardGeofencingService: OnboardGeofencingService
    private lateinit var speedfencingService: SpeedfenceService
    private lateinit var valetProtectService: ValetProtectService
    private lateinit var userManagementService: UserManagementService
    private lateinit var userManagementCache: UserManagementCache
    private lateinit var commandCapabilitiesCache: CommandCapabilitiesCache
    private lateinit var sendToCarCache: SendToCarCache
    private lateinit var accountLinkageService: AccountLinkageService

    fun init(carKitServiceConfig: MBCarKitServiceConfig): MBCarKit {
        this.vehicleRepository = carKitServiceConfig.vehicleRepository
        this.merchantsRepository = carKitServiceConfig.merchantsRepository
        this.vehicleCache = carKitServiceConfig.vehicleCache
        this.imageService = carKitServiceConfig.imageService
        this.vehicleStatusCache = carKitServiceConfig.vehicleStatusCache
        vehicleImageCache = carKitServiceConfig.imageCache
        serviceCache = carKitServiceConfig.serviceCache
        assignmentService = carKitServiceConfig.assignmentService
        serviceService = carKitServiceConfig.serviceService
        speedAlertService = carKitServiceConfig.speedAlertService
        carVehicleApiCommandManager = carKitServiceConfig.commandVehicleApiManager
        sendToCarService = carKitServiceConfig.sendToCarService
        commandVehicleApiService = carKitServiceConfig.commandVehicleApiService
        selectedVehicleStorage = carKitServiceConfig.selectedVehicleStorage
        geofencingService = carKitServiceConfig.geofencingService
        onboardGeofencingService = carKitServiceConfig.onboardGeofencingService
        speedfencingService = carKitServiceConfig.speedfenceService
        valetProtectService = carKitServiceConfig.valetProtectService
        userManagementService = carKitServiceConfig.userManagementService
        userManagementCache = carKitServiceConfig.userManagementCache
        commandCapabilitiesCache = carKitServiceConfig.commandCapabilitiesCache
        sendToCarCache = carKitServiceConfig.sendToCarCache
        accountLinkageService = carKitServiceConfig.accountLinkageService
        return this
    }

    /**
     * Creates an instance of [CarKitMessageProcessor]. This ensures that the used caches are the same
     * as used when [MBCarKit] was initialized. Of course, it requires that [MBCarKit.init] was
     * called before
     *
     * @param pinCommandVehicleApiStatusCallback Implementation of [PinCommandVehicleApiStatusCallback]
     * @param nextProcessor MessageProcessor that will handle message (or delegate it) if [MyCarMessageProcessor] didn't handle it
     */
    fun createCarKitMessageProcessor(
        pinCommandVehicleApiStatusCallback: PinCommandVehicleApiStatusCallback,
        nextProcessor: MessageProcessor? = null
    ): MessageProcessor {
        return CarKitMessageProcessor(
            vehicleCache,
            vehicleStatusCache,
            CarKitMessageParser(),
            commandVehicleApiService,
            pinCommandVehicleApiStatusCallback,
            selectedVehicleStorage,
            nextProcessor
        )
    }

    /**
     * Creates an instance of [ServiceMessageProcessor]. This ensures that the used caches are the same
     * as used when [MBCarKit] was initialized. Of course, it requires that [MBCarKit.init] was
     * called before
     *
     * @param nextProcessor MessageProcessor that will handle message (or delegate it) if [ServiceMessageProcessor] didn't handle it
     */
    fun createServiceMessageProcessor(
        nextProcessor: MessageProcessor? = null
    ): MessageProcessor {
        return ServiceMessageProcessor(
            ServiceKitMessageParser(),
            selectedVehicleStorage,
            nextProcessor
        )
    }

    /**
     * Fetches the cached status of the given vehicle.
     * @param finOrVin FIN/VIN of the vehicle
     */
    fun loadCurrentVehicleState(finOrVin: String): VehicleStatus {
        return vehicleStatusCache.currentVehicleState(finOrVin)
    }

    /**
     * Send a command to the car.
     *
     * The callback will be notified, when the state of the command changes or the command finishes.
     *
     * @param commandRequest Request that will be sent to the car
     * @param callback provide callback functions that will be notified of command updates
     */
    fun <T : VehicleCommandError> sendVehicleCommand(
        commandRequest: VehicleCommand<T>,
        callback: VehicleCommandCallback<T>
    ) {
        carVehicleApiCommandManager.sendCommand(commandRequest, callback)
    }

    /**
     * Gets the current [VehicleCommandStatus] for the passed [VehicleCommand]
     *
     * @param commandRequest API request command for which the status should be returned
     *
     * @return the current state if command is still in process
     */
    fun commandVehicleApiRequest(commandRequest: VehicleCommand<*>): VehicleCommandStatus? {
        return commandVehicleApiService.commandStateType(commandRequest)
    }

    /**
     * Get the configured [VehicleService] to access vehicle data
     */
    fun vehicleService(): VehicleService {
        return vehicleRepository
    }

    /**
     * Get the configured [GeofencingService] to access geofencing data (fences and violations)
     */
    fun geofencingService(): GeofencingService {
        return geofencingService
    }

    /**
     *  Get the configured [OnboardFenceService] to access onboardFencing data
     */
    fun onboardGeofencingService(): OnboardGeofencingService {
        return onboardGeofencingService
    }

    /**
     *  Get the configured [SpeedfencingService] to access speedfencing data
     */
    fun speedfencingService(): SpeedfenceService {
        return speedfencingService
    }

    /**
     * Get the configured [ValetProtectService] to access valet protect data (item and violations)
     */
    fun valetProtectService(): ValetProtectService {
        return valetProtectService
    }

    /**
     * Get the configured [OutletsService] to access merchant data
     */
    fun merchantService(): OutletsService {
        return merchantsRepository
    }

    /**
     * Get the configured [AssignmentService] to access vehicle assignment
     */
    fun assignmentService(): AssignmentService {
        return assignmentService
    }

    /**
     * Get the configured [ServiceService] to access vehicle services
     */
    fun serviceService(): ServiceService {
        return serviceService
    }

    /**
     * Get the configured [SendToCarService] to access "SendToCar" features
     */
    fun sendToCarService(): SendToCarService {
        return sendToCarService
    }

    /**
     * Get the configured [VehicleImageService] to load images for a vehicle from BBD
     */
    fun vehicleImageService(): VehicleImageService {
        return imageService
    }

    /**
     * Get the configured [SpeedAlertService] to fetch and delete SpeedViolations
     */
    fun speedAlertService(): SpeedAlertService {
        return speedAlertService
    }

    /**
     * Get the configured [UserManagementService] to access vehicle users
     */
    fun userManagementService(): UserManagementService {
        return userManagementService
    }

    /**
     * Get the configured [AccountLinkageService] to access connected accounts/ account providers.
     */
    fun accountLinkageService(): AccountLinkageService {
        return accountLinkageService
    }

    /**
     * Update the local selected vehicle.
     *
     * @param finOrVin FIN/VIN of the vehicle (vehicle identification number)
     */
    fun selectVehicle(finOrVin: String) {
        selectedVehicleStorage.selectFinOrVin(finOrVin)
    }

    /**
     * Update the local selected vehicle.
     *
     * @param vehicleInfo [VehicleInfo] representing the vehicle you want to select
     *
     * @see [selectVehicle]
     */
    fun selectVehicle(vehicleInfo: VehicleInfo) {
        selectedVehicleStorage.selectFinOrVin(vehicleInfo.finOrVin)
    }

    /**
     * Returns the selected FIN/ VIN or null if nothing was selected.
     * You can load the related vehicle using [loadVehicleByVin].
     *
     * ```
     * MBCarKit.selectedFinOrVin()?.let { finOrVin ->
     *      // A vehicle was selected.
     *      MBCarKit.loadVehicleByVin(finOrVin)?.let {
     *          // There is vehicle data available for the selected vehicle.
     *      } ?: {
     *          // There is no vehicle data available.
     *      }()
     * } ?: {
     *    // No vehicle was selected.
     * }()
     * ```
     */
    fun selectedFinOrVin(): String? {
        return selectedVehicleStorage.selectedFinOrVin()
    }

    /**
     * This will clear all local cached vehicle data like vehicles itself, vehicle updates and also the current selected
     * vehicle. This should only be the case e.g. if the user has been logged out.
     *
     * @throws IllegalStateException
     *                 If the cache should be cleared while socket connection is not in state [ConnectionState.Disconnected]
     *
     */
    fun clearLocalCache() {
        if (SocketService.isSocketDisposed()) {
            vehicleImageCache.clear()
            vehicleCache.clearVehicles()
            selectedVehicleStorage.clear()
            vehicleStatusCache.clearVehicleStates()
            userManagementCache.deleteAll()
            serviceCache.clearAll()
            commandCapabilitiesCache.deleteAll()
            sendToCarCache.deleteAllCapabilities()
        } else throw IllegalStateException("Only clear local cache if socket connection was closed.")
    }

    /**
     * Clears all cached data for the given vehicle
     * If this vehicle was selected before, the current selected vehicle is also cleared.
     *
     * @param finOrVin FIN/VIN of the vehicle (vehicle identification number)
     *
     * @throws IllegalStateException
     *                 If the cache should be cleared while socket connection is not in state [ConnectionState.Disconnected]
     */
    fun clearCacheForVehicle(finOrVin: String) {
        if (SocketService.isSocketDisposed()) {
            vehicleCache.deleteVehicle(finOrVin)
            userManagementCache.deleteUserManagement(finOrVin)
            if (selectedVehicleStorage.selectedFinOrVin() == finOrVin) selectedVehicleStorage.clear()
            vehicleStatusCache.clearVehicleState(finOrVin)
            serviceCache.clearForFinOrVin(finOrVin)
            commandCapabilitiesCache.deleteForFinOrVin(finOrVin)
            sendToCarCache.deleteCapabilities(finOrVin)
        } else throw IllegalStateException("Only clear local cache if socket connection was closed.")
    }

    /**
     * Loads the cached [VehicleInfo] for the given FIN/VIN
     *
     * @param finOrVin FIN/VIN of the vehicle (vehicle identification number)
     */
    fun loadVehicleByVin(finOrVin: String): VehicleInfo? {
        return vehicleCache.loadVehicleByVin(finOrVin)
    }

    /**
     * Returns the currently cached vehicles or an empty list if no cached vehicles exist.
     */
    fun loadVehicles(): List<VehicleInfo> {
        return vehicleCache.loadVehicles().vehicles
    }

    /**
     * Returns the locally cached services for the given FIN/ VIN or null if there are
     * no cached services.
     *
     * @param finOrVin FIN/VIN of the vehicle (vehicle identification number)
     */
    fun loadServicesForVin(finOrVin: String): List<Service>? {
        return serviceCache.loadServices(finOrVin)
    }

    /**
     * Sets the [Service.activationStatus] to the status given within the specific
     * [ServiceUpdate] for the locally cached service with the given id for the given FIN/ VIN.
     * The [Service.desiredActivationStatus] will be set to UNKNOWN.
     *
     * @param finOrVin FIN/VIN of the vehicle (vehicle identification number)
     * @param updates list of all services that should be updated
     */
    fun updateServiceStatus(finOrVin: String, updates: List<ServiceUpdate>) {
        serviceCache.updateServiceStatus(finOrVin, updates)
    }

    /**
     * Updates the preconditions of services. It marks it as fulfilled for all available services.
     *
     * @param finOrVin the FIN/ VIN of the vehicle
     * @param precondition the fulfilled precondition
     */
    fun updateServicePrecondition(finOrVin: String, precondition: ServiceResolvedPrecondition) {
        serviceCache.updatePrecondition(finOrVin, precondition)
    }

    /**
     * Returns true if the service for the given ID and vehicle is active.
     *
     * @param finOrVin FIN/VIN of the vehicle (vehicle identification number)
     * @param serviceId ID of the service you want to check
     */
    fun isServiceActiveForVehicle(finOrVin: String, serviceId: Int) =
        serviceCache.loadServiceById(finOrVin, serviceId)?.activationStatus == ServiceStatus.ACTIVE

    /**
     * Returns true if the service for the given ID is activated for the selected vehicle.
     *
     * @param serviceId ID of the service you want to check
     */
    fun isServiceActiveForSelectedVehicle(serviceId: Int) =
        selectedFinOrVin()?.let { isServiceActiveForVehicle(it, serviceId) } == true

    /**
     * Returns the cached command capabilities for a given vehicle or null if there are no
     * cached capabilities.
     *
     * @param finOrVin FIN/VIN of the vehicle (vehicle identification number)
     */
    fun loadCommandCapabilities(finOrVin: String): CommandCapabilities? =
        commandCapabilitiesCache.loadFromCache(finOrVin)
}
