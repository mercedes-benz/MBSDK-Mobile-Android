package com.daimler.mbcarkit.business.model.services

import android.os.Parcelable
import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MissingAccountLinkage(
    val mandatory: Boolean,
    val type: AccountType?
) : Parcelable
