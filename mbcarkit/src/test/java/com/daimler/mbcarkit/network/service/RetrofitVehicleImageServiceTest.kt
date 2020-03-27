package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.model.vehicle.image.ImageBackground
import com.daimler.mbcarkit.business.model.vehicle.image.ImageConfig
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImage
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImageRequest
import com.daimler.mbcarkit.implementation.TopViewImageTask
import com.daimler.mbcarkit.implementation.VehicleImageTask
import com.daimler.mbcarkit.network.model.ApiFinImageProviderResponse
import com.daimler.mbcarkit.utils.ResponseTaskTestCase
import com.daimler.mbnetworkkit.networking.HttpError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.RequestResult
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkConstructor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import kotlin.random.Random

@ExperimentalCoroutinesApi
internal class RetrofitVehicleImageServiceTest :
    BaseRetrofitServiceTest<RetrofitVehicleImageService>() {

    private val imageProviderResponse = mockk<Response<ApiFinImageProviderResponse>>()

    override fun createSubject(scope: CoroutineScope): RetrofitVehicleImageService =
        RetrofitVehicleImageService(vehicleApi, scope)

    @BeforeEach
    fun setup() {
        mockkConstructor(VehicleImageTask::class)
        mockkConstructor(TopViewImageTask::class)

        vehicleApi.apply {
            coEvery { fetchFinImageProviderUrl(any()) } returns imageProviderResponse
        }
    }

    @Test
    fun `fetchVehicleImages() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val finOrVin = "finOrVin"
        val bytes = Random.nextBytes(20)

        coEvery {
            anyConstructed<VehicleImageTask>().execute(
                any()
            )
        } returns RequestResult.Success(
            listOf(
                VehicleImage(finOrVin, "", bytes)
            )
        )
        val request =
            VehicleImageRequest.Builder(finOrVin, ImageConfig(ImageBackground.CUTOUT)).build()

        scope.runBlockingTest {
            var success: List<VehicleImage>? = null
            subject.fetchVehicleImages("", request)
                .onComplete { success = it }

            softly.assertThat(success).isNotNull
            softly.assertThat(success?.firstOrNull()?.finOrVin).isEqualTo(finOrVin)
            softly.assertThat(success?.firstOrNull()?.imageBytes).isEqualTo(bytes)
        }
    }

    @Test
    fun `fetchVehicleImages() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        coEvery {
            anyConstructed<VehicleImageTask>().execute(
                any()
            )
        } returns RequestResult.Error(
            ResponseError.httpError(500)
        )

        scope.runBlockingTest {
            var errorResult: ResponseError<out RequestError>? = null
            subject.fetchVehicleImages("", mockk())
                .onFailure { errorResult = it }

            softly.assertThat(errorResult).isNotNull
            softly.assertThat(errorResult?.requestError)
                .isInstanceOf(HttpError.InternalServerError::class.java)
        }
    }

    @Test
    fun `fetchFinImageProviderUrl() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val api = ApiFinImageProviderResponse("url")
        val case = ResponseTaskTestCase(imageProviderResponse) {
            subject.fetchFinImageProviderUrl("")
        }
        scope.runBlockingTest {
            case.finish(api)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.success).isEqualTo(api.url)
        }
    }

    @Test
    fun `fetchFinImageProviderUrl() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        imageProviderResponse.mockError()
        val case = ResponseTaskTestCase(imageProviderResponse) {
            subject.fetchFinImageProviderUrl("")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchTopViewImages() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val map = mapOf("key" to Random.nextBytes(20))

        coEvery {
            anyConstructed<TopViewImageTask>().execute(
                any()
            )
        } returns RequestResult.Success(map)

        scope.runBlockingTest {
            var success: Map<String, ByteArray>? = null
            subject.fetchTopViewImages("", "", 0, 0)
                .onComplete { success = it }

            softly.assertThat(success).isNotNull
            softly.assertThat(success).isEqualTo(map)
        }
    }

    @Test
    fun `fetchTopViewImages() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        coEvery {
            anyConstructed<TopViewImageTask>().execute(
                any()
            )
        } returns RequestResult.Error(
            ResponseError.httpError(500)
        )

        scope.runBlockingTest {
            var errorResult: ResponseError<out RequestError>? = null
            subject.fetchTopViewImages("", "", 0, 0)
                .onFailure { errorResult = it }

            softly.assertThat(errorResult).isNotNull
            softly.assertThat(errorResult?.requestError)
                .isInstanceOf(HttpError.InternalServerError::class.java)
        }
    }
}
