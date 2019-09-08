package com.daimler.mbmobilesdk.tou.natcon

import android.app.Application
import com.daimler.mbmobilesdk.registration.LoginUser
import com.daimler.mbingresskit.common.UserAgreementUpdates
import com.daimler.mbuikit.eventbus.EventBus
import com.daimler.mbuikit.eventbus.Observes
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent

internal class NatconLoginViewModel(
    app: Application,
    private val user: LoginUser
) : BaseNatconViewModel(app, user.userLocale.countryCode) {

    internal val natconConfirmedEvent = MutableLiveEvent<LoginUser>()

    init {
        EventBus.createStation(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.dismount(this)
    }

    override fun onConfirmation(updates: UserAgreementUpdates) {
        natconConfirmedEvent.sendEvent(user.copy(pendingNatconAgreements = updates))
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