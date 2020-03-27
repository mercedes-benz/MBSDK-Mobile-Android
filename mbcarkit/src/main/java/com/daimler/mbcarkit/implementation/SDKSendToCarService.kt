package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.SendToCarProvider
import com.daimler.mbcarkit.business.SendToCarService
import com.daimler.mbcarkit.business.model.sendtocar.RouteType
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapabilities
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapability
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPoi
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPrecondition
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarRoute
import com.daimler.mbcarkit.business.model.sendtocar.hasUnmetPreconditions
import com.daimler.mbcarkit.implementation.exceptions.SendToCarNotPossibleError
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

class SDKSendToCarService internal constructor(
    private val s2cProvider: SendToCarProvider
) : SendToCarService {

    private var isBluetoothEnabled: Boolean = false

    override fun fetchCapabilities(
        token: String,
        finOrVin: String
    ): FutureTask<SendToCarCapabilities, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<SendToCarCapabilities, ResponseError<out RequestError>?>()
        s2cProvider.fetchCapabilities(token, finOrVin)
            .onComplete {
                deferredTask.complete(
                    SendToCarCapabilities(
                        it.capabilities,
                        it.preconditions
                    )
                )
            }.onFailure {
                deferredTask.fail(it)
            }
        return deferredTask.futureTask()
    }

    override fun sendPoi(
        token: String,
        finOrVin: String,
        poi: SendToCarPoi
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Attempting to send POI via S2C to car #$finOrVin")
        val deferredTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        fetchCapabilities(token, finOrVin).onComplete { carCapabilities ->
            when {
                !isSendPoiPossibleForPreconditions(carCapabilities) -> {
                    MBLoggerKit.e("POI S2C impossible -> capability has unmet preconditions: ${carCapabilities.preconditions.joinToString(",")}")
                    deferredTask.fail(ResponseError.requestError(SendToCarNotPossibleError.Preconditions(carCapabilities.preconditions)))
                }
                isPoiSendingAvailable(carCapabilities) -> {
                    MBLoggerKit.d("POI sending is possible, attempting to send")
                    determineSendModeForPoi(carCapabilities, token, finOrVin, poi)?.let { mode ->
                        mode.sendPoi()
                            .onComplete {
                                deferredTask.complete(Unit)
                            }.onFailure {
                                deferredTask.fail(it)
                            }
                    }
                        ?: deferredTask.fail(ResponseError.requestError(SendToCarNotPossibleError.BluetoothNotConfigured))
                }
                else -> {
                    MBLoggerKit.e("POI S2C impossible -> required capabilities for route not present: ${carCapabilities.capabilities.joinToString(",")}")
                    deferredTask.fail(ResponseError.requestError(SendToCarNotPossibleError.Capability))
                }
            }
        }.onFailure {
            deferredTask.fail(it)
        }

        return deferredTask.futureTask()
    }

    private fun isSendPoiPossibleForPreconditions(carCapabilities: SendToCarCapabilities): Boolean {
        if (!carCapabilities.hasUnmetPreconditions()) {
            return true
        }

        // we allow Bluetooth in case MBApps also does not work
        return carCapabilities.capabilities.contains(SendToCarCapability.SINGLE_POI_BLUETOOTH) &&
            (
                carCapabilities.preconditions.contains(SendToCarPrecondition.MBAPPS_REGISTER_USER) ||
                    carCapabilities.preconditions.contains(SendToCarPrecondition.MBAPPS_ENABLE_MBAPPS)
                ) &&
            MBCarKit.bluetoothSendToCarService != null &&
            isBluetoothEnabled
    }

    private fun determineSendModeForPoi(
        carCapabilities: SendToCarCapabilities,
        token: String,
        finOrVin: String,
        poi: SendToCarPoi
    ): PoiSendMode? {
        return if (MBCarKit.bluetoothSendToCarService != null && isBluetoothEnabled) {
            MBLoggerKit.d("Bluetooth S2C Service present, attempt sending via bluetooth first")
            if (carCapabilities.capabilities.contains(SendToCarCapability.SINGLE_POI_BLUETOOTH)) {
                MBLoggerKit.d("Send POI via Bluetooth")
                MBCarKit.bluetoothSendToCarService?.let {
                    PoiSendMode.Bluetooth(
                        it,
                        finOrVin,
                        poi,
                        MBCarKit.bluetoothSendToCarService?.isBluetoothConnectionAvailable(finOrVin) == true
                    )
                }
            } else {
                MBLoggerKit.d("Car does not support Bluetooth POI, send via BA")
                PoiSendMode.Network(s2cProvider, token, finOrVin, poi)
            }
        } else {
            MBLoggerKit.d("No Bluetooth S2C Service present, send via BE")
            PoiSendMode.Network(s2cProvider, token, finOrVin, poi)
        }
    }

    override fun sendRoute(
        token: String,
        finOrVin: String,
        route: SendToCarRoute
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Attempting to send route via S2C to car #$finOrVin")
        val deferredTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        fetchCapabilities(token, finOrVin).onComplete { carCapabilities ->
            when {
                carCapabilities.hasUnmetPreconditions() -> {
                    MBLoggerKit.e("Route S2C impossible -> capability has unmet preconditions: ${carCapabilities.preconditions.joinToString(",")}")
                    deferredTask.fail(ResponseError.requestError(SendToCarNotPossibleError.Preconditions(carCapabilities.preconditions)))
                }
                isRouteSendingAvailable(carCapabilities.capabilities, route.routeType) -> {
                    MBLoggerKit.d("Route sending is possible, attempting to send")
                    s2cProvider.sendRoute(token, finOrVin, route)
                        .onComplete {
                            deferredTask.complete(Unit)
                        }.onFailure {
                            MBLoggerKit.e("Failed to send route to car via Backend: $it")
                            deferredTask.fail(it)
                        }
                }
                else -> {
                    MBLoggerKit.e("Route S2C impossible -> required capabilities for route not present: ${carCapabilities.capabilities.joinToString(",")}")
                    deferredTask.fail(ResponseError.requestError(SendToCarNotPossibleError.Capability))
                }
            }
        }.onFailure {
            deferredTask.fail(it)
        }

        return deferredTask.futureTask()
    }

    override fun enableBluetooth(isEnabled: Boolean) {
        MBLoggerKit.d("Bluetooth enablement state changed: $isEnabled")
        isBluetoothEnabled = isEnabled
    }

    private fun isPoiSendingAvailable(carCapabilities: SendToCarCapabilities) =
        carCapabilities.capabilities.contains(SendToCarCapability.SINGLE_POI_BACKEND) ||
            (isBluetoothEnabled && carCapabilities.capabilities.contains(SendToCarCapability.SINGLE_POI_BLUETOOTH))

    private fun isRouteSendingAvailable(
        capabilities: List<SendToCarCapability>,
        routeType: RouteType
    ) =
        routeType == RouteType.STATIC_ROUTE && capabilities.contains(SendToCarCapability.STATIC_ROUTE_BACKEND) ||
            routeType == RouteType.DYNAMIC_ROUTE && capabilities.contains(SendToCarCapability.DYNAMIC_ROUTE_BACKEND)
}
