package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.ServiceAction
import com.daimler.mbcarkit.network.model.ApiAllowedServiceActions.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiAllowedServiceActions {
    @SerializedName("SET_DESIRED_ACTIVE") SET_DESIRED_ACTIVE,
    @SerializedName("SET_DESIRED_INACTIVE") SET_DESIRED_INACTIVE,
    @SerializedName("SIGN_USER_AGREEMENT") SIGN_USER_AGREEMENT,
    @SerializedName("EDIT_USER_PROFILE") EDIT_USER_PROFILE,
    @SerializedName("UPDATE_TRUST_LEVEL") UPDATE_TRUST_LEVEL,
    @SerializedName("SET_CUSTOM_PROPERTY") SET_CUSTOM_PROPERTY,
    @SerializedName("PURCHASE_LICENSE") PURCHASE_LICENSE,
    @SerializedName("REMOVE_FUSEBOX_ENTRY") REMOVE_FUSEBOX_ENTRY,
    @SerializedName("ACCOUNT_LINKAGE") ACCOUNT_LINKAGE;

    companion object {
        val map: Map<String, ServiceAction> = ServiceAction.values().associateBy(ServiceAction::name)
    }
}

internal fun ApiAllowedServiceActions?.toServiceAction(): ServiceAction =
    this?.let { map[name] } ?: ServiceAction.UNKNOWN
