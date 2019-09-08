package com.daimler.mbmobilesdk.jumio.dialogs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.daimler.mbmobilesdk.tou.custom.CustomAgreementsService
import com.daimler.mbmobilesdk.tou.custom.CustomUserAgreements
import com.daimler.mbmobilesdk.utils.extensions.format
import com.daimler.mbmobilesdk.utils.extensions.hasPdf
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.CustomUserAgreement
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbuikit.components.viewmodels.MBGenericDialogViewModel
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf
import java.util.*

class JumioTermsDialogViewModel(
    title: String?,
    message: String?,
    positiveButtonText: String?,
    negativeButtonText: String?,
    val termsAccepted: Boolean
) : MBGenericDialogViewModel(title, message, positiveButtonText, negativeButtonText) {

    internal val positiveClickEvent = MutableLiveUnitEvent()
    internal val negativeClickEvent = MutableLiveUnitEvent()
    internal val errorEvent = MutableLiveEvent<ResponseError<out RequestError>?>()
    internal val showPdfAgreementsEvent = MutableLiveEvent<String>()
    internal val showWebAgreementsEvent = MutableLiveEvent<String>()

    internal val progressVisible = mutableLiveDataOf(false)
    internal val termsAcceptedLiveData = mutableLiveDataOf(termsAccepted)

    private val agreements = MutableLiveData<AgreementsLoadingResult>()

    private val customService = CustomAgreementsService(MBIngressKit.userService())

    init {
        loadCustomTermsOfUse()
    }

    private fun loadCustomTermsOfUse() {
        val locale = Locale.getDefault()
        customService.fetchAgreements(locale.format(), locale.country.toUpperCase(), false)
            .onComplete {
                agreements.postValue(AgreementsLoadingResult(it, null))
            }.onFailure {
                agreements.postValue(AgreementsLoadingResult(null, it))
            }
    }

    fun onCheckClicked() {
        termsAcceptedLiveData.postValue(termsAcceptedLiveData.value?.not())
    }

    fun onNegativeButtonClicked() {
        negativeClickEvent.sendEvent()
    }

    fun onPositiveButtonClicked() {
        positiveClickEvent.sendEvent()
    }

    fun onTermsSelected() {
        showAgreementsIfPossibleOrWait { tou }
    }

    fun onPrivacyStatementSelected() {
        showAgreementsIfPossibleOrWait { jumio }
    }

    private fun showAgreementsIfPossibleOrWait(accessor: CustomUserAgreements.() -> CustomUserAgreement?) {
        if (!handleAgreementsResponseIfAvailable(accessor)) waitForAgreements(accessor)
    }

    private fun handleAgreementsResponseIfAvailable(accessor: CustomUserAgreements.() -> CustomUserAgreement?): Boolean {
        val result = agreements.value
        return result?.let { r ->
            when {
                r.error != null -> {
                    errorEvent.sendEvent(r.error)
                    true
                }
                r.agreements != null -> {
                    val customAgreement = r.agreements.accessor()
                    customAgreement?.let { agreement ->
                        if (agreement.hasPdf()) {
                            agreement.filePath?.let {
                                showPdfAgreementsEvent.sendEvent(it)
                            } ?: errorEvent.sendEvent(null)
                        } else {
                            showWebAgreementsEvent.sendEvent(agreement.originalUrl)
                        }
                    } ?: errorEvent.sendEvent(null)
                    true
                }
                else -> {
                    errorEvent.sendEvent(null)
                    true
                }
            }
        } ?: false
    }

    private fun waitForAgreements(accessor: CustomUserAgreements.() -> CustomUserAgreement?) {
        val observer = object : Observer<AgreementsLoadingResult> {
            override fun onChanged(t: AgreementsLoadingResult?) {
                agreements.removeObserver(this)
                handleAgreementsResponseIfAvailable(accessor)
            }
        }
        agreements.observeForever(observer)
    }

    private data class AgreementsLoadingResult(
        val agreements: CustomUserAgreements?,
        val error: ResponseError<out RequestError>?
    )
}