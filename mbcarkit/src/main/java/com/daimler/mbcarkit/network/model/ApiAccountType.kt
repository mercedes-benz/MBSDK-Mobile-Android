package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import com.google.gson.annotations.SerializedName

internal enum class ApiAccountType {
    @SerializedName("MUSIC") MUSIC,
    @SerializedName("IN_CAR_OFFICE") IN_CAR_OFFICE,
    @SerializedName("CHARGING") CHARGING,
    @SerializedName("SMART_HOME") SMART_HOME
}

internal fun ApiAccountType.toAccountType() =
    enumValueOf<AccountType>(name)

internal fun AccountType.toApiAccountType() =
    enumValueOf<ApiAccountType>(name)
