package com.daimler.mbingresskit.common

import com.daimler.mbnetworkkit.networking.RequestError

data class UserInputErrors(val errors: List<UserInputError>) : RequestError
