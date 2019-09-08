package com.daimler.mbmobilesdk.tou.ciam

import com.daimler.mbmobilesdk.tou.BaseWrappedUserAgreement
import com.daimler.mbingresskit.common.CiamUserAgreement

internal data class CiamUserAgreements(
    override val locale: String,
    override val countryCode: String,
    val accessConditions: CiamUserAgreement?,
    val cookies: CiamUserAgreement?,
    val dataPrivacy: CiamUserAgreement?
) : BaseWrappedUserAgreement()