package com.daimler.mbingresskit.implementation.network.service

import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.mbnetworkkit.networking.exception.ResponseException
import com.daimler.testutils.coroutines.CoroutineTest
import com.daimler.testutils.coroutines.TestCoroutineExtension
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.ResponseBody
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Call
import retrofit2.Response

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class, TestCoroutineExtension::class)
internal abstract class BaseRetrofitServiceTest<Api, T : BaseRetrofitService<Api>> : CoroutineTest {

    protected abstract val headerService: HeaderService
    protected abstract val api: Api

    protected val noContentResponse = mockk<Response<Unit>>()
    protected val responseBodyCall = mockk<Call<ResponseBody>>()
    protected val retrofit500Error = ResponseException(500)

    protected lateinit var subject: T

    private lateinit var testScope: CoroutineScope

    override fun attachScope(scope: CoroutineScope) {
        this.testScope = scope
    }

    @BeforeEach
    fun setupTests() {
        every { headerService.currentNetworkLocale() } returns LOCALE_DE_DE

        subject = createSubject(testScope)
    }

    @AfterEach
    fun shutdownTests() {
        clearAllMocks()
    }

    protected abstract fun createSubject(scope: CoroutineScope): T

    protected companion object {

        const val LOCALE_DE_DE = "de-DE"
    }
}
