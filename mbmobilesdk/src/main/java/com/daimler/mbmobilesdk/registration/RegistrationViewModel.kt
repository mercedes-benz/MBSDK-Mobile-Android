package com.daimler.mbmobilesdk.registration

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.app.format
import com.daimler.mbmobilesdk.profile.BaseUserLiveEditViewModel
import com.daimler.mbmobilesdk.profile.UserCreator
import com.daimler.mbmobilesdk.profile.creator.DynamicProfileCreator
import com.daimler.mbmobilesdk.profile.fields.*
import com.daimler.mbmobilesdk.profile.format.ProfileFieldValueFormatter
import com.daimler.mbmobilesdk.tou.ciam.CiamAgreementsService
import com.daimler.mbmobilesdk.tou.ciam.CiamUserAgreements
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.generalError
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbmobilesdk.utils.extensions.userInputErrorMessage
import com.daimler.mbmobilesdk.vehiclestage.StageConfig
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.CiamUserAgreement
import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbingresskit.common.User
import com.daimler.mbnetworkkit.MBNetworkKit
import com.daimler.mbnetworkkit.networking.HttpError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

internal class RegistrationViewModel(
    app: Application,
    private val loginUser: LoginUser,
    val stageConfig: StageConfig
) : BaseUserLiveEditViewModel<ProfileView>(app) {

    val bottomProgressVisible = mutableLiveDataOf(false)
    val generalProgressVisible = mutableLiveDataOf(false)
    val contentAvailable = mutableLiveDataOf(false)

    val itemsCreatedEvent = MutableLiveEvent<ItemsCreatedEvent>()
    val registrationError = MutableLiveEvent<String>()
    val navigateToVerification = MutableLiveEvent<LoginUser>()
    val showAgreementsEvent = MutableLiveEvent<CiamUserAgreement>()
    val legalErrorEvent = MutableLiveEvent<String>()
    val alreadyRegisteredError = MutableLiveEvent<LoginUser>()
    val showMmeIdInfoEvent = MutableLiveUnitEvent()
    val showLegalEvent = MutableLiveUnitEvent()

    private val profileViews = mutableListOf<ProfileView>()

    private val ciamService = CiamAgreementsService(MBIngressKit.userService())

    private val agreements = MutableLiveData<AgreementsLoadingResult>()

    override val userCreator: UserCreator = UserCreator(
        loginUser.user,
        loginUser.isMail,
        loginUser.userLocale.countryCode,
        loginUser.userLocale.format() // TODO userLocale.languageCode when locale selection adjusted
    )

    fun onFragmentInit() {
        profileViews.clear()
        showGeneralProgress()
        loadFields()
        loadCiamTermsOfUse()
    }

    fun onMmeIdClicked() {
        showMmeIdInfoEvent.sendEvent()
    }

    fun onRegisterClicked() {
        if (handleInput()) {
            register()
        }
    }

    override fun onCleared() {
        super.onCleared()
        profileViews.clear()
    }

    fun onLegalClicked() {
        showLegalEvent.sendEvent()
    }

    override fun onProfileFieldsLoaded(fieldsData: ProfileFieldsData) {
        super.onProfileFieldsLoaded(fieldsData)
        dismissGeneralProgress()
    }

    override fun onProfileFieldsLoadingFailed(error: ResponseError<out RequestError>?) {
        super.onProfileFieldsLoadingFailed(error)
        dismissGeneralProgress()
    }

    override fun prepareProfileFields(fieldsData: ProfileFieldsData): List<ProfileField> {
        val fields = ProfileFieldPreparationImpl(fieldsData, false).fields()
        setStandardCommunicationPreferences(fields, loginUser.isMail)
        return fields
    }

    override fun getResolutionStrategy(): ProfileFieldResolutionStrategy {
        return ResolutionMandatoryBuilder().apply {
            // Show account identifier based on the user input.
            if (loginUser.isMail) {
                include<ProfileField.Email>()
            } else {
                include<ProfileField.MobilePhone>()
            }

            // Always show title.
            include<ProfileField.Title>()

            // Exclude communication preferences and MarketCountryCode.
            exclude<ProfileField.ContactPerMail>()
            exclude<ProfileField.ContactPerPhone>()
            exclude<ProfileField.ContactPerLetter>()
            exclude<ProfileField.ContactPerSms>()
            exclude<ProfileField.MarketCountryCode>()
        }.build()
    }

    override fun getItemsCreator(
        app: Application,
        user: User,
        resolutionStrategy: ProfileFieldResolutionStrategy,
        formatter: ProfileFieldValueFormatter
    ): DynamicProfileCreator<ProfileView> {
        return RegistrationProfileItemsCreator(app, this, user,
            resolutionStrategy, formatter, loginUser.isMail)
    }

    override fun onItemsCreated(items: List<ProfileView>) {
        profileViews.clear()
        profileViews.addAll(items)
        itemsCreatedEvent.sendEvent(ItemsCreatedEvent(profileViews, loginUser.isMail))
        contentAvailable.postValue(true)
    }

    internal fun onEulaSelected() {
        showAgreementsIfPossibleOrWait { accessConditions }
    }

    internal fun onDataProtectionSelected() {
        showAgreementsIfPossibleOrWait { dataPrivacy }
    }

    private fun loadCiamTermsOfUse() {
        val locale = MBMobileSDK.userLocale()
        ciamService.fetchAgreements(locale.format(), locale.countryCode.toUpperCase(), false)
            .onComplete {
                agreements.postValue(AgreementsLoadingResult(it, null))
            }.onFailure {
                agreements.postValue(AgreementsLoadingResult(null, it))
            }
    }

    inline fun <reified T> onUpdateProfileItems() {
        updateProfileItems(user(), profileViews) { it.associatedField() is T }
    }

    /**
     * This method handles 3 cases:
     *  1. Agreements are already loaded -> just show them.
     *  2. Requesting of agreements failed -> show error.
     *  3. Wait for the loading to finish.
     */
    private fun showAgreementsIfPossibleOrWait(accessor: CiamUserAgreements.() -> CiamUserAgreement?) {
        if (!handleAgreementsResponseIfAvailable(accessor)) waitForAgreements(accessor)
    }

    private fun handleAgreementsResponseIfAvailable(accessor: CiamUserAgreements.() -> CiamUserAgreement?): Boolean {
        val result = agreements.value
        return result?.let { r ->
            when {
                r.error != null -> {
                    legalErrorEvent.sendEvent(defaultErrorMessage(r.error))
                    true
                }
                r.agreements != null -> {
                    val ciamAgreement = r.agreements.accessor()
                    ciamAgreement?.let { showAgreementsEvent.sendEvent(it) }
                        ?: legalErrorEvent.sendEvent(generalError())
                    true
                }
                else -> {
                    legalErrorEvent.sendEvent(generalError())
                    true
                }
            }
        } ?: false
    }

    private fun waitForAgreements(accessor: CiamUserAgreements.() -> CiamUserAgreement?) {
        val observer = object : Observer<AgreementsLoadingResult> {
            override fun onChanged(t: AgreementsLoadingResult?) {
                agreements.removeObserver(this)
                dismissGeneralProgress()
                handleAgreementsResponseIfAvailable(accessor)
            }
        }
        agreements.observeForever(observer)
        showGeneralProgress()
    }

    private fun setStandardCommunicationPreferences(fields: List<ProfileField>, isMail: Boolean) {
        userCreator.updateCommunicationPreferences(
            contactByMail = isMail,
            contactByPhone = !isMail,
            contactBySms = !isMail && fields.containsFieldType<ProfileField.ContactPerSms>(),
            contactByLetter = false
        )
    }

    private fun register() {
        showBottomProgress()
        val registrationUser = userCreator.user()
        MBNetworkKit.headerService().updateNetworkLocale(registrationUser.languageCode)
        MBIngressKit.userService().createUser(
            loginUser.isMail,
            registrationUser
        ).onComplete { user ->
            navigateToVerification.sendEvent(LoginUser(user.userName, loginUser.isMail, loginUser.userLocale))
        }.onFailure {
            MBLoggerKit.re("Registration failed.", it)
            notifyCreateUserError(it)
        }.onAlways { _, _, _ ->
            dismissBottomProgress()
        }
    }

    private fun showBottomProgress() {
        bottomProgressVisible.postValue(true)
    }

    private fun dismissBottomProgress() {
        bottomProgressVisible.postValue(false)
    }

    private fun showGeneralProgress() {
        generalProgressVisible.postValue(true)
    }

    private fun dismissGeneralProgress() {
        generalProgressVisible.postValue(false)
    }

    private fun notifyCreateUserError(error: ResponseError<out RequestError>?) {
        if (error?.requestError is HttpError.Conflict) {
            alreadyRegisteredError.sendEvent(loginUser)
        } else {
            registrationError.sendEvent(userInputErrorMessage(error))
        }
    }

    private inline fun <reified T : ProfileField> List<ProfileField>.containsFieldType() =
        any { it is T }

    data class ItemsCreatedEvent(
        val items: List<ProfileView>,
        val isMailIdentifier: Boolean
    )

    private data class AgreementsLoadingResult(
        val agreements: CiamUserAgreements?,
        val error: ResponseError<out RequestError>?
    )
}