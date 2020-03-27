package com.daimler.mbcarkit.business.model.merchants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Deprecated("Not reliable. Will be removed in a future version.")
@Parcelize
data class SpokenLanguage(
    val languageIsoCode: String?
) : Parcelable
