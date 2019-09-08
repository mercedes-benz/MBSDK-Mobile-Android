package com.daimler.mbmobilesdk.vehicleassignment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

internal class RifViewModel(
    app: Application,
    private val finOrVin: String,
    val checkActivationButtonEnabled: Boolean
) : AndroidViewModel(app) {

    val progressVisible = mutableLiveDataOf(false)
    val closeScreenEvent = MutableLiveUnitEvent()
    val errorEvent = MutableLiveEvent<String>()
    val vehicleActivationEvent = MutableLiveEvent<VehicleActivationEvent>()

    fun onCloseClicked() {
        closeScreenEvent.sendEvent()
    }

    fun onCheckActivationClicked() {
        progressVisible.postValue(true)
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBCarKit.vehicleService().fetchVehicles(jwt)
                    .onComplete { vehicles ->
                        val vehicleToCheck =
                            vehicles.vehicles.firstOrNull { it.finOrVin == finOrVin }
                        vehicleToCheck?.let {
                            val activated = it.trustLevel > TRUST_LEVEL_LOW
                            val event = VehicleActivationEvent(it, activated)
                            vehicleActivationEvent.sendEvent(event)
                        }
                    }.onFailure {
                        MBLoggerKit.re("Failed to load vehicles.", it)
                        errorEvent.sendEvent(defaultErrorMessage(it))
                    }.onAlways { _, _, _ -> progressVisible.postValue(false) }
            }.onFailure {
                MBLoggerKit.e("Failed to refresh token.", throwable = it)
                progressVisible.postValue(false)
                errorEvent.sendEvent(defaultErrorMessage(it))
            }
    }

    internal data class VehicleActivationEvent(val vehicle: VehicleInfo?, val isActivated: Boolean)

    private companion object {

        private const val TRUST_LEVEL_LOW = 1
    }
}