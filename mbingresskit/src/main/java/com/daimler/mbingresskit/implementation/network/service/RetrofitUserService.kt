package com.daimler.mbingresskit.implementation.network.service

import com.daimler.mbingresskit.common.Countries
import com.daimler.mbingresskit.common.LoginUser
import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbingresskit.common.RegistrationUser
import com.daimler.mbingresskit.common.RegistrationUserAgreementUpdates
import com.daimler.mbingresskit.common.UnitPreferences
import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.common.UserAdaptionValues
import com.daimler.mbingresskit.common.UserBiometricState
import com.daimler.mbingresskit.common.UserBodyHeight
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.implementation.etag.ETagProvider
import com.daimler.mbingresskit.implementation.etag.profilePicture
import com.daimler.mbingresskit.implementation.network.api.UserApi
import com.daimler.mbingresskit.implementation.network.error.NotModifiedError
import com.daimler.mbingresskit.implementation.network.executor.ProfilePictureRequestExecutor
import com.daimler.mbingresskit.implementation.network.mapDefaultInputError
import com.daimler.mbingresskit.implementation.network.mapRegistrationErrors
import com.daimler.mbingresskit.implementation.network.model.adaptionvalues.toApiUserAdaptionValues
import com.daimler.mbingresskit.implementation.network.model.biometric.UserBiometricActivationStateRequest
import com.daimler.mbingresskit.implementation.network.model.biometric.UserBiometricApiState
import com.daimler.mbingresskit.implementation.network.model.country.CountryResponse
import com.daimler.mbingresskit.implementation.network.model.country.toCountries
import com.daimler.mbingresskit.implementation.network.model.pin.ChangePinRequest
import com.daimler.mbingresskit.implementation.network.model.pin.LoginUserRequest
import com.daimler.mbingresskit.implementation.network.model.pin.SetPinRequest
import com.daimler.mbingresskit.implementation.network.model.profilefields.ProfileFieldsDataResponse
import com.daimler.mbingresskit.implementation.network.model.unitpreferences.toUserUnitPreferences
import com.daimler.mbingresskit.implementation.network.model.user.ApiVerifyUserRequest
import com.daimler.mbingresskit.implementation.network.model.user.create.CreateUserResponse
import com.daimler.mbingresskit.implementation.network.model.user.create.toCreateUserRequest
import com.daimler.mbingresskit.implementation.network.model.user.fetch.UserTokenResponse
import com.daimler.mbingresskit.implementation.network.model.user.update.toUpdateUserRequest
import com.daimler.mbingresskit.implementation.network.ropc.nonce.NonceProvider
import com.daimler.mbingresskit.implementation.network.ropc.nonce.loginNonce
import com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.LoginRequestExecutor
import com.daimler.mbingresskit.login.UserService
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.common.ErrorMapStrategy
import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.MappableRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.NoContentRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.RequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.RequestResult
import com.daimler.mbnetworkkit.networking.coroutines.dispatchError
import com.daimler.mbnetworkkit.networking.coroutines.dispatchResult
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import com.daimler.mbnetworkkit.task.TaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

internal class RetrofitUserService(
    userApi: UserApi,
    private val headerService: HeaderService,
    private val eTagProvider: ETagProvider,
    private val nonceProvider: NonceProvider,
    scope: CoroutineScope = NetworkCoroutineScope()
) : CoroutineScope by scope, UserService, BaseRetrofitService<UserApi>(userApi, scope) {

    override fun sendTan(
        userName: String,
        countryCode: String
    ): FutureTask<LoginUser, ResponseError<out RequestError>?> {
        val nonce = newNonce()
        nonceProvider.loginNonce = nonce
        val task = ResponseTaskObject<LoginUser>()

        launch {
            val result = LoginRequestExecutor().execute {
                api.sendTan(
                    LoginUserRequest(
                        emailOrPhoneNumber = userName,
                        countryCode = countryCode,
                        locale = headerService.currentNetworkLocale(),
                        nonce = nonce
                    )
                )
            }

            when (result) {
                is RequestResult.Success -> {
                    val loginResponse = result.body.response
                    val authMode = result.body.authMode
                    authMode?.let {
                        MBLoggerKit.i("Backend chose auth mode: $it")
                        headerService.updateAuthMode(it)
                    }
                    val authMethod = AuthenticationType.values().find { it.authMode == authMode }
                        ?: AuthenticationType.KEYCLOAK
                    task.dispatchResult(
                        LoginUser(
                            loginResponse.userName,
                            loginResponse.isEmail,
                            authMethod
                        )
                    )
                }
                is RequestResult.Error -> task.dispatchError(result.error)
            }
        }

        return task.futureTask()
    }

    override fun sendPin(
        userName: String,
        countryCode: String
    ) = sendTan(userName, countryCode)

    override fun loadUser(jwtToken: String): FutureTask<User, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<User>()
        launch {
            RequestExecutor<UserTokenResponse, User>().executeWithTask(task) {
                api.fetchUserData(jwtToken)
            }
        }
        return task.futureTask()
    }

    override fun createUser(
        useMailAsUsername: Boolean,
        user: User
    ): FutureTask<RegistrationUser, ResponseError<out RequestError>?> =
        createUserInternal(useMailAsUsername, user, null)

    override fun createUserWithConsent(
        useMailAsUsername: Boolean,
        user: User,
        consent: RegistrationUserAgreementUpdates
    ): FutureTask<RegistrationUser, ResponseError<out RequestError>?> =
        createUserInternal(useMailAsUsername, user, consent)

    override fun updateUser(
        jwtToken: String,
        user: User
    ): FutureTask<User, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<User>()
        launch {
            RequestExecutor<UserTokenResponse, User>(getInputErrorMapping())
                .executeWithTask(task) {
                    api.updateUser(
                        jwtToken,
                        user.countryCode,
                        user.toUpdateUserRequest()
                    )
                }
        }
        return task.futureTask()
    }

    override fun deleteUser(
        jwtToken: String,
        user: User
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()

        launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteUser(
                    jwtToken,
                    user.countryCode
                )
            }
        }

        return task.futureTask()
    }

    override fun updateProfilePicture(
        jwtToken: String,
        bitmapByteArray: ByteArray,
        mediaType: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.updateProfilePicture(
                    jwtToken,
                    RequestBody.create(MediaType.parse(mediaType), bitmapByteArray)
                )
            }
        }
        return task.futureTask()
    }

    override fun fetchProfilePictureBytes(jwtToken: String): FutureTask<ByteArray, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<ByteArray>()

        launch {
            val result = ProfilePictureRequestExecutor().execute {
                api.fetchProfilePictureIfModified(
                    jwtToken,
                    eTagProvider.profilePicture
                )
            }
            when (result) {
                is RequestResult.Success ->
                    when (val body = result.body) {
                        is ProfilePictureRequestExecutor.Result.Picture -> {
                            eTagProvider.profilePicture = body.eTag
                            task.dispatchResult(body.bytes)
                        }
                        ProfilePictureRequestExecutor.Result.NotModified ->
                            task.dispatchError(ResponseError.requestError(NotModifiedError()))
                    }
                is RequestResult.Error -> task.dispatchError(result.error)
            }
        }

        return task.futureTask()
    }

    override fun fetchCountries(): FutureTask<Countries, ResponseError<out RequestError>?> {
        val task = TaskObject<Countries, ResponseError<out RequestError>?>()
        launch {
            MappableRequestExecutor<List<CountryResponse>, Countries> { it.toCountries() }
                .executeWithTask(task) {
                    api.fetchCountries(
                        headerService.currentNetworkLocale()
                    )
                }
        }
        return task.futureTask()
    }

    override fun setPin(
        jwtToken: String,
        pin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        launch {
            getPinManipulatingExecutor().executeWithTask(task) {
                api.setPin(
                    jwtToken,
                    SetPinRequest(pin)
                )
            }
        }
        return task.futureTask()
    }

    override fun changePin(
        jwtToken: String,
        currentPin: String,
        newPin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = TaskObject<Unit, ResponseError<out RequestError>?>()
        launch {
            getPinManipulatingExecutor().executeWithTask(task) {
                api.changePin(
                    jwtToken,
                    ChangePinRequest(currentPin, newPin)
                )
            }
        }
        return task.futureTask()
    }

    override fun deletePin(
        jwtToken: String,
        currentPin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = TaskObject<Unit, ResponseError<out RequestError>?>()
        launch {
            getPinManipulatingExecutor().executeWithTask(task) {
                api.deletePin(
                    jwtToken,
                    currentPin
                )
            }
        }
        return task.futureTask()
    }

    override fun resetPin(jwtToken: String): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = TaskObject<Unit, ResponseError<out RequestError>?>()
        launch {
            getPinManipulatingExecutor().executeWithTask(task) {
                api.resetPin(jwtToken)
            }
        }
        return task.futureTask()
    }

    override fun sendBiometricActivation(
        jwtToken: String,
        countryCode: String,
        state: UserBiometricState,
        currentPin: String?
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = TaskObject<Unit, ResponseError<out RequestError>?>()
        launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.sendBiometricActivation(
                    jwtToken,
                    countryCode,
                    UserBiometricActivationStateRequest(
                        currentPin.orEmpty(),
                        UserBiometricApiState.forName(state.name)
                    )
                )
            }
        }
        return task.futureTask()
    }

    override fun updateUnitPreferences(
        jwtToken: String,
        unitPreferences: UnitPreferences
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = TaskObject<Unit, ResponseError<out RequestError>?>()
        launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.updateUnitPreferences(
                    jwtToken,
                    unitPreferences.toUserUnitPreferences()
                )
            }
        }
        return task.futureTask()
    }

    override fun updateAdaptionValues(
        jwtToken: String,
        bodyHeight: UserBodyHeight
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        return updateAdaptionValues(jwtToken, UserAdaptionValues(bodyHeight, null))
    }

    override fun updateAdaptionValues(
        jwtToken: String,
        userAdaptionValues: UserAdaptionValues
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = TaskObject<Unit, ResponseError<out RequestError>?>()
        launch {
            NoContentRequestExecutor(getInputErrorMapping()).executeWithTask(task) {
                api.updateAdaptionValues(
                    jwtToken,
                    userAdaptionValues.toApiUserAdaptionValues()
                )
            }
        }
        return task.futureTask()
    }

    override fun fetchProfileFields(
        countryCode: String,
        locale: String?
    ): FutureTask<ProfileFieldsData, ResponseError<out RequestError>?> {
        val task = TaskObject<ProfileFieldsData, ResponseError<out RequestError>?>()
        launch {
            RequestExecutor<ProfileFieldsDataResponse, ProfileFieldsData>()
                .executeWithTask(task) {
                    api.fetchProfileFields(
                        locale ?: headerService.currentNetworkLocale(),
                        countryCode
                    )
                }
        }
        return task.futureTask()
    }

    override fun verifyUser(
        jwtToken: String,
        scanReference: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = TaskObject<Unit, ResponseError<out RequestError>?>()
        launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.verifyUser(
                    jwtToken,
                    ApiVerifyUserRequest(scanReference)
                )
            }
        }
        return task.futureTask()
    }

    private fun createUserInternal(
        useMailAsUsername: Boolean,
        user: User,
        agreementUpdates: RegistrationUserAgreementUpdates?
    ): FutureTask<RegistrationUser, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<RegistrationUser>()
        launch {
            val errorMapping = agreementUpdates?.let {
                getRegistrationErrorMapping()
            } ?: getInputErrorMapping()
            RequestExecutor<CreateUserResponse, RegistrationUser>(errorMapping)
                .executeWithTask(task) {
                    val nonce = newNonce()
                    nonceProvider.loginNonce = nonce
                    api.createUser(
                        user.languageCode,
                        user.toCreateUserRequest(useMailAsUsername, agreementUpdates?.updates, nonce)
                    )
                }
        }
        return task.futureTask()
    }

    private fun getPinManipulatingExecutor() = NoContentRequestExecutor(getInputErrorMapping())

    private fun getInputErrorMapping() = ErrorMapStrategy.Custom {
        mapDefaultInputError(it)
    }

    private fun getRegistrationErrorMapping() = ErrorMapStrategy.Custom {
        mapRegistrationErrors(it)
    }
}
