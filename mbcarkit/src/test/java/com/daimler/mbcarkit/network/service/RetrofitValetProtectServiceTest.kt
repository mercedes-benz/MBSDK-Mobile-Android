package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.daimler.mbcarkit.business.model.valetprotect.DistanceUnit
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectItem
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectRadius
import com.daimler.mbcarkit.network.model.ApiDistanceUnit
import com.daimler.mbcarkit.network.model.ApiGeoCoordinates
import com.daimler.mbcarkit.network.model.ApiValetprotectItem
import com.daimler.mbcarkit.network.model.ApiValetprotectRadius
import com.daimler.mbcarkit.network.model.ApiValetprotectViolation
import com.daimler.mbcarkit.network.model.ApiValetprotectViolationType
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
internal class RetrofitValetProtectServiceTest : BaseRetrofitServiceTest<RetrofitValetProtectService>() {

    private val valetprotectItemResponse = mockk<Response<ApiValetprotectItem>>()
    private val valetprotectViolationResponse = mockk<Response<ApiValetprotectViolation>>()
    private val valetprotectViolationsResponse = mockk<Response<List<ApiValetprotectViolation>>>()

    override fun createSubject(scope: CoroutineScope): RetrofitValetProtectService =
        RetrofitValetProtectService(vehicleApi, scope)

    @BeforeEach
    fun setup() {
        vehicleApi.apply {
            coEvery {
                fetchValetprotectItem(
                    any(),
                    any(),
                    any()
                )
            } returns valetprotectItemResponse
            coEvery {
                createValetprotectItem(
                    any(),
                    any(),
                    any()
                )
            } returns valetprotectItemResponse
            coEvery {
                deleteValetprotectItem(
                    any(),
                    any()
                )
            } returns noContentResponse
            coEvery {
                fetchAllValetprotectViolations(
                    any(),
                    any(),
                    any()
                )
            } returns valetprotectViolationsResponse
            coEvery {
                deleteAllValetprotectViolations(
                    any(),
                    any()
                )
            } returns noContentResponse
            coEvery {
                fetchValetprotectViolation(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns valetprotectViolationResponse
            coEvery {
                deleteValetprotectViolation(
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
        }
    }

    @Test
    fun `fetchValetprotectItem() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(valetprotectItemResponse) {
            subject.fetchValetprotectItem("", "", DistanceUnit.KM)
        }
        scope.runBlockingTest {
            val original = ApiValetprotectItem(
                "",
                emptyList(),
                ApiGeoCoordinates(0.0, 1.0),
                ApiValetprotectRadius(1.0, ApiDistanceUnit.KM)
            )
            case.finish(original)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            with(case.success) {
                softly.assertThat(this?.name).isEqualTo(original.name)
                softly.assertThat(this?.violationtypes?.size).isEqualTo(original.violationtypes?.size)
                softly.assertThat(this?.center?.longitude).isEqualTo(original.center?.longitude)
                softly.assertThat(this?.center?.latitude).isEqualTo(original.center?.latitude)
                softly.assertThat(this?.radius?.value).isEqualTo(original.radius?.value)
                softly.assertThat(this?.radius?.unit?.name).isEqualTo(original.radius?.unit?.name)
            }
        }
    }

    @Test
    fun `fetchSpeedFences() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        valetprotectItemResponse.mockError()
        val case = ResponseTaskTestCase(valetprotectItemResponse) {
            subject.fetchValetprotectItem("", "", DistanceUnit.KM)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `createValetprotectItem() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(valetprotectItemResponse) {
            subject.createValetprotectItem(
                "",
                "",
                ValetprotectItem(
                    "",
                    emptyList(),
                    GeoCoordinates(0.0, 1.0),
                    ValetprotectRadius(1.0, DistanceUnit.KM)
                )
            )
        }
        scope.runBlockingTest {
            val original = ApiValetprotectItem(
                "",
                emptyList(),
                ApiGeoCoordinates(0.0, 1.0),
                ApiValetprotectRadius(1.0, ApiDistanceUnit.KM)
            )
            case.finish(original)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            with(case.success) {
                softly.assertThat(this?.name).isEqualTo(original.name)
                softly.assertThat(this?.violationtypes?.size).isEqualTo(original.violationtypes?.size)
                softly.assertThat(this?.center?.longitude).isEqualTo(original.center?.longitude)
                softly.assertThat(this?.center?.latitude).isEqualTo(original.center?.latitude)
                softly.assertThat(this?.radius?.value).isEqualTo(original.radius?.value)
                softly.assertThat(this?.radius?.unit?.name).isEqualTo(original.radius?.unit?.name)
            }
        }
    }

    @Test
    fun `createValetprotectItem() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        valetprotectItemResponse.mockError()
        val case = ResponseTaskTestCase(valetprotectItemResponse) {
            subject.createValetprotectItem(
                "",
                "",
                ValetprotectItem(
                    "",
                    emptyList(),
                    GeoCoordinates(0.0, 1.0),
                    ValetprotectRadius(1.0, DistanceUnit.KM)
                )
            )
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteViolation() should return successfully`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteValetprotectItem("", "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `deleteValetprotectItem() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteValetprotectItem("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchAllValetprotectViolations() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(valetprotectViolationsResponse) {
            subject.fetchAllValetprotectViolations("", "", DistanceUnit.KM)
        }
        scope.runBlockingTest {
            val apiValetViolation = listOf(
                ApiValetprotectViolation(
                    0,
                    ApiValetprotectViolationType.FENCE,
                    1234L,
                    ApiGeoCoordinates(0.0, 0.0),
                    ApiValetprotectItem(
                        "",
                        emptyList(),
                        ApiGeoCoordinates(0.0, 1.0),
                        ApiValetprotectRadius(1.0, ApiDistanceUnit.KM)
                    )
                )
            )
            case.finish(apiValetViolation)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            with(case.success) {
                val original = apiValetViolation.get(0)
                val violation = this?.get(0)
                softly.assertThat(violation?.id).isEqualTo(original.id)
                softly.assertThat(violation?.violationtype?.name).isEqualTo(original.violationtype?.name)
                softly.assertThat(violation?.time).isEqualTo(original.time)
                softly.assertThat(violation?.coordinate?.longitude).isEqualTo(original.coordinate?.longitude)
                softly.assertThat(violation?.coordinate?.latitude).isEqualTo(original.coordinate?.latitude)
                val valetprotectItem = violation?.snapshot
                softly.assertThat(valetprotectItem?.name).isEqualTo(original.snapshot.name)
            }
        }
    }

    @Test
    fun `fetchAllValetprotectViolations() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        valetprotectViolationsResponse.mockError()
        val case = ResponseTaskTestCase(valetprotectViolationsResponse) {
            subject.fetchAllValetprotectViolations("", "", DistanceUnit.KM)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteAllValetprotectViolations() should return successfully`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteAllValetprotectViolations("", "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `deleteAllValetprotectViolations() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteAllValetprotectViolations("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchValetprotectViolation() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(valetprotectViolationResponse) {
            subject.fetchValetprotectViolation("", "", 0, DistanceUnit.KM)
        }
        scope.runBlockingTest {
            val apiValetViolation =
                ApiValetprotectViolation(
                    0,
                    ApiValetprotectViolationType.FENCE,
                    1234L,
                    ApiGeoCoordinates(0.0, 0.0),
                    ApiValetprotectItem(
                        "",
                        emptyList(),
                        ApiGeoCoordinates(0.0, 1.0),
                        ApiValetprotectRadius(1.0, ApiDistanceUnit.KM)
                    )
                )
            case.finish(apiValetViolation)
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.error).isNull()
            with(case.success) {
                softly.assertThat(this?.id).isEqualTo(apiValetViolation.id)
                softly.assertThat(this?.violationtype?.name).isEqualTo(apiValetViolation.violationtype?.name)
                softly.assertThat(this?.time).isEqualTo(apiValetViolation.time)
                softly.assertThat(this?.coordinate?.longitude).isEqualTo(apiValetViolation.coordinate?.longitude)
                softly.assertThat(this?.coordinate?.latitude).isEqualTo(apiValetViolation.coordinate?.latitude)
                softly.assertThat(this?.snapshot?.name).isEqualTo(apiValetViolation.snapshot.name)
            }
        }
    }

    @Test
    fun `fetchValetprotectViolation() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        valetprotectViolationResponse.mockError()
        val case = ResponseTaskTestCase(valetprotectViolationResponse) {
            subject.fetchValetprotectViolation("", "", 0, DistanceUnit.KM)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `deleteValetprotectViolation() should return successfully`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteValetprotectViolation("", "", 0)
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `deleteValetprotectViolation() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.deleteValetprotectViolation("", "", 0)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }
}
