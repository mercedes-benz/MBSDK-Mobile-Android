package com.daimler.mbingresskit.implementation.network.model.profilefields

import com.daimler.mbingresskit.common.ProfileFieldType
import com.google.gson.annotations.SerializedName

internal enum class ProfileFieldTypeResponse(profileFieldType: ProfileFieldType? = null) {
    @SerializedName("email")
    EMAIL,
    @SerializedName("salutationCode")
    SALUTATION,
    @SerializedName("title")
    TITLE,
    @SerializedName("firstName")
    FIRST_NAME,
    @SerializedName("lastName1")
    LAST_NAME_1,
    @SerializedName("lastName2")
    LAST_NAME_2,
    @SerializedName("preferredLanguageCode")
    LANGUAGE_CODE,
    @SerializedName("birthday")
    BIRTHDAY,
    @SerializedName("mobilePhoneNumber")
    MOBILE_PHONE,
    @SerializedName("landlinePhone")
    LANDLINE_PHONE,
    @SerializedName("address.countryCode")
    ADDRESS_COUNTRY_CODE,
    @SerializedName("address.street")
    ADDRESS_STREET,
    @SerializedName("address.streetType")
    ADDRESS_STREET_TYPE,
    @SerializedName("address.houseName")
    ADDRESS_HOUSE_NAME,
    @SerializedName("address.houseNo")
    ADDRESS_HOUSE_NUMBER,
    @SerializedName("address.zipCode")
    ADDRESS_ZIP_CODE,
    @SerializedName("address.city")
    ADDRESS_CITY,
    @SerializedName("address.state")
    ADDRESS_STATE,
    @SerializedName("address.province")
    ADDRESS_PROVINCE,
    @SerializedName("address.floorNo")
    ADDRESS_FLOOR_NUMBER,
    @SerializedName("address.doorNo")
    ADDRESS_DOOR_NUMBER,
    @SerializedName("address.addressLine1")
    ADDRESS_LINE_1,
    @SerializedName("address.addressLine2")
    ADDRESS_LINE_2,
    @SerializedName("address.addressLine3")
    ADDRESS_LINE_3,
    @SerializedName("address.postOfficeBox")
    POST_OFFICE_BOX(ProfileFieldType.ADDRESS_POST_OFFICE_BOX),
    @SerializedName("communicationPreferences.contactedByEmail")
    COMMUNICATION_EMAIL,
    @SerializedName("communicationPreferences.contactedByLetter")
    COMMUNICATION_LETTER,
    @SerializedName("communicationPreferences.contactedByPhone")
    COMMUNICATION_PHONE,
    @SerializedName("communicationPreferences.contactedBySms")
    COMMUNICATION_SMS,
    @SerializedName("accountIdentifier")
    ACCOUNT_IDENTIFIER,
    @SerializedName("MOBILE_PHONE_NUMBER_VERIFIED")
    MOBILE_PHONE_NUMBER_VERIFIED,
    @SerializedName("MARKET_COUNTRY")
    MARKET_COUNTRY,
    @SerializedName("SERVICE_DEALER")
    SERVICE_DEALER,
    @SerializedName("LICENSE_PLATE")
    LICENSE_PLATE,
    @SerializedName("taxNumber")
    TAX_NUMBER,
    @SerializedName("adaptionValues.bodyHeight")
    BODY_HEIGHT,
    @SerializedName("adaptionValues.preAdjustment")
    PRE_ADJUSTMENT,
    @SerializedName("adaptionValues.alias")
    ALIAS;

    internal val profileFieldType = profileFieldType ?: enumValueOf(name)
}
