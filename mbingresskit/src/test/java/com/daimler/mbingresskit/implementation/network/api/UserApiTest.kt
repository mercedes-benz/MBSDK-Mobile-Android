package com.daimler.mbingresskit.implementation.network.api

import com.daimler.mbingresskit.common.UnitPreferences
import com.daimler.mbingresskit.implementation.network.model.biometric.UserBiometricActivationStateRequest
import com.daimler.mbingresskit.implementation.network.model.pin.ChangePinRequest
import com.daimler.mbingresskit.implementation.network.model.pin.LoginUserRequest
import com.daimler.mbingresskit.implementation.network.model.pin.SetPinRequest
import com.daimler.mbingresskit.implementation.network.model.profilefields.FieldOwnerTypeResponse
import com.daimler.mbingresskit.implementation.network.model.profilefields.ProfileDataFieldRelationshipTypeResponse
import com.daimler.mbingresskit.implementation.network.model.profilefields.ProfileFieldUsageResponse
import com.daimler.mbingresskit.implementation.network.model.user.ApiAccountIdentifier
import com.daimler.mbingresskit.implementation.network.model.user.create.CreateUserRequest
import com.daimler.mbingresskit.implementation.network.model.user.fetch.UserPinStatusResponse
import com.daimler.mbingresskit.implementation.network.model.user.update.UpdateUserRequest
import com.daimler.testutils.coroutines.TestCoroutineExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class, TestCoroutineExtension::class)
class UserApiTest {

    private lateinit var mockServer: MockWebServer
    private lateinit var userApi: UserApi

    @BeforeEach
    fun setUp() {
        mockServer = MockWebServer()
        userApi = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockServer.url("/"))
            .build()
            .create(UserApi::class.java)
    }

    @AfterEach
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun `send pin call with valid data`() {
        mockServer.enqueue(createMockResponse(HttpURLConnection.HTTP_OK, LOGIN_JSON_FILE))

        runBlocking {
            val loginUserResponse = userApi.sendTan(
                body = LoginUserRequest("johnneumann@example.com", COUNTRY_CODE, "de-DE", "nonce")
            ).body()

            assertEquals(true, loginUserResponse?.isEmail)
            assertEquals("johnneumann", loginUserResponse?.userName)
        }
    }

    @ParameterizedTest
    @ValueSource(
        ints = [
            HttpURLConnection.HTTP_BAD_REQUEST, HttpURLConnection.HTTP_INTERNAL_ERROR,
            HttpURLConnection.HTTP_BAD_GATEWAY, HttpURLConnection.HTTP_UNAUTHORIZED, HttpURLConnection.HTTP_NOT_FOUND
        ]
    )
    fun `send pin call with different http response`(responseCode: Int) {
        mockServer.enqueue(createMockResponse(responseCode))

        runBlocking {
            val loginUserResponse = userApi.sendTan(
                body = LoginUserRequest("thisisnotanemail", "asdf", "asdf-ASDF", "nonce")
            ).body()

            assertEquals(null, loginUserResponse)
        }
    }

    @Test
    fun `fetch user data`(softly: SoftAssertions) {
        mockServer.enqueue(createMockResponse(HttpURLConnection.HTTP_OK, USER_JSON_FILE))

        runBlocking {
            val userTokenResponse = userApi.fetchUserData(
                jwtToken = JWT_TOKEN,
            ).body()

            softly.assertThat(userTokenResponse).isNotNull
            softly.assertThat(userTokenResponse?.ciamId).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.firstName).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.lastName1).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.lastName2).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.birthday).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.email).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.mobilePhone).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.landlinePhone).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.accountCountryCode).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.preferredLanguageCode).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.createdAt).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.updatedAt).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.title).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.salutationCode).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.taxNumber).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.accountVerified).isEqualTo(true)
            softly.assertThat(userTokenResponse?.isEmailVerified).isEqualTo(true)
            softly.assertThat(userTokenResponse?.isMobileVerified).isEqualTo(true)
            // Address
            softly.assertThat(userTokenResponse?.address).isNotNull
            softly.assertThat(userTokenResponse?.address?.countryCode).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.address?.state).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.address?.province).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.address?.street).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.address?.houseNumber).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.address?.zipCode).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.address?.city).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.address?.streetType).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.address?.houseName).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.address?.floorNumber).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.address?.doorNumber).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.address?.addressLine1).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.address?.addressLine2).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.address?.addressLine3).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(userTokenResponse?.address?.postOfficeBox).isEqualTo(JSON_FIELD_VALUE)
            // UserPinStatus
            assertEquals(UserPinStatusResponse.SET, userTokenResponse?.userPinStatus)
            // UserCommunicationPreference
            softly.assertThat(userTokenResponse?.communicationPreference).isNotNull
            softly.assertThat(userTokenResponse?.communicationPreference?.contactByPhone)
                .isEqualTo(true)
            softly.assertThat(userTokenResponse?.communicationPreference?.contactByLetter)
                .isEqualTo(true)
            softly.assertThat(userTokenResponse?.communicationPreference?.contactByMail)
                .isEqualTo(true)
            softly.assertThat(userTokenResponse?.communicationPreference?.contactBySms)
                .isEqualTo(true)
            // UserUnitPreferences
            softly.assertThat(userTokenResponse?.unitPreferences).isNotNull
            softly.assertThat(userTokenResponse?.unitPreferences?.clockHours)
                .isEqualTo(UnitPreferences.ClockHoursUnits.TYPE_24H)
            softly.assertThat(userTokenResponse?.unitPreferences?.speedDistance)
                .isEqualTo(UnitPreferences.SpeedDistanceUnits.KILOMETERS)
            softly.assertThat(userTokenResponse?.unitPreferences?.consumptionCo)
                .isEqualTo(UnitPreferences.ConsumptionCoUnits.LITERS_PER_100_KILOMETERS)
            softly.assertThat(userTokenResponse?.unitPreferences?.consumptionEv)
                .isEqualTo(UnitPreferences.ConsumptionEvUnits.KILOWATT_HOURS_PER_100_KILOMETERS)
            softly.assertThat(userTokenResponse?.unitPreferences?.consumptionGas)
                .isEqualTo(UnitPreferences.ConsumptionGasUnits.KILOGRAM_PER_100_KILOMETERS)
            softly.assertThat(userTokenResponse?.unitPreferences?.tirePressure)
                .isEqualTo(UnitPreferences.TirePressureUnits.KILOPASCAL)
            softly.assertThat(userTokenResponse?.unitPreferences?.temperature)
                .isEqualTo(UnitPreferences.TemperatureUnits.CELSIUS)
            // ApiAccountIdentifier
            softly.assertThat(userTokenResponse?.accountIdentifier)
                .isEqualTo(ApiAccountIdentifier.EMAIL)
        }
    }

    @ParameterizedTest
    @ValueSource(
        ints = [
            HttpURLConnection.HTTP_BAD_REQUEST, HttpURLConnection.HTTP_INTERNAL_ERROR,
            HttpURLConnection.HTTP_BAD_GATEWAY, HttpURLConnection.HTTP_UNAUTHORIZED, HttpURLConnection.HTTP_NOT_FOUND
        ]
    )
    fun `fetch user data with different http response`(responseCode: Int) {
        mockServer.enqueue(createMockResponse(responseCode))

        runBlocking {
            val userTokenResponse = userApi.fetchUserData(
                jwtToken = JWT_TOKEN,
            ).body()

            assertEquals(null, userTokenResponse)
        }
    }

    @Test
    fun `create user data`(softly: SoftAssertions) {
        mockServer.enqueue(createMockResponse(HttpURLConnection.HTTP_OK, CREATE_USER_JSON_FILE))

        val createUserRequest = mockk<CreateUserRequest>()
        runBlocking {
            val createUserResponse = userApi.createUser(
                locale = HEADER_LOCALE,
                body = createUserRequest
            ).body()

            softly.assertThat(createUserResponse?.email).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(createUserResponse?.firstName).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(createUserResponse?.lastName).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(createUserResponse?.username).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(createUserResponse?.userId).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(createUserResponse?.phone).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(createUserResponse?.countryCode).isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(createUserResponse?.communicationPreference).isEqualTo(null)
        }
    }

    @ParameterizedTest
    @ValueSource(
        ints = [
            HttpURLConnection.HTTP_BAD_REQUEST, HttpURLConnection.HTTP_INTERNAL_ERROR,
            HttpURLConnection.HTTP_BAD_GATEWAY, HttpURLConnection.HTTP_UNAUTHORIZED, HttpURLConnection.HTTP_NOT_FOUND
        ]
    )
    fun `create user data with different http response`(responseCode: Int) {
        mockServer.enqueue(createMockResponse(responseCode))

        val createUserRequest = mockk<CreateUserRequest>()
        runBlocking {
            val createUserResponse = userApi.createUser(
                locale = HEADER_LOCALE,
                body = createUserRequest
            ).body()

            assertEquals(null, createUserResponse)
        }
    }

    @Test
    fun `update user data`() {
        mockServer.enqueue(createMockResponse(HttpURLConnection.HTTP_OK, UPDATE_USER_JSON_FILE))

        val updateUserRequest = mockk<UpdateUserRequest>()
        runBlocking {
            val userTokenResponse = userApi.updateUser(
                jwtToken = JWT_TOKEN,
                countryCode = COUNTRY_CODE,
                body = updateUserRequest
            ).body()

            assertEquals(TIMESTAMP_EXAMPLE, userTokenResponse?.updatedAt)
        }
    }

    @ParameterizedTest
    @ValueSource(
        ints = [
            HttpURLConnection.HTTP_BAD_REQUEST, HttpURLConnection.HTTP_INTERNAL_ERROR,
            HttpURLConnection.HTTP_BAD_GATEWAY, HttpURLConnection.HTTP_UNAUTHORIZED, HttpURLConnection.HTTP_NOT_FOUND
        ]
    )
    fun `update user data with different http response`(responseCode: Int) {
        mockServer.enqueue(createMockResponse(responseCode))

        val updateUserRequest = mockk<UpdateUserRequest>()
        runBlocking {
            val userTokenResponse = userApi.updateUser(
                jwtToken = JWT_TOKEN,
                countryCode = COUNTRY_CODE,
                body = updateUserRequest
            ).body()

            assertEquals(null, userTokenResponse)
        }
    }

    @Test
    fun `delete user data`() {
        mockServer.enqueue(createMockResponse(HttpURLConnection.HTTP_OK))

        runBlocking {
            val responseBody = userApi.deleteUser(
                jwtToken = JWT_TOKEN,
                countryCode = COUNTRY_CODE
            ).body()
            assertNotNull(responseBody)
        }
    }

    @Test
    fun `fetch profile picture bytes 200`() {
        mockServer.enqueue(createMockResponse(HttpURLConnection.HTTP_OK))

        runBlocking {
            val responseBody = userApi.fetchProfilePictureIfModified(
                jwtToken = JWT_TOKEN,
                eTag = null
            ).body()
            assertNotNull(responseBody)
        }
    }

    @Test
    fun `fetch profile picture bytes 304`() {
        mockServer.enqueue(createMockResponse(HttpURLConnection.HTTP_NOT_MODIFIED))

        runBlocking {
            val responseBody = userApi.fetchProfilePictureIfModified(
                jwtToken = JWT_TOKEN,
                eTag = ""
            ).body()
            assertNull(responseBody)
        }
    }

    @Test
    fun `update profile picture`() {
        mockServer.enqueue(createMockResponse(HttpURLConnection.HTTP_OK))

        runBlocking {
            val responseBody = userApi.updateProfilePicture(
                jwtToken = JWT_TOKEN,
                image = ByteArray(1234).toRequestBody(MEDIA_TYPE.toMediaTypeOrNull(), 0)
            ).body()
            assertNotNull(responseBody)
        }
    }

    @Test
    fun `fetch countries`() {
        mockServer.enqueue(createMockResponse(HttpURLConnection.HTTP_OK, COUNTRIES_JSON_FILE))

        runBlocking {
            val responseBody = userApi.fetchCountries(
                locale = HEADER_LOCALE
            ).body()

            assertNotNull(responseBody)
            val countryResponse = responseBody?.first()
            assertEquals(JSON_FIELD_VALUE, countryResponse?.countryCode)
            assertEquals(JSON_FIELD_VALUE, countryResponse?.countryName)
            assertEquals(null, countryResponse?.instance)
            assertEquals(JSON_FIELD_VALUE, countryResponse?.legalRegion)
            assertEquals(true, countryResponse?.connectCountry)
            assertEquals(true, countryResponse?.natconCountry)
            assertEquals(JSON_FIELD_VALUE, countryResponse?.locales?.first()?.localeCode)
        }
    }

    @Test
    fun `set pin`() {
        mockServer.enqueue(createMockResponse(HttpURLConnection.HTTP_OK))

        val setPinRequest = mockk<SetPinRequest>()
        runBlocking {
            val responseBody = userApi.setPin(
                jwtToken = JWT_TOKEN,
                pinRequest = setPinRequest
            ).body()

            assertNotNull(responseBody)
        }
    }

    @Test
    fun `change pin`() {
        mockServer.enqueue(createMockResponse(HttpURLConnection.HTTP_OK))

        val changePinRequest = mockk<ChangePinRequest>()
        runBlocking {
            val responseBody = userApi.changePin(
                jwtToken = JWT_TOKEN,
                pinRequest = changePinRequest
            ).body()

            assertNotNull(responseBody)
        }
    }

    @Test
    fun `delete pin`() {
        mockServer.enqueue(createMockResponse(HttpURLConnection.HTTP_OK))

        runBlocking {
            val responseBody = userApi.deletePin(
                jwtToken = JWT_TOKEN,
                currentPin = "12345"
            ).body()

            assertNotNull(responseBody)
        }
    }

    @Test
    fun `send biometric activation`() {
        mockServer.enqueue(createMockResponse(HttpURLConnection.HTTP_OK))

        val userBiometricActivationStateRequest = mockk<UserBiometricActivationStateRequest>()
        runBlocking {
            val sendBiometricActivationResponse = userApi.sendBiometricActivation(
                jwtToken = JWT_TOKEN,
                countryCode = COUNTRY_CODE,
                body = userBiometricActivationStateRequest
            ).body()

            assertNotNull(sendBiometricActivationResponse)
        }
    }

    @Test
    fun `fetch profile fields`(softly: SoftAssertions) {
        mockServer.enqueue(createMockResponse(HttpURLConnection.HTTP_OK, PROFILE_JSON_FILE))

        runBlocking {
            val profileFieldsDataResponse = userApi.fetchProfileFields(
                headerLocale = HEADER_LOCALE,
                countryCode = COUNTRY_CODE
            ).body()

            val customerDataFieldResponse = profileFieldsDataResponse?.customerDataFields?.first()
            softly.assertThat(customerDataFieldResponse).isNotNull
            softly.assertThat(customerDataFieldResponse?.sequenceOrder).isEqualTo(0)
            softly.assertThat(customerDataFieldResponse?.fieldUsageResponse)
                .isEqualTo(ProfileFieldUsageResponse.INVISIBLE)
            val fieldValidation = customerDataFieldResponse?.fieldValidation
            softly.assertThat(fieldValidation?.minLength).isEqualTo(0)
            softly.assertThat(fieldValidation?.maxLength).isEqualTo(0)
            softly.assertThat(fieldValidation?.regularExpression).isEqualTo(JSON_FIELD_VALUE)
            val selectableValues = customerDataFieldResponse?.selectableValues
            softly.assertThat(selectableValues?.matchSelectableValueByKey).isEqualTo(true)
            softly.assertThat(selectableValues?.defaultSelectableValueKey)
                .isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(selectableValues?.selectableValues?.first()?.key)
                .isEqualTo(JSON_FIELD_VALUE)
            softly.assertThat(selectableValues?.selectableValues?.first()?.description)
                .isEqualTo(JSON_FIELD_VALUE)

            val groupDependencyResponse = profileFieldsDataResponse?.groupDependencies?.first()
            softly.assertThat(groupDependencyResponse?.fieldType)
                .isEqualTo(ProfileDataFieldRelationshipTypeResponse.GROUP)

            val fieldDependencyResponse = profileFieldsDataResponse?.fieldDependencies?.first()
            softly.assertThat(fieldDependencyResponse?.fieldOwnerType)
                .isEqualTo(FieldOwnerTypeResponse.ACCOUNT)
            softly.assertThat(fieldDependencyResponse?.fieldType)
                .isEqualTo(ProfileDataFieldRelationshipTypeResponse.GROUP)
        }
    }

    private fun createMockResponse(responseCode: Int, jsonFilePath: String? = null) =
        MockResponse().apply {
            setResponseCode(responseCode)
            jsonFilePath?.let {
                val resource = UserApiTest::class.java.getResource(it)
                val readText = resource?.readText()
                readText?.let { text ->
                    setBody(text)
                }
            }
        }

    companion object {
        private const val JSON_FIELD_VALUE = "string"
        private const val JWT_TOKEN = "MyJwtToken"
        private const val HEADER_LOCALE = "de"
        private const val COUNTRY_CODE = "DE"
        private const val TIMESTAMP_EXAMPLE = "2020-03-18T15:31:33.148Z"
        private const val MEDIA_TYPE = "image/jpeg"

        // JSON Files
        private const val LOGIN_JSON_FILE = "/login_200.json"
        private const val USER_JSON_FILE = "/get_user_200.json"
        private const val CREATE_USER_JSON_FILE = "/post_user_200.json"
        private const val UPDATE_USER_JSON_FILE = "/put_user_200.json"
        private const val COUNTRIES_JSON_FILE = "/get_countries_200.json"
        private const val PROFILE_JSON_FILE = "/get_profile_fields_200.json"
    }
}
