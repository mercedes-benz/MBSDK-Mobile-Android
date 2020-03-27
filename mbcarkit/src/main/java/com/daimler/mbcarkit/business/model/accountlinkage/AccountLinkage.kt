package com.daimler.mbcarkit.business.model.accountlinkage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AccountLinkage(
    val connectionState: AccountConnectionState?,
    val userAccountId: String?,
    val vendorId: String,
    val description: String?,
    val descriptionLinks: Map<String, String>?,
    val isDefault: Boolean,
    val iconUrl: String?,
    val bannerUrl: String?,
    val vendorDisplayName: String,
    val accountType: AccountType?,
    val actions: List<AccountLinkageAction>,
    val legalTextUrl: String?
) : Parcelable
