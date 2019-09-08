package com.daimler.mbmobilesdk.profile

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.logic.UserTask
import com.daimler.mbmobilesdk.login.ProfileLogoutState
import com.daimler.mbmobilesdk.profile.creator.DynamicProfileCreator
import com.daimler.mbmobilesdk.profile.creator.DynamicProfileViewCreator
import com.daimler.mbmobilesdk.profile.fields.*
import com.daimler.mbmobilesdk.profile.format.ProfileFieldValueFormatter
import com.daimler.mbmobilesdk.utils.LineDividerDecorator
import com.daimler.mbmobilesdk.utils.extensions.*
import com.daimler.mbmobilesdk.utils.resizeBitmap
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.CommunicationPreference
import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.common.UserPinStatus
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.getColor
import com.daimler.mbuikit.utils.extensions.getString
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf
import com.daimler.mbuikit.utils.extensions.toByteArray
import java.text.SimpleDateFormat
import java.util.*

internal class ProfileViewModel(app: Application) : BaseProfileFieldViewModel<ProfileView>(app) {

    val imageBitmap = MutableLiveData<Bitmap>()

    val progressVisible = MutableLiveData<Boolean>()
    val headerVisible = mutableLiveDataOf(false)
    val logoutVisible = mutableLiveDataOf(false)

    val name = MutableLiveData<String>()
    val mercedesMeId = MutableLiveData<String>()

    val itemsCreatedEvent = MutableLiveEvent<List<ProfileView>>()
    val editEvent = MutableLiveEvent<UserEditEvent>()
    val logoutRequestEvent = MutableLiveUnitEvent()
    val deleteAccountEvent = MutableLiveUnitEvent()
    val logoutStateEvent = MutableLiveEvent<ProfileLogoutState>()
    val imageEvent = MutableLiveUnitEvent()
    val userUpdateError = MutableLiveEvent<String>()
    val authenticationError = MutableLiveEvent<String>()
    val errorEvent = MutableLiveEvent<String>()
    val userChangedEvent = MutableLiveEvent<User>()
    val userPictureChangedEvent = MutableLiveEvent<ByteArray>()

    private val profileViews = mutableListOf<ProfileView>()

    private var logoutPending = false
    private var deleteAccountPending = false
    private var user: User? = null

    init {
        loadData()
    }

    override fun onCleared() {
        super.onCleared()
        profileViews.clear()
    }

    fun getDecorator() = LineDividerDecorator(getColor(R.color.mb_divider_color))

    fun onLogoutClicked() {
        if (!logoutPending) logoutRequestEvent.sendEvent()
    }

    fun onDeleteAccountClicked() {
        if (!deleteAccountPending) deleteAccountEvent.sendEvent()
    }

    override fun onProfileFieldsLoaded(fieldsData: ProfileFieldsData) {
        super.onProfileFieldsLoaded(fieldsData)
        progressVisible.postValue(false)
    }

    override fun onProfileFieldsLoadingFailed(error: ResponseError<out RequestError>?) {
        super.onProfileFieldsLoadingFailed(error)
        MBLoggerKit.re("Failed to load profile fields.", error)
        progressVisible.postValue(false)
        errorEvent.sendEvent(defaultErrorMessage(error))
    }

    override fun prepareProfileFields(fieldsData: ProfileFieldsData): List<ProfileField> {
        return ProfileFieldPreparationImpl(
            fieldsData,
            true,
            ProfileFieldPreparationImpl.CombinationOption.Name,
            ProfileFieldPreparationImpl.CombinationOption.Address,
            ProfileFieldPreparationImpl.CombinationOption.AdaptionValues,
            ProfileFieldPreparationImpl.CombinationOption.Communication
        ).fields()
    }

    override fun getResolutionStrategy(): ProfileFieldResolutionStrategy {
        return ResolutionAllBuilder()
            .exclude<ProfileField.Title>()
            .exclude<ProfileField.Salutation>()
            .exclude<ProfileField.MarketCountryCode>()
            .build()
    }

    override fun getItemsCreator(
        app: Application,
        user: User,
        resolutionStrategy: ProfileFieldResolutionStrategy,
        formatter: ProfileFieldValueFormatter
    ): DynamicProfileCreator<ProfileView> {
        return DynamicProfileViewCreator(getApplication(), this, user,
            resolutionStrategy, formatter, false)
    }

    override fun onItemsCreated(items: List<ProfileView>) {
        itemsCreated(items)
    }

    private fun itemsCreated(items: List<ProfileView>) {
        profileViews.clear()
        profileViews.addAll(items)
        itemsCreatedEvent.sendEvent(items)
    }

    override fun addActionCallback(profileField: ProfileField.AccountIdentifier, viewable: ProfileViewable) {
        // --
    }

    override fun addActionCallback(profileField: ProfileField.Email, viewable: ProfileViewable) {
        // viewable.withUserClickEvent(ID_MAIL)
    }

    override fun addActionCallback(profileField: ProfileField.Salutation, viewable: ProfileViewable) {
        // --
    }

    override fun addActionCallback(profileField: ProfileField.Title, viewable: ProfileViewable) {
        // --
    }

    override fun addActionCallback(profileField: ProfileField.FirstName, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_NAME)
    }

    override fun addActionCallback(profileField: ProfileField.LastName, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_NAME)
    }

    override fun addActionCallback(profileField: ProfileField.MarketCountryCode, viewable: ProfileViewable) {
        // --
    }

    override fun addActionCallback(profileField: ProfileField.LanguageCode, viewable: ProfileViewable) {
        if (profileField.externalSelection) {
            viewable.withUserClickEvent(ID_LANGUAGE_CODE)
        } else {
            viewable.withAction {
                if (it.orEmpty() != user?.languageCode) {
                    if (!it.isNullOrBlank()) {
                        val tmp = user?.copy(languageCode = it)
                        tmp?.let { user ->
                            updateUser(user)
                        }
                    }
                }
            }
        }
    }

    override fun addActionCallback(profileField: ProfileField.Birthday, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_BIRTHDAY)
    }

    override fun addActionCallback(profileField: ProfileField.MobilePhone, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_PHONE)
    }

    override fun addActionCallback(profileField: ProfileField.LandlinePhone, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_PHONE)
    }

    override fun addActionCallback(profileField: ProfileField.AddressCountryCode, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADDRESS)
    }

    override fun addActionCallback(profileField: ProfileField.AddressStreet, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADDRESS)
    }

    override fun addActionCallback(profileField: ProfileField.AddressStreetType, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADDRESS)
    }

    override fun addActionCallback(profileField: ProfileField.AddressHouseName, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADDRESS)
    }

    override fun addActionCallback(profileField: ProfileField.AddressState, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADDRESS)
    }

    override fun addActionCallback(profileField: ProfileField.AddressProvince, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADDRESS)
    }

    override fun addActionCallback(profileField: ProfileField.AddressFloorNumber, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADDRESS)
    }

    override fun addActionCallback(profileField: ProfileField.AddressDoorNumber, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADDRESS)
    }

    override fun addActionCallback(profileField: ProfileField.AddressHouseNumber, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADDRESS)
    }

    override fun addActionCallback(profileField: ProfileField.AddressZipCode, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADDRESS)
    }

    override fun addActionCallback(profileField: ProfileField.AddressCity, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADDRESS)
    }

    override fun addActionCallback(profileField: ProfileField.AddressLine1, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADDRESS)
    }

    override fun addActionCallback(profileField: ProfileField.AddressPostOfficeBox, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADDRESS)
    }

    override fun addActionCallback(profileField: ProfileField.TaxNumber, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_TAX_NUMBER)
    }

    override fun addActionCallback(profileField: ProfileField.BodyHeight, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADAPTION_VALUES)
    }

    override fun addActionCallback(profileField: ProfileField.PreAdjustment, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADAPTION_VALUES)
    }

    override fun addActionCallback(profileField: ProfileField.ContactPerMail, viewable: ProfileViewable) {
        viewable.withUserAction { user, value ->
            updateCommunicationPreferencesIfNecessary(user) {
                it.copy(contactByMail = value != null)
            }
        }
    }

    override fun addActionCallback(profileField: ProfileField.ContactPerPhone, viewable: ProfileViewable) {
        viewable.withUserAction { user, value ->
            updateCommunicationPreferencesIfNecessary(user) {
                it.copy(contactByPhone = value != null)
            }
        }
    }

    override fun addActionCallback(profileField: ProfileField.ContactPerSms, viewable: ProfileViewable) {
        viewable.withUserAction { user, value ->
            updateCommunicationPreferencesIfNecessary(user) {
                it.copy(contactBySms = value != null)
            }
        }
    }

    override fun addActionCallback(profileField: ProfileField.ContactPerLetter, viewable: ProfileViewable) {
        viewable.withUserAction { user, value ->
            updateCommunicationPreferencesIfNecessary(user) {
                it.copy(contactByLetter = value != null)
            }
        }
    }

    override fun addActionCallback(profileField: ProfileField.Communication, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_COMMUNICATION)
    }

    override fun addActionCallback(profileField: ProfileField.MePin, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_PIN)
    }

    override fun addActionCallback(profileField: ProfileField.Address, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADDRESS)
    }

    override fun addActionCallback(profileField: ProfileField.Name, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_NAME)
    }

    override fun addActionCallback(profileField: ProfileField.AdaptionValues, viewable: ProfileViewable) {
        viewable.withUserClickEvent(ID_ADAPTION_VALUES)
    }

    private fun loadData() {
        profileViews.clear()
        loadDataInternal()
    }

    fun adjustUser(user: User) {
        onUserLoaded(user)
    }

    private fun ProfileViewable.withUserAction(action: (User, String?) -> Unit) {
        applyCallback(object : ProfileValueCallback {
            override fun onValueChanged(value: String?) {
                user?.let { action(it, value) }
            }
        })
    }

    private fun ProfileViewable.withAction(action: (String?) -> Unit): ProfileViewable {
        applyCallback(object : ProfileValueCallback {
            override fun onValueChanged(value: String?) {
                action(value)
            }
        })
        return this
    }

    private fun updateCommunicationPreferencesIfNecessary(
        user: User,
        receiver: (CommunicationPreference) -> CommunicationPreference
    ) {
        val currentPrefs = user.communicationPreference
        val newPrefs = receiver(currentPrefs)
        if (currentPrefs != newPrefs) {
            updateCommunicationPreferences(user, newPrefs)
        }
    }

    private fun updateCommunicationPreferences(user: User, newPreferences: CommunicationPreference) {
        val error = checkCommunicationPreferences(user, newPreferences)
        error?.let {
            userUpdateError.sendEvent(it)
            updateProfileItems(user, profileViews)
        } ?: {
            val newUser = user.copy(communicationPreference = newPreferences)
            updateUser(newUser, onError = {
                updateProfileItems(user, profileViews)
            })
        }()
    }

    private fun checkCommunicationPreferences(user: User, preferences: CommunicationPreference): String? =
        when {
            preferences.noneSelected() ->
                getString(R.string.profile_communication_preferences_empty)
            preferences.contactByMail && user.email.isBlank() ->
                getString(R.string.profile_communication_preferences_no_mail_error)
            preferences.contactByPhone && user.mobilePhone.isBlank() && user.landlinePhone.isBlank() ->
                getString(R.string.profile_communication_preferences_no_phone_error)
            preferences.contactBySms && user.mobilePhone.isBlank() ->
                getString(R.string.profile_communication_preferences_no_sms_error)
            preferences.contactByLetter && user.address == null ->
                getString(R.string.profile_communication_preferences_no_address_error)
            else -> null
        }

    private fun ProfileViewable.withUserClickEvent(id: Int) {
        withUserAction { user, _ -> editEvent.sendEvent(UserEditEvent(id, user)) }
    }

    private fun loadDataInternal() {
        progressVisible.postValue(true)
        UserTask().fetchUser(true)
            .onComplete { result ->
                onUserLoaded(result.user)
                loadProfileFields(result.user)
                fetchProfilePicture()
            }
            .onFailure {
                MBLoggerKit.e("Refresh data failed", throwable = it)
                logoutVisible.postValue(true)
                progressVisible.postValue(false)
                errorEvent.sendEvent(defaultErrorMessage(it))
            }
    }

    fun logout() {
        // Double check if logout is valid here.
        if (logoutPending || !MBIngressKit.authenticationService().isLoggedIn()) return
        progressVisible.postValue(true)
        logoutPending = true
        logoutStateEvent.sendEvent(ProfileLogoutState.STARTED)
        MBIngressKit.logout()
            .onComplete {
                logoutStateEvent.sendEvent(ProfileLogoutState.FINISHED_SUCCESS)
            }
            .onFailure { logoutStateEvent.sendEvent(ProfileLogoutState.FINISHED_ERROR) }
            .onAlways { _, _, _ ->
                progressVisible.postValue(false)
                logoutPending = false
            }
    }

    fun logoutAndDeleteAccount() {
        if (deleteAccountPending || !MBIngressKit.authenticationService().isLoggedIn()) return

        progressVisible.postValue(true)
        deleteAccountPending = true

        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                user?.let {
                    MBIngressKit.userService().deleteUser(token.jwtToken.plainToken, it)
                        .onComplete {
                            MBLoggerKit.d("Delete user successful!")
                            logout()
                        }
                        .onFailure {
                            MBLoggerKit.e("Delete user failure!")
                        }
                        .onAlways { _, _, _ ->
                            deleteAccountPending = false
                            progressVisible.postValue(false)
                        }
                } ?: run {
                    MBLoggerKit.e("Delete user failure! User is null")
                    deleteAccountPending = false
                    progressVisible.postValue(false)
                }
            }
            .onFailure {
                MBLoggerKit.e("Delete user failure! Refresh token failed.")
                deleteAccountPending = false
                progressVisible.postValue(false)
            }
    }

    fun onSelectImage() = imageEvent.sendEvent()

    fun applyProfileImageUrl(url: String) {
        handleProfilePictureUrl(upload = true) {
            load(url)
        }
    }

    internal fun onLanguageSelected(code: String) {
        val tmp = user?.copy(languageCode = code)
        tmp?.let { user ->
            updateUser(user, onComplete = {
                updateProfileItems(user, profileViews) {
                    it.associatedField() is ProfileField.LanguageCode
                }
            })
        }
    }

    internal fun onDateSelected(date: Long) {
        val tmp = user?.copy(birthday = getDateString(date))
        tmp?.let { user ->
            updateUser(user, onComplete = {
                updateProfileItems(user, profileViews) {
                    it.associatedField() is ProfileField.Birthday
                }
            })
        }
    }

    internal fun onPinSet() {
        user = user?.copy(pinStatus = UserPinStatus.SET) ?: return
        user?.let { user ->
            updateProfileItems(user, profileViews) {
                it.associatedField() is ProfileField.MePin
            }
        }
    }

    private fun onUserLoaded(user: User) {
        this.user = user
        name.postValue(user.formatName())
        mercedesMeId.postValue(user.email.takeIf { !it.isBlank() }
            ?: user.mobilePhone)
        headerVisible.postValue(true)
        logoutVisible.postValue(true)
        userChangedEvent.sendEvent(user)
        updateForFields(user, profileViews)
    }

    private fun updateUser(
        newUser: User,
        notifyObservers: Boolean = false,
        onComplete: ((User) -> Unit)? = null,
        onError: ((ResponseError<out RequestError>?) -> Unit)? = null
    ) {
        progressVisible.postValue(true)
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBIngressKit.userService().updateUser(jwt, newUser)
                    .onComplete { updatedUser ->
                        this.user = updatedUser
                        onComplete?.invoke(updatedUser)
                        if (notifyObservers) userChangedEvent.sendEvent(updatedUser)
                    }.onFailure {
                        onError?.invoke(it)
                        userUpdateError.sendEvent(userInputErrorMessage(it))
                    }.onAlways { _, _, _ -> progressVisible.postValue(false) }
            }.onFailure {
                MBLoggerKit.e("Could not refresh token.", throwable = it)
                onError?.invoke(null)
                authenticationError.sendEvent(defaultErrorMessage(it))
                progressVisible.postValue(false)
            }
    }

    private fun getDateString(date: Long): String {
        return SimpleDateFormat(ProfileFragment.DATE_PATTERN, Locale.getDefault()).format(date)
    }

    private fun fetchProfilePicture() {
        val token = MBIngressKit.authenticationService().getToken().jwtToken
        MBIngressKit.userService().fetchProfilePictureBytes(token.plainToken)
            .onComplete { handleProfilePictureUrl { load(it) } }
            .onFailure { MBLoggerKit.e("Failed to fetch Profile Picture") }
    }

    private fun handleProfilePictureUrl(
        upload: Boolean = false,
        loader: RequestBuilder<Bitmap>.() -> RequestBuilder<Bitmap>
    ) {
        Glide
            .with(getApplication<Application>())
            .asBitmap()
            .loader()
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {

                    return true
                }

                override fun onResourceReady(
                    bitmap: Bitmap,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageBitmap.postValue(bitmap)
                    if (upload) {
                        updateProfilePicture(bitmap)
                    }
                    return true
                }
            })
            .submit()
    }

    private fun updateProfilePicture(bitmap: Bitmap) {
        val token = MBIngressKit.authenticationService().getToken().jwtToken
        val bitmapByteArray = bitmap.toByteArray(PICTURE_QUALITY_FULL)
        val shrankBitmap = resizeBitmap(bitmapByteArray, PICTURE_MAX_SIZE_PX, PICTURE_MAX_SIZE_PX)
        shrankBitmap?.let {
            val byteArray = it.toByteArray(PICTURE_QUALITY)

            MBIngressKit.userService().updateProfilePicture(token.plainToken, byteArray, "image/jpeg")
                .onComplete {
                    userPictureChangedEvent.sendEvent(byteArray)
                    MBLoggerKit.d("Profile Picture updated")
                }.onFailure {
                    MBLoggerKit.re("Failed to update Profile Picture", it)
                }
        }
    }

    internal data class UserEditEvent(val id: Int, val user: User)

    internal companion object {

        const val ID_NAME = 1
        const val ID_MAIL = 2
        const val ID_BIRTHDAY = 3
        const val ID_PHONE = 4
        const val ID_ADDRESS = 5
        const val ID_PIN = 6
        const val ID_LANGUAGE_CODE = 7
        const val ID_TAX_NUMBER = 8
        const val ID_ADAPTION_VALUES = 9
        const val ID_COMMUNICATION = 10

        private const val PICTURE_QUALITY = 70
        private const val PICTURE_QUALITY_FULL = 100
        private const val PICTURE_MAX_SIZE_PX = 512
    }
}