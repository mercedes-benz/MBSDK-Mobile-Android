package com.daimler.mbingresskit.implementation.network.ropc

import android.util.Base64
import com.daimler.mbingresskit.common.JwtToken
import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.keycloak.KeycloakApi
import com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.keycloak.KeycloakTokenService
import com.daimler.mbingresskit.implementation.network.service.BaseRetrofitServiceTest
import com.daimler.mbingresskit.testutils.ResponseTaskTestCase
import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.mbnetworkkit.networking.exception.ResponseException
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class)
internal class KeycloakTokenServiceTest :
    BaseRetrofitServiceTest<KeycloakApi, KeycloakTokenService>() {

    override val api: KeycloakApi = mockk()
    override val headerService: HeaderService = mockk()

    private val response = mockk<Response<TokenResponse>>()

    override fun createSubject(scope: CoroutineScope): KeycloakTokenService =
        KeycloakTokenService(
            keycloakApi = api,
            stage = "stage",
            clientId = "clientId",
            scope = scope
        )

    @BeforeEach
    fun setup() {
        coEvery {
            api.refreshToken(
                stage = any(),
                clientId = any(),
                grantType = any(),
                refreshToken = any()
            )
        } returns response
        coEvery {
            api.requestToken(
                stage = any(),
                deviceId = any(),
                clientId = any(),
                grantType = any(),
                userName = any(),
                password = any()
            )
        } returns response
        coEvery {
            api.logout(
                stage = any(),
                clientId = any(),
                refreshToken = any()
            )
        } returns noContentResponse

        mockkStatic("android.util.Base64")
        every { Base64.decode(any<String>(), any()) } answers { TOKEN_PAYLOAD.toByteArray() }
    }

    @Test
    fun `refreshToken() should return BadRequest if called with empty refresh token`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(response) {
            subject.refreshToken(Token("", "", "", JwtToken("", ""), 0L, 0L))
        }
        scope.runBlockingTest {
            case.finish(null)
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            val error = case.error as? ResponseException
            softly.assertThat(error).isNotNull
            softly.assertThat(error?.responseCode).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST)
        }
    }

    @Test
    fun `refreshToken() should return new token`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val testToken = ANY_VALID_JWT_TOKEN
        val response =
            TokenResponse(
                testToken,
                testToken,
                "tokenType",
                0,
                0,
                "scope",
                "typ"
            )
        val case = ResponseTaskTestCase(this.response) {
            subject.refreshToken(Token("", "", "asdf", JwtToken("", ""), 0L, 0L))
        }
        scope.runBlockingTest {
            case.finish(response)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.success?.accessToken).isEqualTo(testToken)
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `requestToken() should return token`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val response =
            TokenResponse(
                ANY_VALID_JWT_TOKEN,
                ANY_VALID_JWT_TOKEN,
                "tokenType",
                0,
                0,
                "scope",
                "typ"
            )

        val case = ResponseTaskTestCase(this.response) {
            subject.requestToken("", "", "")
        }
        scope.runBlockingTest {
            case.finish(response)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.success?.accessToken).isEqualTo(response.accessToken)
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `logout() should return successfully`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.logout(
                Token(
                    typ = "",
                    accessToken = "",
                    refreshToken = "",
                    tokenExpirationDate = 0L,
                    refreshTokenExpirationDate = 0L,
                    scope = "",
                    IdToken = "",
                    jwtToken = JwtToken("asdasd", "")
                )
            )
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    private companion object {

        private const val TOKEN_PAYLOAD = "{" +
            "  \"ciamid\": \"test\"," +
            "  \"typ\": \"typ\"" +
            "}"
        private const val ANY_VALID_JWT_TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjaWFtaWQiOiJ0ZXN0IiwidHlwIjoidHlwIn0.-afZ8n2_TIFIXeMQ1bGvrqO6LOYAfihQwUjomAHIOFg"
    }
}
