package com.daimler.mbingresskit.implementation.network.model.user.fetch

import com.daimler.mbingresskit.common.AccountIdentifier
import com.daimler.mbingresskit.common.CommunicationPreference
import com.daimler.mbingresskit.common.UnitPreferences
import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.common.UserPinStatus
import com.daimler.mbingresskit.implementation.network.model.adaptionvalues.ApiUserAdaptionValues
import com.daimler.mbingresskit.implementation.network.model.adaptionvalues.toUserAdaptionValues
import com.daimler.mbingresskit.implementation.network.model.adaptionvalues.toUserBodyHeight
import com.daimler.mbingresskit.implementation.network.model.unitpreferences.UserUnitPreferences
import com.daimler.mbingresskit.implementation.network.model.unitpreferences.toUnitPreferences
import com.daimler.mbingresskit.implementation.network.model.user.ApiAccountIdentifier
import com.daimler.mbingresskit.implementation.network.model.user.UserCommunicationPreference
import com.daimler.mbingresskit.implementation.network.model.user.toCommunicationPreference
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class UserTokenResponse(
    @SerializedName("ciamId") val ciamId: String,
    @SerializedName("userId") val userId: String?,
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName1") val lastName1: String?,
    @SerializedName("lastName2") val lastName2: String?,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("mobilePhoneNumber") val mobilePhone: String?,
    @SerializedName("landlinePhone") val landlinePhone: String?,
    @SerializedName("accountCountryCode") val accountCountryCode: String?,
    @SerializedName("preferredLanguageCode") val preferredLanguageCode: String?,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("address") val address: AddressResponse?,
    @SerializedName("userPinStatus") val userPinStatus: UserPinStatusResponse?,
    @SerializedName("communicationPreference") val communicationPreference: UserCommunicationPreference?,
    @SerializedName("unitPreferences") val unitPreferences: UserUnitPreferences?,
    @SerializedName("accountIdentifier") val accountIdentifier: ApiAccountIdentifier?,
    @SerializedName("title") val title: String?,
    @SerializedName("salutationCode") val salutationCode: String?,
    @SerializedName("taxNumber") val taxNumber: String?,
    @SerializedName("adaptionValues") val userAdaptionValues: ApiUserAdaptionValues?,
    @SerializedName("accountVerified") val accountVerified: Boolean?,
    @SerializedName("isEmailVerified") val isEmailVerified: Boolean,
    @SerializedName("isMobileVerified") val isMobileVerified: Boolean
) : Mappable<User> {

    override fun map(): User = toUser()
}

internal fun UserTokenResponse.toUser() = User(
    ciamId = ciamId,
    userId = userId.orEmpty(),
    firstName = firstName.orEmpty(),
    lastName = lastName1.orEmpty(),
    birthday = birthday,
    email = email,
    mobilePhone = mobilePhone,
    landlinePhone = landlinePhone,
    countryCode = accountCountryCode.orEmpty(),
    languageCode = preferredLanguageCode.orEmpty(),
    createdAt = createdAt,
    updatedAt = updatedAt,
    pinStatus = userPinStatus?.let { UserPinStatus.forName(it.name) } ?: UserPinStatus.UNKNOWN,
    address = address?.toAddress(),
    communicationPreference = communicationPreference?.toCommunicationPreference() ?: CommunicationPreference.initialState(),
    unitPreferences = unitPreferences?.toUnitPreferences() ?: UnitPreferences.defaultUnitPreferences(),
    accountIdentifier = accountIdentifier?.let {
        AccountIdentifier.forName(it.name)
    } ?: AccountIdentifier.UNKNOWN,
    title = title,
    salutationCode = salutationCode,
    taxNumber = taxNumber,
    bodyHeight = userAdaptionValues?.toUserBodyHeight(),
    accountVerified = accountVerified,
    emailVerified = isEmailVerified,
    mobileVerified = isMobileVerified,
    adaptionValues = userAdaptionValues?.toUserAdaptionValues()
)
