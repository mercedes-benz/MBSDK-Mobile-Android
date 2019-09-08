package com.daimler.mbmobilesdk.tou.custom

import com.daimler.mbmobilesdk.tou.BaseWrappedUserAgreement
import com.daimler.mbingresskit.common.CustomUserAgreement

internal data class CustomUserAgreements(
    override val locale: String,
    override val countryCode: String,
    val accessConditions: CustomUserAgreement?,
    val dataPrivacy: CustomUserAgreement?,
    val betaContent: CustomUserAgreement?,
    val appDescription: CustomUserAgreement?,
    val legalNotices: CustomUserAgreement?,
    val imprint: CustomUserAgreement?,
    val foreign: CustomUserAgreement?,
    val jumio: CustomUserAgreement?,
    val tou: CustomUserAgreement?
) : BaseWrappedUserAgreement()