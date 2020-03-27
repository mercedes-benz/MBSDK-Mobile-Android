package com.daimler.mbingresskit.implementation.network.api

import com.daimler.mbingresskit.implementation.network.HttpHeaderConstants.Companion.HEADER_AUTHORIZATION
import com.daimler.mbingresskit.implementation.network.model.verification.ApiVerificationConfirmation
import com.daimler.mbingresskit.implementation.network.model.verification.ApiVerificationTransaction
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface VerificationApi {

    private companion object {
        private const val PATH_VERSION = "/v1"
        private const val PATH_VERIFICATION = "/verification"
        private const val PATH_TRANSACTION = "/transaction"
        private const val PATH_CONFIRMATION = "/confirmation"
    }

    @POST("$PATH_VERSION$PATH_VERIFICATION$PATH_TRANSACTION")
    suspend fun sendTransaction(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Body payload: ApiVerificationTransaction
    ): Response<Unit>

    @POST("$PATH_VERSION$PATH_VERIFICATION$PATH_CONFIRMATION")
    suspend fun sendConfirmation(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Body payload: ApiVerificationConfirmation
    ): Response<Unit>
}
