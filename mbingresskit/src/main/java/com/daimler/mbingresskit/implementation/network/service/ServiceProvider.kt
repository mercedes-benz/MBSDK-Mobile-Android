package com.daimler.mbingresskit.implementation.network.service

import com.daimler.mbingresskit.login.UserService
import com.daimler.mbingresskit.login.VerificationService

internal interface ServiceProvider {

    fun createUserService(baseUrl: String, enableLogging: Boolean): UserService

    fun createVerificationService(baseUrl: String, enableLogging: Boolean): VerificationService
}
