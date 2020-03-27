package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountLinkageGroup
import com.google.gson.annotations.SerializedName

internal data class ApiAccountGroup(
    @SerializedName("accountType") val accountType: ApiAccountType?,
    @SerializedName("iconUrl") val iconUrl: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("bannerImageUrl") val bannerImageUrl: String?,
    @SerializedName("heading") val bannerTitle: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("visible") val visible: Boolean,
    @SerializedName("accounts") val accounts: List<ApiAccountLinkage>?
)

internal fun ApiAccountGroup.toAccountLinkageGroup() =
    AccountLinkageGroup(
        accountType?.toAccountType(),
        iconUrl,
        name,
        bannerImageUrl,
        bannerTitle,
        description,
        visible,
        accounts?.map { it.toAccountLinkage() } ?: emptyList()
    )
