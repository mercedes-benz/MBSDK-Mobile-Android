package com.daimler.mbmobilesdk.tou.soe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.logic.UserTask
import com.daimler.mbmobilesdk.tou.AcceptableAgreementsService
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.SoeUserAgreement
import com.daimler.mbingresskit.common.User
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.eventbus.EventBus
import com.daimler.mbuikit.eventbus.Observes
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.getString
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

internal class SoeAgreementsViewModel(app: Application) : AndroidViewModel(app) {

    val loading = mutableLiveDataOf(false)
    val items = MutableLiveArrayList<SoeRecyclerItem>()
    val itemsVisible = mutableLiveDataOf(false)

    val readSoeEvent = MutableLiveEvent<String>()
    val updateEvent = MutableLiveEvent<String>()
    val cancelEvent = MutableLiveUnitEvent()
    val rejectEvent = MutableLiveEvent<String>()
    val errorEvent = MutableLiveEvent<String>()

    private val isLoading: Boolean
        get() = loading.value == true

    private val service: AcceptableAgreementsService<SoeUserAgreement> =
        SoeAgreementsService(MBIngressKit.userService())

    private val itemsObserver = Observer<List<*>> {
        itemsVisible.postValue(it != null && it.isNotEmpty())
    }

    init {
        items.observeForever(itemsObserver)
        loadSoeTerms()

        EventBus.createStation(this)
    }

    override fun onCleared() {
        super.onCleared()
        items.removeObserver(itemsObserver)
        EventBus.dismount(this)
    }

    fun onSaveClicked() {

        when {
            isLoading -> Unit
            service.didAgreementStatesChange().not() ->
                updateEvent.sendEvent(getString(R.string.agreements_updated_successful))
            service.mandatoryAgreementsAccepted() -> {
                disableAll()
                onLoadingStarted()
                MBIngressKit.refreshTokenIfRequired()
                    .onComplete { executeSendSoeTerms(it.jwtToken.plainToken) }
                    .onFailure {
                        MBLoggerKit.e("Failed to refresh token.", throwable = it)
                        onLoadingFinished()
                        enableAll()
                        errorEvent.sendEvent(defaultErrorMessage(it))
                    }
            }
            else -> rejectEvent.sendEvent(getString(R.string.agreements_reject_message))
        }
    }

    fun onLaterClicked() {
        if (!isLoading) cancelEvent.sendEvent()
    }

    fun rejectTermsAndConditions() {
        disableAll()
        onLoadingStarted()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete {
                service.disagreeToAll()
                executeSendSoeTerms(it.jwtToken.plainToken)
            }.onFailure {
                MBLoggerKit.e("Failed to refresh token.", throwable = it)
                onLoadingFinished()
                enableAll()
                errorEvent.sendEvent(defaultErrorMessage(it))
            }
    }

    private fun onLoadingStarted() {
        loading.postValue(true)
    }

    private fun onLoadingFinished() {
        loading.postValue(false)
    }

    private fun loadSoeTerms() {
        onLoadingStarted()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                UserTask().fetchUser()
                    .onComplete {
                        executeLoadSoeTerms(token.jwtToken.plainToken, it.user)
                    }.onFailure {
                        MBLoggerKit.e("Failed to fetch user.", throwable = it)
                        onLoadingFinished()
                        errorEvent.sendEvent(defaultErrorMessage(it))
                    }
            }.onFailure {
                MBLoggerKit.e("Failed to refresh token.", throwable = it)
                onLoadingFinished()
                errorEvent.sendEvent(defaultErrorMessage(it))
            }
    }

    private fun executeLoadSoeTerms(token: String, user: User) {
        service.loadAgreements(token, user.countryCode)
            .onComplete {
                items.apply {
                    value.clear()
                    value.addAll(SoeItemsCreator().createSoeItems(getApplication(), it.agreements))
                    dispatchChange()
                }
                handleGeneralAcceptance(service.mandatoryAgreementsAccepted())
            }
            .onFailure {
                MBLoggerKit.re("Failed to load agreements.", it)
                errorEvent.sendEvent(defaultErrorMessage(it))
            }.onAlways { _, _, _ -> onLoadingFinished() }
    }

    private fun executeSendSoeTerms(token: String) {
        service.sendAgreements(token)
            .onComplete {
                updateEvent.sendEvent(getString(R.string.agreements_updated_successful))
            }.onFailure {
                MBLoggerKit.re("Failed to update ToU.", it)
                errorEvent.sendEvent(defaultErrorMessage(it))
            }.onAlways { _, _, _ ->
                onLoadingFinished()
                enableAll()
            }
    }

    private fun handleGeneralAcceptance(accepted: Boolean) {
        items.value
            .filterIsInstance<SoeAgreementItem>()
            .filter { !service.containsMandatoryAgreement(it.ids) }
            .forEach {
                it.updateEnabledState(accepted)
                if (!accepted) {
                    it.updateCheckedState(false)
                    service.changeAcceptanceStates(it.ids, false)
                }
            }
    }

    private fun disableAll() {
        items.value
            .filterIsInstance<SoeAgreementItem>()
            .forEach { it.updateEnabledState(false) }
    }

    private fun enableAll() {
        val all = service.mandatoryAgreementsAccepted()
        items.value
            .filterIsInstance<SoeAgreementItem>()
            .filter { all || service.containsMandatoryAgreement(it.ids) }
            .forEach { it.updateEnabledState(true) }
    }

    @Observes
    @Suppress("UNUSED")
    private fun onSoeAgreementAcceptanceStateEvent(event: SoeAgreementAcceptanceStateEvent) {
        val generalAccepted = service.changeAcceptanceStates(event.ids, event.accepted)
        handleGeneralAcceptance(generalAccepted)
    }

    @Observes
    @Suppress("UNUSED")
    private fun onSoeAgreementReadEvent(event: SoeAgreementReadEvent) {
        MBLoggerKit.d("Read ${event.id}.")
        val filePath = service.getFilePath(event.id)
        filePath?.let { readSoeEvent.sendEvent(it) }
    }
}