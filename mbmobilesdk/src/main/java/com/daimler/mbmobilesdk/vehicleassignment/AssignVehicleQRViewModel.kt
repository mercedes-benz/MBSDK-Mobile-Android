package com.daimler.mbmobilesdk.vehicleassignment

import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent

class AssignVehicleQRViewModel(hintText: String?) : QRCodeReaderViewModel(hintText) {

    val vehicleAssignedEvent = MutableLiveEvent<String>()
    val invalidCodeEvent = MutableLiveEvent<String>()
    val authErrorEvent = MutableLiveUnitEvent()
    val networkErrorEvent = MutableLiveUnitEvent()

    fun sendUrl(url: String) {
        MBLoggerKit.d("Sending url: $url")
        progressVisible.postValue(true)
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBCarKit.assignmentService()
                    .assignVehicleWithQrCode(jwt, url)
                    .onComplete {
                        MBLoggerKit.d("Assigned vehicle ${it.finOrVin}")
                        if (it.finOrVin.isNotEmpty()) {
                            vehicleAssignedEvent.sendEvent(it.finOrVin)
                        }
                    }
                    .onFailure {
                        MBLoggerKit.re("Failed to assign vehicle through QR code.", it)
                        it?.networkError?.let { networkErrorEvent.sendEvent() } ?: invalidCodeEvent.sendEvent(url)
                    }
                    .onAlways { _, _, _ -> progressVisible.postValue(false) }
            }
            .onFailure {
                MBLoggerKit.e("Error while refreshing token", throwable = it)
                progressVisible.postValue(false)
                authErrorEvent.sendEvent()
            }
    }
}