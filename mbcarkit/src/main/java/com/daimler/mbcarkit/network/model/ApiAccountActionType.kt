package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountActionType
import com.google.gson.annotations.SerializedName

internal enum class ApiAccountActionType {
    @SerializedName("CONNECT")
    CONNECT,

    @SerializedName("DELETE")
    DELETE,

    @SerializedName("SET_DEFAULT")
    SET_DEFAULT,

    @SerializedName("CONNECT_WITH_VOUCHER")
    CONNECT_WITH_VOUCHER
}

internal fun ApiAccountActionType.toAccountAction() =
    enumValueOf<AccountActionType>(name)
