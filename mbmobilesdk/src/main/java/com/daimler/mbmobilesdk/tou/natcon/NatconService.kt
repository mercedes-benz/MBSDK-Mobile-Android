package com.daimler.mbmobilesdk.tou.natcon

import com.daimler.mbmobilesdk.tou.AcceptableAgreementsService
import com.daimler.mbingresskit.common.NatconUserAgreement
import com.daimler.mbingresskit.common.UserAgreements
import com.daimler.mbingresskit.login.UserService
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal class NatconService(
    userService: UserService
) : AcceptableAgreementsService<NatconUserAgreement>(userService) {

    override fun executeFetch(
        token: String,
        countryCode: String,
        userService: UserService
    ): FutureTask<UserAgreements<NatconUserAgreement>, ResponseError<out RequestError>?> {
        return userService.fetchNatconTermsAndConditions(countryCode)
    }

    override fun NatconUserAgreement.isMandatory(): Boolean = mandatory
}