package com.daimler.mbingresskit.implementation.network.service

import com.daimler.mbingresskit.implementation.etag.ETagProvider
import com.daimler.mbingresskit.implementation.network.api.UserApi
import com.daimler.mbingresskit.implementation.network.api.VerificationApi
import com.daimler.mbingresskit.implementation.network.ropc.nonce.NonceProvider
import com.daimler.mbingresskit.login.UserService
import com.daimler.mbingresskit.login.VerificationService
import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.mbnetworkkit.networking.RetrofitHelper

internal class ServiceProviderImpl(
    private val headerService: HeaderService,
    private val eTagProvider: ETagProvider,
    private val nonceProvider: NonceProvider,
    private val retrofitHelper: RetrofitHelper
) : ServiceProvider {

    override fun createUserService(baseUrl: String, enableLogging: Boolean): UserService =
        RetrofitUserService(
            retrofitHelper.createRetrofit(
                UserApi::class.java,
                baseUrl,
                enableLogging,
                RetrofitHelper.LONG_TIMEOUT
            ),
            headerService,
            eTagProvider,
            nonceProvider
        )

    override fun createVerificationService(
        baseUrl: String,
        enableLogging: Boolean
    ): VerificationService =
        RetrofitVerificationService(
            retrofitHelper.createRetrofit(
                VerificationApi::class.java,
                baseUrl,
                enableLogging
            ),
        )
}
