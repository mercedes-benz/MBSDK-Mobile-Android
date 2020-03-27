package com.daimler.mbcarkit.network

import com.daimler.mbcarkit.network.model.ApiAccountActionType
import com.daimler.mbcarkit.network.model.ApiAccountConnectionState
import com.daimler.mbcarkit.network.model.ApiAccountType
import com.daimler.mbcarkit.network.model.ApiAllowedServiceActions
import com.daimler.mbcarkit.network.model.ApiPrerequisiteType
import com.daimler.mbcarkit.network.model.ApiServiceGroupOption
import com.daimler.mbcarkit.network.model.ApiServiceRight
import com.daimler.mbcarkit.network.model.ApiServiceStatus
import io.mockk.clearAllMocks
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@ExtendWith(SoftAssertionsExtension::class)
internal class VehicleApiTest {

    private lateinit var mockServer: MockWebServer
    private lateinit var vehicleApi: VehicleApi

    @BeforeEach
    fun setup() {
        mockServer = MockWebServer()
        vehicleApi = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockServer.url("/"))
            .build()
            .create(VehicleApi::class.java)
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
        mockServer.shutdown()
    }

    @Test
    fun `verify GET accounts 200`(softly: SoftAssertions) {
        mockServer.enqueue(createHttpOkResponse(GET_ACCOUNTS_200_PATH))

        runBlocking {
            val result = vehicleApi.fetchAccounts(
                jwtToken = TOKEN,
                finOrVin = FIN_OR_VIN,
                serviceIds = null,
                connectRedirectUrl = null
            ).body()!!

            softly.assertThat(result).isNotNull
            softly.assertThat(result.accounts).isNotNull
            softly.assertThat(result.accounts!!.size).isEqualTo(4)

            val musicGroup = result.accounts[0]
            val officeGroup = result.accounts[1]
            val chargingGroup = result.accounts[2]
            val smartHomeGroup = result.accounts[3]

            softly.assertThat(musicGroup.accountType).isEqualTo(ApiAccountType.MUSIC)
            softly.assertThat(musicGroup.iconUrl).isEqualTo(STRING_VALUE)
            softly.assertThat(musicGroup.name).isEqualTo(STRING_VALUE)
            softly.assertThat(musicGroup.bannerImageUrl).isEqualTo(STRING_VALUE)
            softly.assertThat(musicGroup.bannerTitle).isEqualTo(STRING_VALUE)
            softly.assertThat(musicGroup.description).isEqualTo(STRING_VALUE)
            softly.assertThat(musicGroup.visible).isTrue
            softly.assertThat(musicGroup.accounts?.size).isEqualTo(1)
            val musicAccount = musicGroup.accounts!!.first()
            softly.assertThat(musicAccount.connectionState).isEqualTo(ApiAccountConnectionState.DISCONNECTED)
            softly.assertThat(musicAccount.userAccountId).isEqualTo(STRING_VALUE)
            softly.assertThat(musicAccount.vendorId).isEqualTo(STRING_VALUE)
            softly.assertThat(musicAccount.description).isEqualTo(STRING_VALUE)
            softly.assertThat(musicAccount.descriptionLinks?.size).isEqualTo(1)
            val link = musicAccount.descriptionLinks?.get(STRING_VALUE)
            softly.assertThat(link).isEqualTo(STRING_VALUE)
            softly.assertThat(musicAccount.isDefault).isEqualTo(true)
            softly.assertThat(musicAccount.iconUrl).isEqualTo(STRING_VALUE)
            softly.assertThat(musicAccount.bannerUrl).isEqualTo(STRING_VALUE)
            softly.assertThat(musicAccount.vendorDisplayName).isEqualTo(STRING_VALUE)
            softly.assertThat(musicAccount.accountType).isEqualTo(ApiAccountType.MUSIC)
            softly.assertThat(musicAccount.possibleActions?.size).isEqualTo(1)
            val musicAction = musicAccount.possibleActions!!.first()
            softly.assertThat(musicAction.action).isEqualTo(ApiAccountActionType.CONNECT)
            softly.assertThat(musicAction.label).isEqualTo(STRING_VALUE)
            softly.assertThat(musicAction.url).isEqualTo(STRING_VALUE)
            softly.assertThat(musicAccount.legalTextUrl).isEqualTo(STRING_VALUE)

            softly.assertThat(officeGroup.accountType).isEqualTo(ApiAccountType.IN_CAR_OFFICE)
            softly.assertThat(officeGroup.accounts).isNull()

            softly.assertThat(chargingGroup.accountType).isEqualTo(ApiAccountType.CHARGING)
            softly.assertThat(chargingGroup.accounts).isNull()

            softly.assertThat(smartHomeGroup.accountType).isEqualTo(ApiAccountType.SMART_HOME)
            softly.assertThat(smartHomeGroup.accounts).isNull()
        }
    }

    @Test
    fun `verify DELETE account 204`() {
        mockServer.enqueue(createNoContentResponse())

        runBlocking {
            val result = vehicleApi.deleteAccount(
                TOKEN,
                FIN_OR_VIN,
                "",
                ApiAccountType.IN_CAR_OFFICE
            ).body()

            Assertions.assertNull(result)
        }
    }

    @Test
    fun `verify GET services 200`(softly: SoftAssertions) {
        mockServer.enqueue(createHttpOkResponse(GET_SERVICES_200_PATH))

        runBlocking {
            val result = vehicleApi.fetchServices(
                jwtToken = TOKEN,
                localeHeader = LOCALE,
                vin = FIN_OR_VIN,
                locale = LOCALE,
                groupBy = ApiServiceGroupOption.CATEGORY.apiName,
                fillMissingData = true
            ).body()

            softly.assertThat(result).isNotNull

            softly.assertThat(result!!.size).isEqualTo(1)

            val group = result.first()
            softly.assertThat(group.group).isEqualTo(STRING_VALUE)
            softly.assertThat(group.services.size).isEqualTo(2)

            val firstService = group.services[0]
            softly.assertThat(firstService.activationStatus).isEqualTo(ApiServiceStatus.ACTIVE)
            softly.assertThat(firstService.actualActivationServiceStatus)
                .isEqualTo(ApiServiceStatus.ACTIVE)
            softly.assertThat(firstService.desiredServiceStatus).isNull()
            softly.assertThat(firstService.virtualActivationServiceStatus).isNull()
            softly.assertThat(firstService.allowedActions).isEmpty()
            softly.assertThat(firstService.categoryName).isEqualTo(STRING_VALUE)
            softly.assertThat(firstService.description).isEqualTo(STRING_VALUE)
            softly.assertThat(firstService.id).isEqualTo(INT_VALUE)
            softly.assertThat(firstService.name).isEqualTo(STRING_VALUE)
            softly.assertThat(firstService.prerequisiteCheck.size).isEqualTo(1)
            softly.assertThat(firstService.prerequisiteCheck[0]?.actions).isEmpty()
            softly.assertThat(firstService.prerequisiteCheck[0]?.missingFields).isNull()
            softly.assertThat(firstService.prerequisiteCheck[0]?.name)
                .isEqualTo(ApiPrerequisiteType.LICENSE)
            softly.assertThat(firstService.rights).contains(ApiServiceRight.READ)
            softly.assertThat(firstService.rights).contains(ApiServiceRight.EXECUTE)
            softly.assertThat(firstService.shortDescription).isEqualTo(STRING_VALUE)
            softly.assertThat(firstService.shortName).isEqualTo(STRING_VALUE)
            softly.assertThat(firstService.missingData).isNull()

            val secondService = group.services[1]
            softly.assertThat(secondService.activationStatus).isEqualTo(ApiServiceStatus.INACTIVE)
            softly.assertThat(secondService.actualActivationServiceStatus).isNull()
            softly.assertThat(secondService.desiredServiceStatus).isEqualTo(ApiServiceStatus.ACTIVE)
            softly.assertThat(secondService.virtualActivationServiceStatus)
                .isEqualTo(ApiServiceStatus.ACTIVE)
            softly.assertThat(secondService.allowedActions.size).isEqualTo(1)
            softly.assertThat(secondService.allowedActions)
                .contains(ApiAllowedServiceActions.ACCOUNT_LINKAGE)
            softly.assertThat(secondService.categoryName).isNull()
            softly.assertThat(secondService.description).isNull()
            softly.assertThat(secondService.id).isEqualTo(INT_VALUE)
            softly.assertThat(secondService.name).isNull()
            softly.assertThat(secondService.prerequisiteCheck).isNull()
            softly.assertThat(secondService.rights).isNull()
            softly.assertThat(secondService.shortDescription).isNull()
            softly.assertThat(secondService.shortName).isNull()
            softly.assertThat(secondService.missingData).isNotNull
            softly.assertThat(secondService.missingData?.missingAccountLinkage).isNotNull
            softly.assertThat(secondService.missingData?.missingAccountLinkage?.mandatory).isTrue
            softly.assertThat(secondService.missingData?.missingAccountLinkage?.accountType)
                .isEqualTo(ApiAccountType.CHARGING)
        }
    }

    private fun createHttpOkResponse(jsonPath: String?) =
        createResponse(HttpURLConnection.HTTP_OK, jsonPath)

    private fun createNoContentResponse() =
        createResponse(HttpURLConnection.HTTP_NO_CONTENT, null)

    private fun createResponse(code: Int, jsonPath: String?) =
        MockResponse().apply {
            setResponseCode(code)
            jsonPath?.let { path ->
                val resource = VehicleApiTest::class.java.getResource(path)
                resource?.readText()?.let { setBody(it) }
            }
        }

    private companion object {

        private const val GET_ACCOUNTS_200_PATH = "/accounts_200.json"
        private const val GET_SERVICES_200_PATH = "/services_200.json"

        private const val TOKEN = "token"
        private const val FIN_OR_VIN = "finOrVin"
        private const val LOCALE = "de-DE"

        private const val STRING_VALUE = "string"
        private const val INT_VALUE = 1
    }
}
