package com.daimler.mbingresskit.implementation.network.ropc.tokenprovider

import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface TokenService {

    val authenticationType: AuthenticationType

    fun requestToken(
        deviceId: String,
        userName: String,
        password: String
    ): FutureTask<Token, ResponseError<out RequestError>?>

    fun refreshToken(
        token: Token
    ): FutureTask<Token, Throwable?>

    fun logout(
        token: Token
    ): FutureTask<Unit, ResponseError<out RequestError>?>
}
