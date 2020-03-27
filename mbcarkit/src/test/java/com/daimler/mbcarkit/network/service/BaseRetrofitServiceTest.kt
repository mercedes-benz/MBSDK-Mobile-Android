package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.testutils.coroutines.CoroutineTest
import com.daimler.testutils.coroutines.TestCoroutineExtension
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class, TestCoroutineExtension::class)
internal abstract class BaseRetrofitServiceTest<T : BaseRetrofitService<*>> : CoroutineTest {

    protected val headerService = mockk<HeaderService>()
    protected val vehicleApi = mockk<VehicleApi>()

    protected val noContentResponse = mockk<Response<Unit>>()

    protected lateinit var subject: T

    private lateinit var scope: CoroutineScope

    override fun attachScope(scope: CoroutineScope) {
        this.scope = scope
    }

    @BeforeEach
    fun setupTests() {
        every { headerService.currentNetworkLocale() } returns LOCALE_DE_DE
        every { headerService.networkLocale } returns LOCALE_DE_DE

        subject = createSubject(scope)
    }

    @AfterEach
    fun shutdownTests() {
        clearAllMocks()
    }

    protected abstract fun createSubject(scope: CoroutineScope): T

    protected fun Response<*>.mockError(code: Int = HttpURLConnection.HTTP_BAD_REQUEST) =
        apply {
            every { isSuccessful } returns false
            every { code() } returns code
            every { body() } returns null
            every { errorBody() } returns null
        }

    protected companion object {

        protected const val LOCALE_DE_DE = "de-DE"
    }
}
