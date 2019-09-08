package com.daimler.mbmobilesdk.tou.natcon

import android.app.Application
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.UserAgreementUpdates
import com.daimler.mbuikit.eventbus.EventBus
import com.daimler.mbuikit.eventbus.Observes
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent

internal class NatconLegalViewModel(
    app: Application,
    countryCode: String
) : BaseNatconViewModel(app, countryCode) {

    val natconUpdatedEvent = MutableLiveUnitEvent()

    init {
        EventBus.createStation(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.dismount(this)
    }

    override fun onConfirmation(updates: UserAgreementUpdates) {
        onAcceptingStarted()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete {
                val jwt = it.jwtToken.plainToken
                // TODO update agreements
                natconUpdatedEvent.sendEvent()
            }.onFailure {
                MBLoggerKit.e("Failed to refresh token.", throwable = it)
                errorEvent.sendEvent(defaultErrorMessage(it))
            }.onAlways { _, _, _ ->
                // TODO move when update request is finished
                onAcceptingFinished()
            }
    }

    @Observes
    @Suppress("UNUSED")
    private fun onTermsAndConditionsAcceptedEvent(event: NatconAcceptedEvent) {
        changeAcceptanceState(event.id, event.accepted)
    }

    @Observes
    @Suppress("UNUSED")
    private fun onReadTermsAndConditionsEvent(event: NatconReadEvent) {
        readAgreement(event.id)
    }
}