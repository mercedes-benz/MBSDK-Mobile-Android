package com.daimler.mbingresskit.implementation.network.ropc.tokenexchange

import com.daimler.mbingresskit.common.AuthenticationState
import com.daimler.mbingresskit.common.JwtToken
import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.implementation.AuthstateRepository
import com.daimler.mbingresskit.implementation.network.ropc.tokenexchange.ciam.CiamTokenExchangeService
import com.daimler.mbingresskit.persistence.UserCache
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.TaskObject
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class CiamTokenExchangeServiceTest {

    private val tokenExchanger: TokenExchanger = mockk()
    private val authStateRepository: AuthstateRepository = mockk()
    private val userCache: UserCache = mockk(relaxed = true)

    private val subject: TokenExchangeService = CiamTokenExchangeService(
        tokenExchanger,
        authStateRepository,
        userCache,
        DEVICE_ID
    )

    @Test
    fun `exchange token should not be possible with unauthorized token`() {
        every { authStateRepository.readAuthState() } returns AuthenticationState()

        val isPossible = subject.isExchangeTokenPossible()

        assertEquals(false, isPossible)
    }

    @Test
    fun `exchange token is possible with authorized keycloak token`() {
        every { authStateRepository.readAuthState() } returns AuthenticationState(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                Long.MAX_VALUE,
                Long.MAX_VALUE
            )
        )

        val isPossible = subject.isExchangeTokenPossible()

        assertEquals(true, isPossible)
    }

    @Test
    fun `exchange token should be not possible with authorized ciam token`() {
        every { authStateRepository.readAuthState() } returns AuthenticationState(
            token = Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                Long.MAX_VALUE,
                Long.MAX_VALUE,
                authenticationType = AuthenticationType.CIAM
            )
        )

        val isPossible = subject.isExchangeTokenPossible()

        assertEquals(false, isPossible)
    }

    @Test
    fun `exchange token request should fail with impossible when unauthorized`(softly: SoftAssertions) {
        var successful: Boolean? = null
        var failure: TokenExchangeService.Error? = null
        every { authStateRepository.readAuthState() } returns AuthenticationState()

        subject.exchangeToken().onComplete {
            successful = true
        }.onFailure {
            failure = it
        }

        softly.assertThat(successful).isNull()
        softly.assertThat(failure).isEqualTo(TokenExchangeService.Error.Impossible)
    }

    @Test
    fun `exchange token request should fail with impossible when ciam token`(softly: SoftAssertions) {
        var successful: Boolean? = null
        var failure: TokenExchangeService.Error? = null
        every { authStateRepository.readAuthState() } returns AuthenticationState(
            token = Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                Long.MAX_VALUE,
                Long.MAX_VALUE,
                authenticationType = AuthenticationType.CIAM
            )
        )

        subject.exchangeToken().onComplete {
            successful = true
        }.onFailure {
            failure = it
        }

        softly.assertThat(successful).isNull()
        softly.assertThat(failure).isEqualTo(TokenExchangeService.Error.Impossible)
    }

    @Test
    fun `exchange token request should successful with authorized keycloak token`(softly: SoftAssertions) {
        var successful: Boolean? = null
        var failure: TokenExchangeService.Error? = null
        every { authStateRepository.readAuthState() } returns AuthenticationState(
            token = Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                Long.MAX_VALUE,
                Long.MAX_VALUE,
                authenticationType = AuthenticationType.KEYCLOAK
            )
        )
        every { authStateRepository.saveAuthState(any()) } returns Unit
        every { userCache.loadUser() } returns mockk(relaxed = true)
        val tokenExchangerTask = TaskObject<Token, ResponseError<out RequestError>?>()
        every { tokenExchanger.exchangeToken(DEVICE_ID, any(), TEST_ACCESS_TOKEN) } returns tokenExchangerTask.futureTask()

        subject.exchangeToken().onComplete {
            successful = true
        }.onFailure {
            failure = it
        }

        tokenExchangerTask.complete(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                Long.MAX_VALUE,
                Long.MAX_VALUE,
                authenticationType = AuthenticationType.CIAM
            )
        )

        softly.assertThat(successful).isTrue
        softly.assertThat(failure).isNull()
    }

    companion object {
        private const val DEVICE_ID = "deviceID"
        private const val TEST_ACCESS_TOKEN = "1234asdf5678jklö"
        private const val TEST_REFRESH_TOKEN = "asdf1234jklö5678"
        private const val TEST_JWT_TOKEN = "asdfjklöasdfjklö"
        private const val TOKEN_TYPE_REFRESH = "Refresh"
    }
}
