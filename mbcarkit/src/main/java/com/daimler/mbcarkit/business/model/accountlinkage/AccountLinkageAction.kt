package com.daimler.mbcarkit.business.model.accountlinkage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AccountLinkageAction(
    val label: String?,
    val actionType: AccountActionType?,
    val url: String?
) : Parcelable
