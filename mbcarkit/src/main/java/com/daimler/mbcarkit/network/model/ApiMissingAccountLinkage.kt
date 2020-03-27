package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.MissingAccountLinkage
import com.google.gson.annotations.SerializedName

internal data class ApiMissingAccountLinkage(
    @SerializedName("mandatory") val mandatory: Boolean,
    @SerializedName("accountType") val accountType: ApiAccountType?
)

internal fun ApiMissingAccountLinkage.toMissingAccountLinkage(): MissingAccountLinkage =
    MissingAccountLinkage(
        mandatory,
        accountType?.toAccountType()
    )
