package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountLinkageAction
import com.google.gson.annotations.SerializedName

internal data class ApiAccountPossibleAction(
    @SerializedName("label") val label: String?,
    @SerializedName("action") val action: ApiAccountActionType?,
    @SerializedName("url") val url: String?
)

internal fun ApiAccountPossibleAction.toAccountAction() =
    AccountLinkageAction(
        label,
        action?.toAccountAction(),
        url
    )
