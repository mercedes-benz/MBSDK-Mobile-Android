package com.daimler.mbcarkit.business.model.accountlinkage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AccountLinkageGroup(
    val accountType: AccountType?,
    val iconUrl: String?,
    val name: String?,
    val bannerImageUrl: String?,
    val bannerTitle: String?,
    val description: String?,
    val visible: Boolean,
    val accounts: List<AccountLinkage>
) : Parcelable
