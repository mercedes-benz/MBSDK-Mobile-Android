package com.daimler.mbingresskit.implementation.network.ropc.tokenexchange

import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface TokenExchanger {

    val exchangeAuthType: AuthenticationType

    fun exchangeToken(
        deviceId: String,
        userName: String,
        accessToken: String
    ): FutureTask<Token, ResponseError<out RequestError>?>
}
