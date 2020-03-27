package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.daimler.mbcarkit.business.model.merchants.OutletActivity
import com.daimler.mbcarkit.business.model.merchants.Radius
import com.daimler.mbcarkit.network.model.ApiMerchantRequest
import com.daimler.mbcarkit.network.model.ApiMerchantResponse
import com.daimler.mbcarkit.network.model.ApiOutletActivity
import com.daimler.mbcarkit.utils.ResponseTaskTestCase
import com.daimler.mbnetworkkit.networking.HttpError
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
internal class RetrofitOutletsServiceTest : BaseRetrofitServiceTest<RetrofitOutletsService>() {

    private val merchantsResponse = mockk<Response<List<ApiMerchantResponse>>>()

    override fun createSubject(scope: CoroutineScope): RetrofitOutletsService =
        RetrofitOutletsService(vehicleApi, scope)

    @BeforeEach
    fun setup() {
        coEvery { vehicleApi.fetchMerchants(any(), any()) } returns merchantsResponse
    }

    @Test
    fun `fetchOutlets() should use correct request model`(softly: SoftAssertions) {
        val zip = "12345"
        val country = "DE"
        val geo = GeoCoordinates(50.0, 50.0)
        val radius = Radius("30", "km")
        val activity = OutletActivity.SALES

        val requestSlot = slot<ApiMerchantRequest>()
        coEvery { vehicleApi.fetchMerchants(any(), capture(requestSlot)) } returns merchantsResponse

        subject.fetchOutlets(null, zip, country, geo, radius, activity)

        softly.assertThat(requestSlot.isCaptured).isTrue
        softly.assertThat(requestSlot.captured.activity).isEqualTo(ApiOutletActivity.SALES)
        softly.assertThat(requestSlot.captured.countryIsoCode).isEqualTo(country)
        softly.assertThat(requestSlot.captured.zipCodeOrCityName).isEqualTo(zip)

        val area = requestSlot.captured.searchArea
        softly.assertThat(area).isNotNull
        val center = area?.center
        softly.assertThat(center).isNotNull

        softly.assertThat(center?.latitude).isEqualTo(geo.latitude.toString())
        softly.assertThat(center?.longitude).isEqualTo(geo.longitude.toString())
    }

    @Test
    fun `fetchOutlets() should use default radius if nothing specified`(softly: SoftAssertions) {
        val zip = "12345"
        val country = "DE"
        val geo = GeoCoordinates(50.0, 50.0)
        val activity = OutletActivity.SALES

        val requestSlot = slot<ApiMerchantRequest>()
        coEvery { vehicleApi.fetchMerchants(any(), capture(requestSlot)) } returns merchantsResponse

        subject.fetchOutlets(null, zip, country, geo, outletActivity = activity)

        softly.assertThat(requestSlot.captured.searchArea?.radius?.value).isEqualTo("50")
        softly.assertThat(requestSlot.captured.searchArea?.radius?.unit).isEqualTo("km")
    }

    @Test
    fun `fetchOutlets() should return transformed data`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val apiMerchant = ApiMerchantResponse(
            "id",
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val case =
            ResponseTaskTestCase(merchantsResponse) {
                subject.fetchOutlets(
                    null,
                    null,
                    null,
                    null,
                    outletActivity = null
                )
            }
        scope.runBlockingTest {
            case.finish(listOf(apiMerchant))
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.success?.merchants?.firstOrNull()?.id).isEqualTo(apiMerchant.id)
        }
    }

    @Test
    fun `fetchOutlets() should return transformed error`(softly: SoftAssertions, scope: TestCoroutineScope) {
        merchantsResponse.mockError()
        val case = ResponseTaskTestCase(merchantsResponse) {
            subject.fetchOutlets(
                null,
                null,
                null,
                null,
                outletActivity = null
            )
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }
}
