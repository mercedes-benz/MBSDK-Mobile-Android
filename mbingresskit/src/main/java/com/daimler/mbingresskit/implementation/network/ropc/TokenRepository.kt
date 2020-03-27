package com.daimler.mbingresskit.implementation.network.ropc

import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.common.UserCredentials
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface TokenRepository {

    fun requestToken(
        deviceId: String,
        userCredentials: UserCredentials
    ): FutureTask<Token, ResponseError<out RequestError>?>

    fun refreshToken(
        token: Token
    ): FutureTask<Token, Throwable?>

    fun logout(
        token: Token
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    fun replaceAuthenticationType(authenticationType: AuthenticationType)

    fun currentAuthenticationType(): AuthenticationType
}
