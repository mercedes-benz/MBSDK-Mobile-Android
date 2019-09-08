package com.daimler.mbmobilesdk.tou.soe

import com.daimler.mbmobilesdk.tou.AcceptableAgreementsService
import com.daimler.mbingresskit.common.SoeUserAgreement
import com.daimler.mbingresskit.common.UserAgreements
import com.daimler.mbingresskit.login.UserService
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal class SoeAgreementsService(
    userService: UserService
) : AcceptableAgreementsService<SoeUserAgreement>(userService) {

    override fun executeFetch(
        token: String,
        countryCode: String,
        userService: UserService
    ): FutureTask<UserAgreements<SoeUserAgreement>, ResponseError<out RequestError>?> {
        return userService.fetchSOETermsAndConditions(token, countryCode)
    }

    override fun SoeUserAgreement.isMandatory(): Boolean = generalUserAgreement
}