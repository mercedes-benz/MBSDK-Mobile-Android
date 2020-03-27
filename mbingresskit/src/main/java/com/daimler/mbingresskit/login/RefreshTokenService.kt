package com.daimler.mbingresskit.login

import com.daimler.mbingresskit.common.Token
import com.daimler.mbnetworkkit.task.FutureTask

interface RefreshTokenService {
    fun refreshToken(): FutureTask<Token, Throwable?>
}
