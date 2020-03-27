package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import com.daimler.mbcarkit.network.model.ApiAccountLinkages
import com.daimler.mbcarkit.utils.ApiAccountLinkageFactory
import com.daimler.mbcarkit.utils.ResponseTaskTestCase
import com.daimler.mbnetworkkit.networking.HttpError
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
internal class RetrofitAccountLinkageServiceTest : BaseRetrofitServiceTest<RetrofitAccountLinkageService>() {

    private val loadAccountsResponse = mockk<Response<ApiAccountLinkages>>()

    override fun createSubject(scope: CoroutineScope): RetrofitAccountLinkageService =
        RetrofitAccountLinkageService(vehicleApi, scope)

    @BeforeEach
    fun setup() {
        coEvery { vehicleApi.fetchAccounts(any(), any(), any(), any()) } returns loadAccountsResponse
        coEvery { vehicleApi.deleteAccount(any(), any(), any(), any()) } returns noContentResponse
    }

    @Test
    fun `fetchAccounts() should return transformed data`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val linkage = ApiAccountLinkageFactory.createApiAccountLinkage(
            vendorId = "vendorId",
            vendorDisplayName = "vendorDisplayName"
        )
        val group = ApiAccountLinkageFactory.createApiAccountLinkageGroup(
            accounts = listOf(linkage)
        )
        val case =
            ResponseTaskTestCase(loadAccountsResponse) {
                subject.fetchAccounts("", "", null, null)
            }
        scope.runBlockingTest {
            case.finish(ApiAccountLinkageFactory.createApiAccountLinkages(listOf(group)))
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            val account = case.success?.firstOrNull()?.accounts?.firstOrNull()
            softly.assertThat(account).isNotNull
            softly.assertThat(account?.vendorId).isEqualTo(linkage.vendorId)
            softly.assertThat(account?.vendorDisplayName).isEqualTo(linkage.vendorDisplayName)
        }
    }

    @Test
    fun `fetchAccounts() should return transformed error`(softly: SoftAssertions, scope: TestCoroutineScope) {
        loadAccountsResponse.mockError()
        val case =
            ResponseTaskTestCase(loadAccountsResponse) {
                subject.fetchAccounts("", "", null, null)
            }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteAccount() should return success`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case =
            ResponseTaskTestCase(noContentResponse) {
                subject.deleteAccount("", "", "", AccountType.CHARGING)
            }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
        }
    }

    @Test
    fun `deleteAccount() should return error`(softly: SoftAssertions, scope: TestCoroutineScope) {
        noContentResponse.mockError()
        val case =
            ResponseTaskTestCase(noContentResponse) {
                subject.deleteAccount("", "", "", AccountType.CHARGING)
            }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }
}
