package com.daimler.mbmobilesdk.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.app.Endpoint
import com.daimler.mbmobilesdk.app.Region
import com.daimler.mbmobilesdk.app.Stage
import com.daimler.mbmobilesdk.utils.emptyVehicleInfo
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.UserAgreementUpdates
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

class MBLoginViewModel(
    app: Application,
    stageSelectorEnabled: Boolean
) : AndroidViewModel(app) {

    val toolbarVisible = mutableLiveDataOf(true)
    val toolbarTitle = MutableLiveData<String>()

    val endpointSelectorEnabled = mutableLiveDataOf(stageSelectorEnabled)
    val currentEndpoint = MutableLiveData<String>()

    val stageSelectorSelectedEvent = MutableLiveUnitEvent()
    val showServiceActivationEvent = MutableLiveEvent<VehicleInfo>()

    init {
        updateStageTitle(MBMobileSDK.mobileSdkSettings().endPoint.get())
    }

    fun onStageSelectorClicked() = stageSelectorSelectedEvent.sendEvent()

    fun enablePushesInSettings() {
        MBMobileSDK.pushSettings().pushEnabled.set(true)
    }

    internal fun updateAgreements(updates: UserAgreementUpdates) {
        MBIngressKit.refreshTokenIfRequired()
            .onComplete {
                MBLoggerKit.d("Updating agreements.")
                val jwt = it.jwtToken.plainToken
                // TODO update
            }.onFailure {
                MBLoggerKit.e("Failed to refresh token.", throwable = it)
            }
    }

    internal fun vehicleAssigned(finOrVin: String) {
        val vehicle = MBCarKit.loadVehicleByVin(finOrVin) ?: emptyVehicleInfo(finOrVin)
        showServiceActivationEvent.sendEvent(vehicle)
    }

    internal fun updateSelectedEndpoint(region: Region, stage: Stage) {
        updateStageTitle(Endpoint(region, stage))
    }

    private fun updateStageTitle(endpoint: Endpoint) =
        currentEndpoint.postValue("${endpoint.region.displayName}-${endpoint.stage.displayName}")
}