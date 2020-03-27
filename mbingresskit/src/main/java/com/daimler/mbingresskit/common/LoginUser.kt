package com.daimler.mbingresskit.common

import com.daimler.mbingresskit.common.authentication.AuthenticationType

data class LoginUser(
    val userName: String,
    val isMail: Boolean,
    internal var authenticationType: AuthenticationType = AuthenticationType.KEYCLOAK
)
