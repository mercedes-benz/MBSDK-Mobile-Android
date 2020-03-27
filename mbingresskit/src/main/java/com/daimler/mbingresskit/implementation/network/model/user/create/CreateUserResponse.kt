package com.daimler.mbingresskit.implementation.network.model.user.create

import com.daimler.mbingresskit.common.CommunicationPreference
import com.daimler.mbingresskit.common.RegistrationUser
import com.daimler.mbingresskit.implementation.network.model.user.UserCommunicationPreference
import com.daimler.mbingresskit.implementation.network.model.user.toCommunicationPreference
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class CreateUserResponse(
    @SerializedName("email") val email: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String?,
    @SerializedName("id") val userId: String,
    @SerializedName("mobileNumber") val phone: String,
    @SerializedName("countryCode") val countryCode: String?,
    @SerializedName("communicationPreference") val communicationPreference: UserCommunicationPreference?
) : Mappable<RegistrationUser> {

    override fun map(): RegistrationUser = toRegistrationUser()
}

internal fun CreateUserResponse.toRegistrationUser() = RegistrationUser(
    userId = userId,
    firstName = firstName,
    lastName = lastName,
    email = email,
    phone = phone,
    password = password.orEmpty(),
    userName = username,
    countryCode = countryCode.orEmpty(),
    communicationPreference = communicationPreference?.toCommunicationPreference()
        ?: CommunicationPreference.initialState()
)
