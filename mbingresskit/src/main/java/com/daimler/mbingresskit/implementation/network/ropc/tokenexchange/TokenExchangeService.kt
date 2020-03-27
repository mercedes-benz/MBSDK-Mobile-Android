package com.daimler.mbingresskit.implementation.network.ropc.tokenexchange

import com.daimler.mbnetworkkit.task.FutureTask

interface TokenExchangeService {
    fun isExchangeTokenPossible(): Boolean
    fun exchangeToken(): FutureTask<Unit, Error>

    sealed class Error(val message: String) {
        object Impossible : Error("User is not authorized or token is already a ciam token")
        data class NetworkFailure(val error: String) : Error(error)
    }
}
