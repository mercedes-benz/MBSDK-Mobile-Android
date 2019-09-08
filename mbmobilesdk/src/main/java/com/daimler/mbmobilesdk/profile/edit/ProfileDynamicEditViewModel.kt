package com.daimler.mbmobilesdk.profile.edit

import android.app.Application
import com.daimler.mbmobilesdk.profile.BaseUserLiveEditViewModel
import com.daimler.mbmobilesdk.profile.UserCreator
import com.daimler.mbmobilesdk.profile.creator.DynamicProfileCreator
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileFieldPreparationImpl
import com.daimler.mbmobilesdk.profile.fields.ProfileFieldResolutionStrategy
import com.daimler.mbmobilesdk.profile.fields.ProfileRecyclerViewable
import com.daimler.mbmobilesdk.profile.fields.ResolutionOnlyBuilder
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

internal class ProfileDynamicEditViewModel(
    app: Application,
    originalUser: User,
    private val editMode: ProfileEditMode
) : BaseUserLiveEditViewModel<ProfileRecyclerViewable>(app) {

    val progressVisible = mutableLiveDataOf(false)

    val itemsCreatedEvent = MutableLiveEvent<List<ProfileRecyclerViewable>>()
    val updatedEvent = MutableLiveEvent<User>()
    val errorEvent = MutableLiveEvent<String>()

    override val allowCachedProfileFields: Boolean = true
    override val userCreator: UserCreator = UserCreator(originalUser)

    init {
        onLoadingStarted()
        loadFields()
    }

    fun onSaveClicked() {
        if (handleInput()) {
            persistForEditMode(editMode)
        }
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
        return ResolutionOnlyBuilder().apply {
            includeProfileFields(editMode, this)
        }.build()
    }

    override fun getItemsCreator(
        app: Application,
        user: User,
        resolutionStrategy: ProfileFieldResolutionStrategy,
        formatter: ProfileFieldValueFormatter
    ): DynamicProfileCreator<ProfileRecyclerViewable> {
        return ProfileDynamicEditItemsCreator(app, this, user, resolutionStrategy, formatter)
    }

    override fun onItemsCreated(items: List<ProfileRecyclerViewable>) {
        itemsCreatedEvent.sendEvent(items)
    }

    private fun includeProfileFields(editMode: ProfileEditMode, builder: ResolutionOnlyBuilder) =
        when (editMode) {
            ProfileEditMode.NAME -> includeProfileFieldsForName(builder)
            ProfileEditMode.MAIL -> includeProfileFieldsForMail(builder)
            ProfileEditMode.PHONE -> includeProfileFieldsForPhone(builder)
            ProfileEditMode.ADDRESS -> includeProfileFieldsForAddress(builder)
            ProfileEditMode.TAX_NUMBER -> includeProfileFieldsForTaxNumber(builder)
            ProfileEditMode.ADAPTION_VALUES -> includeProfileFieldsForAdaptionValues(builder)
            ProfileEditMode.COMMUNICATION -> includeProfileFieldsForCommunicationValues(builder)
        }

    private fun includeProfileFieldsForCommunicationValues(builder: ResolutionOnlyBuilder): ResolutionOnlyBuilder =
            builder.apply {
                include<ProfileField.ContactPerMail>()
                include<ProfileField.ContactPerPhone>()
                include<ProfileField.ContactPerSms>()
                include<ProfileField.ContactPerLetter>()
            }

    private fun includeProfileFieldsForName(builder: ResolutionOnlyBuilder) =
        builder.apply {
            include<ProfileField.Salutation>()
            include<ProfileField.Title>()
            include<ProfileField.FirstName>()
            include<ProfileField.LastName>()
        }

    private fun includeProfileFieldsForMail(builder: ResolutionOnlyBuilder) =
        builder.apply {
            include<ProfileField.Email>()
        }

    private fun includeProfileFieldsForPhone(builder: ResolutionOnlyBuilder) =
        builder.apply {
            include<ProfileField.MobilePhone>()
            include<ProfileField.LandlinePhone>()
        }

    private fun includeProfileFieldsForAddress(builder: ResolutionOnlyBuilder) =
        builder.apply {
            include<ProfileField.AddressCountryCode>()
            include<ProfileField.AddressStreet>()
            include<ProfileField.AddressStreetType>()
            include<ProfileField.AddressHouseName>()
            include<ProfileField.AddressState>()
            include<ProfileField.AddressProvince>()
            include<ProfileField.AddressFloorNumber>()
            include<ProfileField.AddressDoorNumber>()
            include<ProfileField.AddressHouseNumber>()
            include<ProfileField.AddressZipCode>()
            include<ProfileField.AddressCity>()
            include<ProfileField.AddressLine1>()
            include<ProfileField.AddressPostOfficeBox>()
        }

    private fun includeProfileFieldsForTaxNumber(builder: ResolutionOnlyBuilder) =
        builder.apply {
            include<ProfileField.TaxNumber>()
        }

    private fun includeProfileFieldsForAdaptionValues(builder: ResolutionOnlyBuilder) =
        builder.apply {
            include<ProfileField.BodyHeight>()
            include<ProfileField.PreAdjustment>()
        }

    private fun persistForEditMode(editMode: ProfileEditMode) {
        if (editMode == ProfileEditMode.ADAPTION_VALUES) {
            updateAdaptionValues()
        } else {
            updateUser()
        }
    }

    private fun updateUser() {
        onLoadingStarted()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBIngressKit.userService().updateUser(jwt, user())
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

    private fun updateAdaptionValues() {
        val user = user()
        user.bodyHeight?.let {
            onLoadingStarted()
            MBIngressKit.refreshTokenIfRequired()
                .onComplete { token ->
                    val jwt = token.jwtToken.plainToken
                    MBIngressKit.userService().updateAdaptionValues(jwt, it)
                        .onComplete {
                            MBLoggerKit.d("Updated adaption values.")
                            notifyUpdated(user)
                        }.onFailure {
                            MBLoggerKit.re("Failed to update adaption values.", it)
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
    }

    private fun onLoadingStarted() {
        progressVisible.postValue(true)
    }

    private fun onLoadingFinished() {
        progressVisible.postValue(false)
    }

    private fun notifyUpdated(user: User) {
        updatedEvent.sendEvent(user)
    }

    private fun notifyUserInputError(error: ResponseError<out RequestError>?) {
        errorEvent.sendEvent(userInputErrorMessage(error))
    }

    private fun notifyDefaultError(error: Throwable?) {
        errorEvent.sendEvent(defaultErrorMessage(error))
    }
}