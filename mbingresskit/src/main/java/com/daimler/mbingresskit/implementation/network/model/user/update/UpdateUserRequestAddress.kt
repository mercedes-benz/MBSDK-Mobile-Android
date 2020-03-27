package com.daimler.mbingresskit.implementation.network.model.user.update

import com.daimler.mbingresskit.common.Address
import com.google.gson.annotations.SerializedName

internal data class UpdateUserRequestAddress(
    @SerializedName("countryCode") val countryCode: String?,
    @SerializedName("state") val state: String?,
    @SerializedName("province") val province: String?,
    @SerializedName("street") val street: String?,
    @SerializedName("houseNo") val houseNumber: String?,
    @SerializedName("zipCode") val zipCode: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("streetType") val streetType: String?,
    @SerializedName("houseName") val houseName: String?,
    @SerializedName("floorNo") val floorNumber: String?,
    @SerializedName("doorNo") val doorNumber: String?,
    @SerializedName("addressLine1") val addressLine1: String?,
    @SerializedName("addressLine2") val addressLine2: String?,
    @SerializedName("addressLine3") val addressLine3: String?,
    @SerializedName("postOfficeBox") val postOfficeBox: String?
)

internal fun Address.toUpdateUserRequestAddress() = UpdateUserRequestAddress(
    countryCode = countryCode,
    state = state,
    province = province,
    street = street,
    houseNumber = houseNumber,
    zipCode = zipCode,
    city = city,
    streetType = streetType,
    houseName = houseName,
    floorNumber = floorNumber,
    doorNumber = doorNumber,
    addressLine1 = addressLine1,
    addressLine2 = addressLine2,
    addressLine3 = addressLine3,
    postOfficeBox = postOfficeBox
)
