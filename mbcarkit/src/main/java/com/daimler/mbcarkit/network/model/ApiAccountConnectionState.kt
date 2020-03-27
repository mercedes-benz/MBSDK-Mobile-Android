package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountConnectionState
import com.google.gson.annotations.SerializedName

internal enum class ApiAccountConnectionState {
    @SerializedName("CONNECTED") CONNECTED,
    @SerializedName("DISCONNECTED") DISCONNECTED
}

internal fun ApiAccountConnectionState.toAccountConnectionState() =
    enumValueOf<AccountConnectionState>(name)
