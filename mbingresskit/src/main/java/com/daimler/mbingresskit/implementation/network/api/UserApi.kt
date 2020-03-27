package com.daimler.mbingresskit.implementation.network.api

import com.daimler.mbingresskit.implementation.network.HttpHeaderConstants.Companion.HEADER_AUTHORIZATION
import com.daimler.mbingresskit.implementation.network.HttpHeaderConstants.Companion.HEADER_COUNTRY_CODE
import com.daimler.mbingresskit.implementation.network.HttpHeaderConstants.Companion.HEADER_LOCALE
import com.daimler.mbingresskit.implementation.network.model.adaptionvalues.ApiUserAdaptionValues
import com.daimler.mbingresskit.implementation.network.model.biometric.UserBiometricActivationStateRequest
import com.daimler.mbingresskit.implementation.network.model.country.CountryResponse
import com.daimler.mbingresskit.implementation.network.model.pin.ChangePinRequest
import com.daimler.mbingresskit.implementation.network.model.pin.LoginUserRequest
import com.daimler.mbingresskit.implementation.network.model.pin.LoginUserResponse
import com.daimler.mbingresskit.implementation.network.model.pin.SetPinRequest
import com.daimler.mbingresskit.implementation.network.model.profilefields.ProfileFieldsDataResponse
import com.daimler.mbingresskit.implementation.network.model.unitpreferences.UserUnitPreferences
import com.daimler.mbingresskit.implementation.network.model.user.ApiVerifyUserRequest
import com.daimler.mbingresskit.implementation.network.model.user.create.CreateUserRequest
import com.daimler.mbingresskit.implementation.network.model.user.create.CreateUserResponse
import com.daimler.mbingresskit.implementation.network.model.user.fetch.UserTokenResponse
import com.daimler.mbingresskit.implementation.network.model.user.update.UpdateUserRequest
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

internal interface UserApi {

    companion object {
        private const val PATH_VERSION = "/v1"
        private const val PATH_USERS = "/user"
        private const val PATH_LOGIN = "/login"
        private const val PATH_PIN = "/pin"
        private const val PATH_RESET = "/reset"
        private const val PATH_COUNTRIES = "/countries"
        private const val PATH_BIOMETRIC = "/biometric"
        private const val PATH_PROFILEPICTURE = "/profilepicture"
        private const val PATH_UNIT_PREFERENCES = "/unitpreferences"
        private const val PATH_ADAPTION_VALUES = "/adaptionValues"
        private const val PATH_PROFILE = "/profile"
        private const val PATH_FIELDS = "/fields"
        private const val PATH_VERIFICATION = "/verification"
        private const val PATH_SELF = "self"

        private const val HEADER_IF_NONE_MATCH = "If-None-Match"

        private const val QUERY_LOCALE = "locale"
        private const val QUERY_COUNTRY_CODE = "countryCode"
        private const val QUERY_CURRENT_PIN = "currentPin"
    }

    @POST("$PATH_VERSION$PATH_LOGIN")
    suspend fun sendTan(
        @Body body: LoginUserRequest
    ): Response<LoginUserResponse>

    @POST("$PATH_VERSION$PATH_USERS/$PATH_SELF$PATH_PIN")
    suspend fun setPin(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Body pinRequest: SetPinRequest
    ): Response<Unit>

    @PUT("$PATH_VERSION$PATH_USERS/$PATH_SELF$PATH_PIN")
    suspend fun changePin(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Body pinRequest: ChangePinRequest
    ): Response<Unit>

    @DELETE("$PATH_VERSION$PATH_USERS/$PATH_SELF$PATH_PIN")
    suspend fun deletePin(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Query(QUERY_CURRENT_PIN) currentPin: String
    ): Response<Unit>

    @POST("$PATH_VERSION$PATH_USERS$PATH_PIN$PATH_RESET")
    suspend fun resetPin(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
    ): Response<Unit>

    @GET("$PATH_VERSION$PATH_USERS/$PATH_SELF")
    suspend fun fetchUserData(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
    ): Response<UserTokenResponse>

    @POST("$PATH_VERSION$PATH_USERS")
    suspend fun createUser(
        @Header(HEADER_LOCALE) locale: String,
        @Body body: CreateUserRequest
    ): Response<CreateUserResponse>

    @PUT("$PATH_VERSION$PATH_USERS/$PATH_SELF")
    suspend fun updateUser(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Header(HEADER_COUNTRY_CODE) countryCode: String,
        @Body body: UpdateUserRequest
    ): Response<UserTokenResponse>

    @DELETE("$PATH_VERSION$PATH_USERS/$PATH_SELF")
    suspend fun deleteUser(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Header(HEADER_COUNTRY_CODE) countryCode: String
    ): Response<Unit>

    @PUT("$PATH_VERSION$PATH_USERS/$PATH_SELF$PATH_PROFILEPICTURE")
    suspend fun updateProfilePicture(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Body image: RequestBody
    ): Response<Unit>

    @GET("$PATH_VERSION$PATH_USERS$PATH_PROFILEPICTURE")
    suspend fun fetchProfilePictureIfModified(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Header(HEADER_IF_NONE_MATCH) eTag: String?
    ): Response<ResponseBody>

    @GET("$PATH_VERSION$PATH_COUNTRIES")
    suspend fun fetchCountries(
        @Query(QUERY_LOCALE) locale: String
    ): Response<List<CountryResponse>>

    @POST("$PATH_VERSION$PATH_USERS$PATH_BIOMETRIC")
    suspend fun sendBiometricActivation(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Header(HEADER_COUNTRY_CODE) countryCode: String,
        @Body body: UserBiometricActivationStateRequest
    ): Response<Unit>

    @PUT("$PATH_VERSION$PATH_USERS$PATH_UNIT_PREFERENCES")
    suspend fun updateUnitPreferences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Body body: UserUnitPreferences
    ): Response<Unit>

    @PUT("$PATH_VERSION$PATH_USERS$PATH_ADAPTION_VALUES")
    suspend fun updateAdaptionValues(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Body body: ApiUserAdaptionValues
    ): Response<Unit>

    @GET("$PATH_VERSION$PATH_PROFILE$PATH_FIELDS")
    suspend fun fetchProfileFields(
        @Header(HEADER_LOCALE) headerLocale: String,
        @Query(QUERY_COUNTRY_CODE) countryCode: String
    ): Response<ProfileFieldsDataResponse>

    @POST("$PATH_VERSION$PATH_USERS$PATH_VERIFICATION")
    suspend fun verifyUser(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Body body: ApiVerifyUserRequest
    ): Response<Unit>
}
