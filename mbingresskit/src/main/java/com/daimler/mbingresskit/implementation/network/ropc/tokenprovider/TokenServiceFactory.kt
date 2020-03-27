package com.daimler.mbingresskit.implementation.network.ropc.tokenprovider

import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.common.authentication.ROPCAuthenticationConfig
import com.daimler.mbingresskit.implementation.network.ropc.nonce.NonceProvider
import com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.ciam.CiamApi
import com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.ciam.CiamTokenService
import com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.keycloak.KeycloakApi
import com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.keycloak.KeycloakTokenService
import com.daimler.mbnetworkkit.networking.RetrofitHelper

internal class TokenServiceFactory(
    private val stage: String,
    private val retrofitHelper: RetrofitHelper,
    private val enableLogging: Boolean,
    private val nonceProvider: NonceProvider,
    private val authenticationConfigs: Map<AuthenticationType, ROPCAuthenticationConfig>,
) {

    fun createTokenService(
        authenticationType: AuthenticationType,
    ): TokenService =
        when (authenticationType) {
            AuthenticationType.KEYCLOAK -> {
                val config = authenticationConfigs[AuthenticationType.KEYCLOAK]
                    ?: throw IllegalArgumentException("Keycloak is not configured correctly")
                KeycloakTokenService(
                    keycloakApi = retrofitHelper.createRetrofit(
                        KeycloakApi::class.java,
                        config.baseUrl,
                        enableLogging,
                        RetrofitHelper.LONG_TIMEOUT
                    ),
                    clientId = config.clientId,
                    stage = stage
                )
            }
            AuthenticationType.CIAM -> {
                val config = authenticationConfigs[AuthenticationType.CIAM]
                    ?: throw IllegalArgumentException("CIAM is not configured correctly")
                CiamTokenService(
                    ciamApi = retrofitHelper.createRetrofit(
                        CiamApi::class.java,
                        config.baseUrl,
                        enableLogging,
                        RetrofitHelper.LONG_TIMEOUT
                    ),
                    clientId = config.clientId,
                    stage = stage,
                    nonceProvider = nonceProvider
                )
            }
        }
}
