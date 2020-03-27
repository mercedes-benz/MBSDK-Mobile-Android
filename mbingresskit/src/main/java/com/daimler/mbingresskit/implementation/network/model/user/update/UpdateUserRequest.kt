package com.daimler.mbingresskit.implementation.network.model.user.update

import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.implementation.network.model.user.ApiAccountIdentifier
import com.daimler.mbingresskit.implementation.network.model.user.UserCommunicationPreference
import com.daimler.mbingresskit.implementation.network.model.user.toUserCommunicationPreference
import com.google.gson.annotations.SerializedName

internal data class UpdateUserRequest(
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName1") val lastName1: String,
    @SerializedName("lastName2") val lastName2: String?,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("mobilePhoneNumber") val mobilePhone: String?,
    @SerializedName("landlinePhone") val landlinePhone: String?,
    @SerializedName("accountCountryCode") val accountCountryCode: String?,
    @SerializedName("preferredLanguageCode") val preferredLanguageCode: String?,
    @SerializedName("address") val address: UpdateUserRequestAddress?,
    @SerializedName("accountIdentifier") val accountIdentifier: ApiAccountIdentifier?,
    @SerializedName("title") val title: String?,
    @SerializedName("salutationCode") val salutationCode: String?,
    @SerializedName("taxNumber") val taxNumber: String?,
    @SerializedName("communicationPreference") val communicationPreference: UserCommunicationPreference?
)

internal fun User.toUpdateUserRequest() = UpdateUserRequest(
    firstName = firstName,
    lastName1 = lastName,
    lastName2 = null,
    birthday = birthday,
    email = email,
    mobilePhone = mobilePhone,
    landlinePhone = landlinePhone,
    accountCountryCode = countryCode,
    preferredLanguageCode = languageCode,
    address = address?.toUpdateUserRequestAddress(),
    accountIdentifier = ApiAccountIdentifier.forName(accountIdentifier.name),
    title = title,
    salutationCode = salutationCode,
    taxNumber = taxNumber,
    communicationPreference = communicationPreference.toUserCommunicationPreference()
)
