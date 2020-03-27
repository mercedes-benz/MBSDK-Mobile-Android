package com.daimler.mbcarkit.utils

import com.daimler.mbcarkit.network.model.ApiAccountActionType
import com.daimler.mbcarkit.network.model.ApiAccountConnectionState
import com.daimler.mbcarkit.network.model.ApiAccountGroup
import com.daimler.mbcarkit.network.model.ApiAccountLinkage
import com.daimler.mbcarkit.network.model.ApiAccountLinkages
import com.daimler.mbcarkit.network.model.ApiAccountPossibleAction
import com.daimler.mbcarkit.network.model.ApiAccountType

internal object ApiAccountLinkageFactory {

    internal fun createApiAccountAction(
        label: String? = null,
        action: ApiAccountActionType? = null,
        url: String? = null
    ) = ApiAccountPossibleAction(label, action, url)

    internal fun createApiAccountLinkage(
        connectionState: ApiAccountConnectionState? = null,
        userAccountId: String? = null,
        vendorId: String? = null,
        description: String? = null,
        descriptionLinks: Map<String, String>? = null,
        isDefault: Boolean = false,
        iconUrl: String? = null,
        bannerUrl: String? = null,
        vendorDisplayName: String? = null,
        accountType: ApiAccountType? = null,
        possibleActions: List<ApiAccountPossibleAction>? = null,
        legalTextUrl: String? = null
    ) = ApiAccountLinkage(
        connectionState,
        userAccountId,
        vendorId,
        description,
        descriptionLinks,
        isDefault,
        iconUrl,
        bannerUrl,
        vendorDisplayName,
        accountType,
        possibleActions,
        legalTextUrl
    )

    internal fun createApiAccountLinkageGroup(
        accountType: ApiAccountType? = null,
        iconUrl: String? = null,
        name: String? = null,
        bannerImageUrl: String? = null,
        bannerTitle: String? = null,
        description: String? = null,
        visible: Boolean = false,
        accounts: List<ApiAccountLinkage>? = null
    ) =
        ApiAccountGroup(
            accountType,
            iconUrl,
            name,
            bannerImageUrl,
            bannerTitle,
            description,
            visible,
            accounts
        )

    internal fun createApiAccountLinkages(
        groups: List<ApiAccountGroup>? = null
    ) = ApiAccountLinkages(groups)
}
