package com.daimler.mbingresskit.common

import com.daimler.mbnetworkkit.networking.RequestError

data class RegistrationErrors(
    val errors: List<UserInputError>,
    val consentNotGiven: Boolean
) : RequestError
