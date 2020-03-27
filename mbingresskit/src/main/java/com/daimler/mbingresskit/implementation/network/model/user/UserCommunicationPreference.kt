package com.daimler.mbingresskit.implementation.network.model.user

import com.daimler.mbingresskit.common.CommunicationPreference
import com.google.gson.annotations.SerializedName

internal data class UserCommunicationPreference(
    @SerializedName("contactedByPhone") val contactByPhone: Boolean,
    @SerializedName("contactedByLetter") val contactByLetter: Boolean,
    @SerializedName("contactedByEmail") val contactByMail: Boolean,
    @SerializedName("contactedBySms") val contactBySms: Boolean
)

internal fun CommunicationPreference.toUserCommunicationPreference() = UserCommunicationPreference(
    contactByPhone = contactByPhone,
    contactByLetter = contactByLetter,
    contactByMail = contactByMail,
    contactBySms = contactBySms
)

internal fun UserCommunicationPreference.toCommunicationPreference() = CommunicationPreference(
    contactByPhone = contactByPhone,
    contactByLetter = contactByLetter,
    contactByMail = contactByMail,
    contactBySms = contactBySms
)
