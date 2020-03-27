package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.ServiceMissingField
import com.daimler.mbcarkit.network.model.ApiServiceMissingFields.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiServiceMissingFields {
    @SerializedName("licensePlate") LICENSE_PLATE,
    @SerializedName("vehicleServiceDealer") VEHICLE_SERVICE_DEALER,
    @SerializedName("userContactedByEmail") USER_CONTACT_BY_MAIL,
    @SerializedName("userContactedByPhone") USER_CONTACT_BY_PHONE,
    @SerializedName("userContactedBySms") USER_CONTACT_BY_SMS,
    @SerializedName("userContactedByLetter") USER_CONTACT_BY_LETTER,
    @SerializedName("cp.INCREDIT") CP_INCREDIT,
    @SerializedName("userMobilePhone") USER_MOBILE_PHONE,
    @SerializedName("userMobilePhoneVerified") USER_MOBILE_PHONE_VERIFIED,
    @SerializedName("userEmail") USER_MAIL,
    @SerializedName("userEmailVerified") USER_MAIL_VERIFIED;

    companion object {
        val map: Map<String, ServiceMissingField> = ServiceMissingField.values().associateBy(ServiceMissingField::name)
    }
}

internal fun ApiServiceMissingFields?.toServiceMissingField(): ServiceMissingField =
    this?.let { map[name] } ?: ServiceMissingField.UNKNOWN
