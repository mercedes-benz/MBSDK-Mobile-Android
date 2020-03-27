package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiMerchantRequest(
    @SerializedName("zipCodeOrCityName") val zipCodeOrCityName: String?,
    @SerializedName("countryIsoCode") val countryIsoCode: String?,
    @SerializedName("searchArea") val searchArea: ApiSearchArea?,
    // The brand code for which an outlet should be searched
    @SerializedName("brandCode") val brandCode: String? = null,
    @SerializedName("productGroup") val productGroup: String? = null,
    // The function the outlet should fulfill for provided brandCode and productGroup
    @SerializedName("activity") val activity: ApiOutletActivity? = null

)
