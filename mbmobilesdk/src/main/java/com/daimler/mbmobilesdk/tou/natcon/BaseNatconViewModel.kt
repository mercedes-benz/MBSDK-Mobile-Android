package com.daimler.mbmobilesdk.tou.natcon

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.tou.AcceptableAgreementsService
import com.daimler.mbmobilesdk.utils.LineDividerDecorator
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.hasPdf
import com.daimler.mbmobilesdk.utils.extensions.hasWebContent
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.NatconUserAgreement
import com.daimler.mbingresskit.common.UserAgreementUpdates
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.utils.extensions.getColor
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

internal abstract class BaseNatconViewModel(
    app: Application,
    private val countryCode: String
) : AndroidViewModel(app) {

    val loadingProgressVisible = mutableLiveDataOf(false)
    val approvingProgressVisible = mutableLiveDataOf(false)
    val items = MutableLiveArrayList<NatconItem>()
    val itemsLoaded = mutableLiveDataOf(false)
    val headline = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    val errorEvent = MutableLiveEvent<String>()
    val showPdfEvent = MutableLiveEvent<String>()
    val showWebEvent = MutableLiveEvent<String>()

    val divider = LineDividerDecorator(getColor(R.color.mb_divider_color))

    private val service: AcceptableAgreementsService<NatconUserAgreement> =
        NatconService(MBIngressKit.userService())

    init {
        loadTermsAndConditions()
    }

    fun onAllCheckedChanged(checked: Boolean) {
        if (checked) {
            service.agreeToAll()
            items.value.forEach { it.updateCheckedState(true) }
        }
    }

    fun onSaveClicked() {
        val rejectedMandatory = service.getRejectedMandatoryAgreements()
        if (rejectedMandatory.isNotEmpty()) {
            val ids = rejectedMandatory.map { it.documentId }
            items.value
                .filter { ids.contains(it.id) }
                .forEach { it.errorState = true }
        } else {
            val updates = service.currentAgreementUpdates()
            onConfirmation(updates)
        }
    }

    protected abstract fun onConfirmation(updates: UserAgreementUpdates)

    private fun loadTermsAndConditions() {
        onLoadingStarted()
        service.loadAgreements(countryCode)
            .onComplete {
                itemsLoaded.postValue(true)
                headline.postValue(getAgreementsHeadline(it.agreements))
                description.postValue(getAgreementsDescription(it.agreements))
                addNatconItems(it.agreements)
            }.onFailure {
                MBLoggerKit.re("Failed to load natcon ToU.", it)
                errorEvent.sendEvent(defaultErrorMessage(it))
            }.onAlways { _, _, _ ->
                onLoadingFinished()
            }
    }

    private fun addNatconItems(list: List<NatconUserAgreement>) {
        val items = list.map { NatconItem(it.documentId, getAgreementText(it)) }
        this.items.value.clear()
        this.items.addAllAndDispatch(items)
    }

    protected fun onLoadingStarted() {
        loadingProgressVisible.postValue(true)
    }

    protected fun onLoadingFinished() {
        loadingProgressVisible.postValue(false)
    }

    protected fun onAcceptingStarted() {
        approvingProgressVisible.postValue(true)
    }

    protected fun onAcceptingFinished() {
        approvingProgressVisible.postValue(false)
    }

    protected fun changeAcceptanceState(id: String, accepted: Boolean) {
        service.changeAcceptanceStates(listOf(id), accepted)
    }

    protected fun readAgreement(id: String) {
        val item = service.agreementForId(id)
        item?.let { agreement ->
            if (agreement.hasWebContent()) {
                showWebEvent.sendEvent(agreement.originalUrl)
            } else if (agreement.hasPdf()) {
                agreement.filePath?.let { showPdfEvent.sendEvent(it) }
            }
        }
    }

    private fun getAgreementsHeadline(agreements: List<NatconUserAgreement>) =
        if (agreements.isEmpty()) null else agreements.first().displayName

    private fun getAgreementsDescription(agreements: List<NatconUserAgreement>) =
        if (agreements.isEmpty()) null else agreements.first().description

    private fun getAgreementText(agreement: NatconUserAgreement): String =
        if (agreement.mandatory) "${agreement.text} *" else agreement.text
}