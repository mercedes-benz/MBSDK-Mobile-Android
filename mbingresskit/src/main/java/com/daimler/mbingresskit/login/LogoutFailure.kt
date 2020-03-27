package com.daimler.mbingresskit.login

import com.daimler.mbnetworkkit.networking.RequestError

enum class LogoutFailure : RequestError {

    NOT_LOGGED_IN,

    FAILED
}
