package com.daimler.mbingresskit.implementation.network.model.user.create

import com.daimler.mbingresskit.common.RegistrationUserAgreementUpdate
import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.implementation.network.model.user.ApiAgreementUpdate
import com.daimler.mbingresskit.implementation.network.model.user.UserCommunicationPreference
import com.daimler.mbingresskit.implementation.network.model.user.toApiAgreementUpdate
import com.daimler.mbingresskit.implementation.network.model.user.toUserCommunicationPreference
import com.daimler.mbingresskit.implementation.network.model.user.update.UpdateUserRequestAddress
import com.daimler.mbingresskit.implementation.network.model.user.update.toUpdateUserRequestAddress
import com.daimler.mbingresskit.implementation.network.toNullIfBlank
import com.google.gson.annotations.SerializedName

internal data class CreateUserRequest(
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName1") val lastName1: String,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("mobilePhoneNumber") val mobilePhone: String?,
    @SerializedName("landlinePhone") val landlinePhone: String?,
    @SerializedName("preferredLanguageCode") val preferredLanguageCode: String,
    @SerializedName("address") val address: UpdateUserRequestAddress?,
    @SerializedName("title") val title: String?,
    @SerializedName("salutationCode") val salutationCode: String?,
    @SerializedName("taxNumber") val taxNumber: String?,
    @SerializedName("communicationPreference") val communicationPreference: UserCommunicationPreference?,
    @SerializedName("useEmailAsUsername") val useEmailAsUsername: Boolean,
    @SerializedName("toasConsents") val toasConsents: List<ApiAgreementUpdate>?,
    @SerializedName("nonce") val nonce: String?
)

internal fun User.toCreateUserRequest(
    useMailAsUsername: Boolean,
    agreementUpdates: List<RegistrationUserAgreementUpdate>?,
    nonce: String?
) = CreateUserRequest(
    firstName = firstName,
    lastName1 = lastName,
    birthday = birthday?.toNullIfBlank(),
    email = email?.toNullIfBlank(),
    mobilePhone = mobilePhone?.toNullIfBlank(),
    landlinePhone = landlinePhone?.toNullIfBlank(),
    preferredLanguageCode = languageCode,
    address = address?.toUpdateUserRequestAddress(),
    title = title?.toNullIfBlank(),
    salutationCode = salutationCode?.toNullIfBlank(),
    taxNumber = taxNumber?.toNullIfBlank(),
    communicationPreference = communicationPreference.toUserCommunicationPreference(),
    useEmailAsUsername = useMailAsUsername,
    toasConsents = agreementUpdates?.map { it.toApiAgreementUpdate() },
    nonce = nonce
)
