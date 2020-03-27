package com.daimler.mbingresskit.login

import com.daimler.mbingresskit.implementation.AuthstateRepository
import com.daimler.mbingresskit.implementation.network.ropc.TokenRepository
import io.mockk.mockk
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class LoginServiceFactoryTest {

    @Test
    fun `create login service`(softly: SoftAssertions) {
        val authStateRepository = mockk<AuthstateRepository>(relaxUnitFun = true)
        val tokenRepository = mockk<TokenRepository>(relaxUnitFun = true)

        val subject = LoginServiceFactory(
            authStateRepository,
            tokenRepository,
            "deviceId"
        )

        val loginService = subject.createLoginService(null)

        softly.assertThat(loginService).isNotNull
    }
}
