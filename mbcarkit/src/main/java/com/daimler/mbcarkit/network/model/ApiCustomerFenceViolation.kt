package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.customerfence.CustomerFenceViolation
import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.google.gson.annotations.SerializedName

internal data class ApiCustomerFenceViolation(
    @SerializedName("violationid") val violationId: Int?,
    @SerializedName("time") val timestamp: Long?,
    @SerializedName("coordinates") val coordinates: ApiGeoCoordinates?,
    @SerializedName("customerfence") val customerFence: ApiCustomerFence?,
    @SerializedName("onboardfence") val onboardFence: ApiOnboardFence?
)

internal fun ApiCustomerFenceViolation.toCustomerFenceViolation() = CustomerFenceViolation(
    violationId,
    timestamp,
    GeoCoordinates(coordinates?.latitude, coordinates?.longitude),
    customerFence?.toCustomerFence(),
    onboardFence?.toOnboardFence()
)
