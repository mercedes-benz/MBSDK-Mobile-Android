package com.daimler.mbingresskit.login

import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal interface LoginService : LoginStateService, RefreshTokenService {
    fun startLogin(): FutureTask<Unit, ResponseError<out RequestError>?>
    fun startLogout(): FutureTask<Unit, ResponseError<out RequestError>?>
    fun changeAuthenticationType(authenticationType: AuthenticationType)
}
