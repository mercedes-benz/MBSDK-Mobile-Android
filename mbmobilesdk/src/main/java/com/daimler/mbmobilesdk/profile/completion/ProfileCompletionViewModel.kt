package com.daimler.mbmobilesdk.profile.completion

import android.app.Application
import com.daimler.mbmobilesdk.profile.BaseUserLiveEditViewModel
import com.daimler.mbmobilesdk.profile.UserCreator
import com.daimler.mbmobilesdk.profile.creator.DynamicProfileCreator
import com.daimler.mbmobilesdk.profile.fields.*
import com.daimler.mbmobilesdk.profile.format.ProfileFieldValueFormatter
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbmobilesdk.utils.extensions.userInputErrorMessage
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbingresskit.common.User
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

internal class ProfileCompletionViewModel(
    app: Application,
    user: User
) : BaseUserLiveEditViewModel<ProfileRecyclerViewable>(app) {

    val progressVisible = mutableLiveDataOf(false)
    val saveVisible = mutableLiveDataOf(false)

    val viewablesCreatedEvent = MutableLiveEvent<List<ProfileRecyclerViewable>>()
    val userUpdatedEvent = MutableLiveEvent<User>()
    val generalErrorEvent = MutableLiveEvent<String>()

    override val userCreator: UserCreator = UserCreator(user)

    private val items = mutableListOf<ProfileRecyclerViewable>()

    init {
        onLoadingStarted()
        loadFields()
    }

    fun onSaveClicked() {
        if (handleInput()) {
            updateUser(user())
        }
    }

    override fun addActionCallback(profileField: ProfileField.AddressCountryCode, viewable: ProfileViewable) {
        // --
    }

    override fun onProfileFieldsLoaded(fieldsData: ProfileFieldsData) {
        super.onProfileFieldsLoaded(fieldsData)
        onLoadingFinished()
    }

    override fun onProfileFieldsLoadingFailed(error: ResponseError<out RequestError>?) {
        super.onProfileFieldsLoadingFailed(error)
        onLoadingFinished()
    }

    override fun prepareProfileFields(fieldsData: ProfileFieldsData): List<ProfileField> {
        return ProfileFieldPreparationImpl(fieldsData, false).fields()
    }

    override fun getResolutionStrategy(): ProfileFieldResolutionStrategy {
        return ResolutionMandatoryBuilder().apply {
            exclude<ProfileField.MarketCountryCode>()

            exclude<ProfileField.ContactPerMail>()
            exclude<ProfileField.ContactPerPhone>()
            exclude<ProfileField.ContactPerSms>()
            exclude<ProfileField.ContactPerLetter>()
        }.build()
    }

    override fun getItemsCreator(
        app: Application,
        user: User,
        resolutionStrategy: ProfileFieldResolutionStrategy,
        formatter: ProfileFieldValueFormatter
    ): DynamicProfileCreator<ProfileRecyclerViewable> {
        return ProfileCompletionItemsCreator(app, this, user,
            resolutionStrategy, formatter)
    }

    override fun onItemsCreated(items: List<ProfileRecyclerViewable>) {
        this.items.clear()
        this.items.addAll(items)
        viewablesCreatedEvent.sendEvent(items)
        saveVisible.postValue(true)
    }

    fun adjustBirthday(birthday: Long) {
        birthdaySet(birthday)
        updateProfileItems(user(), items) {
            it.associatedField() is ProfileField.Birthday
        }
    }

    private fun updateUser(user: User) {
        onLoadingStarted()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBIngressKit.userService().updateUser(jwt, user)
                    .onComplete {
                        MBLoggerKit.d("Updated user: $it.")
                        notifyUpdated(it)
                    }.onFailure {
                        MBLoggerKit.re("Failed to updated user.", it)
                        notifyUserInputError(it)
                    }.onAlways { _, _, _ ->
                        onLoadingFinished()
                    }
            }.onFailure {
                MBLoggerKit.e("Failed to refresh token.", throwable = it)
                onLoadingFinished()
                notifyDefaultError(it)
            }
    }

    private fun notifyUpdated(user: User) {
        userUpdatedEvent.sendEvent(user)
    }

    private fun notifyUserInputError(error: ResponseError<out RequestError>?) {
        inputErrorEvent.sendEvent(userInputErrorMessage(error))
    }

    private fun notifyDefaultError(error: Throwable?) {
        generalErrorEvent.sendEvent(defaultErrorMessage(error))
    }

    private fun onLoadingStarted() {
        progressVisible.postValue(true)
    }

    private fun onLoadingFinished() {
        progressVisible.postValue(false)
    }
}