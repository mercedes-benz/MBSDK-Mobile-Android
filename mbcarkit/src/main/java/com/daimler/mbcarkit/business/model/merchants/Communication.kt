package com.daimler.mbcarkit.business.model.merchants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Communication(
    /**
     * Fax number
     */
    val fax: String?,
    /**
     * E-Mail address
     */
    val email: String?,
    /**
     * Website
     */
    val website: String?,
    /**
     * Facebook account
     */
    val facebook: String?,
    /**
     * Mobile number
     */
    val mobile: String?,
    /**
     * Google Plus account
     */
    val googlePlus: String?,
    /**
     * Twitter account
     */
    val twitter: String?,
    /**
     * Phone number
     */
    val phone: String?
) : Parcelable
