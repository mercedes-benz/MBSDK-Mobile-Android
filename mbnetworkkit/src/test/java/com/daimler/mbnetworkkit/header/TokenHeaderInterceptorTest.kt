package com.daimler.mbnetworkkit.header

import com.daimler.mbnetworkkit.BaseTestChain
import com.daimler.mbnetworkkit.utils.dummyResponse
import okhttp3.Request
import okhttp3.Response
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class TokenHeaderInterceptorTest {

    @Test
    fun `interceptor should add 'Bearer' to beginning of authorization header`() {
        val authorizationHeaderValue = "xxxxx"
        val chain = createAuthorizationTestChain(authorizationHeaderValue)
        TokenHeaderInterceptor().intercept(chain)
        Assertions.assertThat(chain.request().header(HEADER_AUTHORIZATION)).isEqualTo("$BEARER $authorizationHeaderValue")
    }

    @Test
    fun `interceptor should not modify authorization header if it already contains 'Bearer'`() {
        val authorizationHeaderValue = "$BEARER xxxxx"
        val chain = createAuthorizationTestChain(authorizationHeaderValue)
        TokenHeaderInterceptor().intercept(chain)
        Assertions.assertThat(chain.request().header(HEADER_AUTHORIZATION)).isEqualTo(authorizationHeaderValue)
    }

    @Test
    fun `interceptor should not modify authorization header if it is empty`() {
        val authorizationHeaderValue = ""
        val chain = createAuthorizationTestChain(authorizationHeaderValue)
        TokenHeaderInterceptor().intercept(chain)
        Assertions.assertThat(chain.request().header(HEADER_AUTHORIZATION)).isEqualTo(authorizationHeaderValue)
    }

    private fun createAuthorizationTestChain(authorizationHeaderValue: String) = object : BaseTestChain() {
        override var request: Request = Request.Builder()
            .url("https://jsonplaceholder.typicode.com")
            .addHeader(HEADER_AUTHORIZATION, authorizationHeaderValue)
            .build()

        override fun createResponse(request: Request): Response = dummyResponse(request)
    }

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
    }
}
