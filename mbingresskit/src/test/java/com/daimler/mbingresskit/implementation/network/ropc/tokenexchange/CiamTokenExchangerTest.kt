package com.daimler.mbingresskit.implementation.network.ropc.tokenexchange

import android.util.Base64
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.implementation.network.ropc.TokenResponse
import com.daimler.mbingresskit.implementation.network.ropc.tokenexchange.ciam.CiamTokenExchanger
import com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.ciam.CiamApi
import com.daimler.mbingresskit.implementation.network.service.BaseRetrofitServiceTest
import com.daimler.mbingresskit.testutils.ResponseTaskTestCase
import com.daimler.mbnetworkkit.header.HeaderService
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

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class)
internal class CiamTokenExchangerTest :
    BaseRetrofitServiceTest<CiamApi, CiamTokenExchanger>() {

    override val api: CiamApi = mockk()
    override val headerService: HeaderService = mockk()

    private val response = mockk<Response<TokenResponse>>()

    override fun createSubject(scope: CoroutineScope): CiamTokenExchanger =
        CiamTokenExchanger(
            ciamApi = api,
            stage = "stage",
            clientId = "clientId",
            scope = scope
        )

    @BeforeEach
    fun setup() {
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
        mockkStatic("android.util.Base64")
        every { Base64.decode(any<String>(), any()) } answers { TOKEN_PAYLOAD.toByteArray() }
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
            subject.exchangeToken(
                deviceId = "123123",
                userName = "username",
                accessToken = ANY_VALID_JWT_TOKEN
            )
        }
        scope.runBlockingTest {
            case.finish(response)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.success?.accessToken).isEqualTo(response.accessToken)
            softly.assertThat(case.success?.authenticationType).isEqualTo(AuthenticationType.CIAM)
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
