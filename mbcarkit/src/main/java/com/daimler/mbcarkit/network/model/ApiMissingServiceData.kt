package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.MissingServiceData
import com.google.gson.annotations.SerializedName

internal data class ApiMissingServiceData(
    @SerializedName("missingAccountLinkage") val missingAccountLinkage: ApiMissingAccountLinkage?
)

internal fun ApiMissingServiceData.toMissingServiceData() =
    MissingServiceData(
        missingAccountLinkage?.toMissingAccountLinkage()
    )
