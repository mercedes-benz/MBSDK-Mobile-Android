package com.daimler.mbingresskit.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserAdaptionValues(
    val bodyHeight: UserBodyHeight?,
    val alias: String?
) : Parcelable
