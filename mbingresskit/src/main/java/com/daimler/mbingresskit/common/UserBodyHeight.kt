package com.daimler.mbingresskit.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserBodyHeight(val bodyHeight: Int?, val preAdjustment: Boolean) : Parcelable
