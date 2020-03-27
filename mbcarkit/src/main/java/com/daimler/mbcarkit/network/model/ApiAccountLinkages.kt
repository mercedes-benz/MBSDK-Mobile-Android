package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountLinkageGroup
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiAccountLinkages(
    @SerializedName("accountTypes") val accounts: List<ApiAccountGroup>?
) : Mappable<List<AccountLinkageGroup>> {

    override fun map(): List<AccountLinkageGroup> = toAccountGroups()
}

internal fun ApiAccountLinkages.toAccountGroups() =
    accounts?.map(ApiAccountGroup::toAccountLinkageGroup) ?: emptyList()
