package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountLinkage
import com.google.gson.annotations.SerializedName

internal data class ApiAccountLinkage(
    @SerializedName("connectionState") val connectionState: ApiAccountConnectionState?,
    @SerializedName("userAccountId") val userAccountId: String?,
    @SerializedName("vendorId") val vendorId: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("descriptionLinks") val descriptionLinks: Map<String, String>?,
    @SerializedName("isDefault") val isDefault: Boolean,
    @SerializedName("iconUrl") val iconUrl: String?,
    @SerializedName("bannerUrl") val bannerUrl: String?,
    @SerializedName("vendorDisplayName") val vendorDisplayName: String?,
    @SerializedName("accountType") val accountType: ApiAccountType?,
    @SerializedName("possibleActions") val possibleActions: List<ApiAccountPossibleAction>?,
    @SerializedName("legalTextUrl") val legalTextUrl: String?
)

internal fun ApiAccountLinkage.toAccountLinkage() =
    AccountLinkage(
        connectionState?.toAccountConnectionState(),
        userAccountId,
        vendorId.orEmpty(),
        description,
        descriptionLinks,
        isDefault,
        iconUrl,
        bannerUrl,
        vendorDisplayName.orEmpty(),
        accountType?.toAccountType(),
        possibleActions?.map { it.toAccountAction() } ?: emptyList(),
        legalTextUrl
    )
