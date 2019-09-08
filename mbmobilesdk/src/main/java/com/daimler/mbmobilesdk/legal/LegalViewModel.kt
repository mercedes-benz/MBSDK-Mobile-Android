package com.daimler.mbmobilesdk.legal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbmobilesdk.app.format
import com.daimler.mbmobilesdk.featuretoggling.FLAG_NATCON_TERMS_OF_USE
import com.daimler.mbmobilesdk.tou.custom.*
import com.daimler.mbmobilesdk.tou.natcon.isNatconCountry
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.isLoggedIn
import com.daimler.mbmobilesdk.utils.extensions.postFromHtml
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.CustomUserAgreement
import com.daimler.mbuikit.eventbus.EventBus
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.getString
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

internal class LegalViewModel(app: Application) : AndroidViewModel(app) {

    val contentVisible = mutableLiveDataOf(false)
    val progressVisible = mutableLiveDataOf(false)
    val imprintSpinnerVisible = mutableLiveDataOf(false)
    val natconVisible = MutableLiveData<Boolean>()
    val soeVisibile = mutableLiveDataOf(false)

    val imprint = MutableLiveData<CharSequence>()
    val appDescriptionTitle = MutableLiveData<String>()
    val termsOfUseTitle = MutableLiveData<String>()
    val dataProtectionTitle = MutableLiveData<String>()
    val foreignTitle = MutableLiveData<String>()
    val legalHintsTitle = MutableLiveData<String>()

    val showNatconEvent = MutableLiveUnitEvent()
    val showSoeEvent = MutableLiveUnitEvent()
    val showLicensesEvent = MutableLiveUnitEvent()
    val showHtmlEvent = MutableLiveEvent<HtmlUserAgreementContent>()
    val showSupportEvent = MutableLiveUnitEvent()
    val errorEvent = MutableLiveEvent<String>()

    private val htmlLoader: HtmlLoadingUserAgreementsService<CustomUserAgreement, CustomUserAgreements> =
        CustomAgreementsHtmlLoader(CustomAgreementsService(MBIngressKit.userService()))

    private val loading: Boolean
        get() = progressVisible.value == true

    init {
        setSoeVisibility()
        setNatconVisibility()
        loadCustomTermsOfUse(MBMobileSDK.userLocale())
        EventBus.createStation(this)
    }

    private fun setSoeVisibility() {
        soeVisibile.postValue(MBIngressKit.authenticationService().isLoggedIn())
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.dismount(this)
    }

    fun onAppDescriptionClicked() {
        showHtml { appDescription }
    }

    fun onTermsOfUseClicked() {
        showHtml { accessConditions }
    }

    fun onSoeClicked() {
        showSoeEvent.sendEvent()
    }

    fun onDataProtectionClicked() {
        showHtml { dataPrivacy }
    }

    fun onForeignClicked() {
        showHtml { foreign }
    }

    fun onLegalHintsClicked() {
        showHtml { legalNotices }
    }

    fun onNatconClicked() {
        showNatconEvent.sendEvent()
    }

    fun onLicensesClicked() {
        showLicensesEvent.sendEvent()
    }

    fun onSupportClicked() {
        showSupportEvent.sendEvent()
    }

    private fun setNatconVisibility() {
        val visible = MBMobileSDK.featureToggleService().isToggleEnabled(FLAG_NATCON_TERMS_OF_USE) &&
            isNatconCountry(MBMobileSDK.userLocale().countryCode)
        natconVisible.postValue(visible)
    }

    private fun loadCustomTermsOfUse(locale: UserLocale) {
        onLoadingStarted()
        htmlLoader.fetchAgreements(locale.format(), locale.countryCode, true)
            .onComplete {
                appDescriptionTitle.postValue(it.appDescription?.displayName
                    ?: getString(R.string.legal_app_description))
                termsOfUseTitle.postValue(it.accessConditions?.displayName
                    ?: getString(R.string.legal_terms_of_use))
                dataProtectionTitle.postValue(it.dataPrivacy?.displayName
                    ?: getString(R.string.legal_data_protection))
                foreignTitle.postValue(it.foreign?.displayName
                    ?: getString(R.string.legal_foreign))
                legalHintsTitle.postValue(it.legalNotices?.displayName
                    ?: getString(R.string.legal_legal_hints))
                loadImprint(locale)
                contentVisible.postValue(true)
            }.onFailure {
                errorEvent.sendEvent(defaultErrorMessage(it))
            }.onAlways { _, _, _ ->
                onLoadingFinished()
            }
    }

    private fun loadImprint(locale: UserLocale) {
        imprintSpinnerVisible.postValue(true)

        htmlLoader.loadHtmlContent(locale.format(), locale.countryCode) { imprint }
            .onComplete { setImprint(it) }
            .onFailure {
                MBLoggerKit.re("Failed to load imprint.", it)
            }.onAlways { _, _, _ -> imprintSpinnerVisible.postValue(false) }
    }

    private fun setImprint(htmlContent: HtmlUserAgreementContent) {
        imprint.postFromHtml(htmlContent.htmlContent)
    }

    private fun showHtml(accessor: CustomUserAgreements.() -> CustomUserAgreement?) {
        if (loading) return
        val locale = MBMobileSDK.userLocale()
        onLoadingStarted()
        htmlLoader.loadHtmlContent(locale.format(), locale.countryCode, accessor)
            .onComplete { notifyShowHtml(it) }
            .onFailure { errorEvent.sendEvent(defaultErrorMessage(it)) }
            .onAlways { _, _, _ -> onLoadingFinished() }
    }

    private fun notifyShowHtml(htmlContent: HtmlUserAgreementContent) {
        showHtmlEvent.sendEvent(htmlContent)
    }

    private fun onLoadingStarted() {
        progressVisible.postValue(true)
    }

    private fun onLoadingFinished() {
        progressVisible.postValue(false)
    }
}