package com.daimler.mbingresskit

import android.os.Handler
import android.os.Looper
import com.daimler.mbingresskit.common.AuthorizationException
import com.daimler.mbingresskit.common.AuthorizationResponse
import com.daimler.mbingresskit.common.Countries
import com.daimler.mbingresskit.common.LoginUser
import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbingresskit.common.RegistrationUser
import com.daimler.mbingresskit.common.RegistrationUserAgreementUpdates
import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.common.UnitPreferences
import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.common.UserAdaptionValues
import com.daimler.mbingresskit.common.UserBiometricState
import com.daimler.mbingresskit.common.UserBodyHeight
import com.daimler.mbingresskit.common.UserCredentials
import com.daimler.mbingresskit.common.VerificationConfirmation
import com.daimler.mbingresskit.common.VerificationTransaction
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.implementation.AuthstateRepository
import com.daimler.mbingresskit.implementation.etag.ETagProvider
import com.daimler.mbingresskit.implementation.network.ropc.tokenexchange.TokenExchangeService
import com.daimler.mbingresskit.login.AuthenticationService
import com.daimler.mbingresskit.login.AuthenticationStateTokenState
import com.daimler.mbingresskit.login.LoginFailure
import com.daimler.mbingresskit.login.LoginService
import com.daimler.mbingresskit.login.LoginServiceFactory
import com.daimler.mbingresskit.login.LoginStateService
import com.daimler.mbingresskit.login.LogoutFailure
import com.daimler.mbingresskit.login.SessionExpiredHandler
import com.daimler.mbingresskit.login.TokenState
import com.daimler.mbingresskit.login.UserService
import com.daimler.mbingresskit.login.VerificationService
import com.daimler.mbingresskit.persistence.ProfileFieldsCache
import com.daimler.mbingresskit.persistence.UserCache
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.Task
import com.daimler.mbnetworkkit.task.TaskObject
import java.util.UUID
import java.util.concurrent.LinkedBlockingQueue

/**
 * Proxy class
 */
internal class ServiceProxy(
    private val authStateRepository: AuthstateRepository,
    private val sessionExpiredHandler: SessionExpiredHandler?,
    val userCache: UserCache,
    val profileFieldsCache: ProfileFieldsCache,
    val eTagProvider: ETagProvider,
    private val userService: UserService,
    private val verificationService: VerificationService,
    private val loginServiceFactory: LoginServiceFactory,
    private val tokenExchangeService: TokenExchangeService
) : LoginService,
    AuthenticationService,
    UserService,
    VerificationService,
    TokenExchangeService {

    private val lastLoginService
        get() = loadLastLoginServiceIfLoggedIn()

    private var loginService: LoginService? = lastLoginService
        get() = if (field == null) lastLoginService else field

    private var loginStateService: LoginStateService? = lastLoginService
        get() = if (field == null) lastLoginService else field

    private var loginTask: TaskObject<Unit, ResponseError<out RequestError>?> = TaskObject()

    private var logoutTask: TaskObject<Unit, ResponseError<out RequestError>?> = TaskObject()

    // holds task objects returned by refreshToken(); this is to support concurrent refresh requests
    private val refreshQueue = RefreshQueue()

    // This attribute is provided by the backend with auth mode header. This should switch/migrate the authentication flow from KEYCLOAK to CIAM
    private var authenticationTypeProvidedByBFF: AuthenticationType? = null

    fun login(userCredentials: UserCredentials): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Login with userCredentials: ${userCredentials.userName} + ${userCredentials.password}")
        return startLoginIfNotInProgress(
            loginServiceFactory.createLoginService(
                userCredentials
            )
        )
    }

    private fun startLoginIfNotInProgress(loginService: LoginService): FutureTask<Unit, ResponseError<out RequestError>?> {
        this.loginService = loginService
        this.loginStateService = loginService
        return startLogin()
    }

    private fun loadLastLoginServiceIfLoggedIn(): LoginService? {
        return if (getTokenState() != TokenState.LOGGEDOUT) {
            MBLoggerKit.d("User already logged in -> Load last LoginService")
            loginServiceFactory.createLoginService()
        } else {
            null
        }
    }

    // LoginService START

    override fun startLogin(): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Start Login-Process")
        authenticationTypeProvidedByBFF?.let {
            loginService?.changeAuthenticationType(it)
        }
        val deferredLoginTask = loginService?.startLogin() ?: run {
            MBLoggerKit.e(
                "Login-Task failed because no LoginService instance provided.",
                LOGGER_TAG_AUTHORIZATION
            )
            loginTask.fail(ResponseError.requestError(LoginFailure.FAILED))
            return loginTask.futureTask()
        }

        deferredLoginTask.onComplete { result ->
            MBLoggerKit.d("Login-Task completed")
            UUID.randomUUID().toString().let {
                MBLoggerKit.d("Set Login ID=$it")
                authStateRepository.saveUserLoginId(it)
            }
            loginTask.complete(result)
        }.onFailure {
            loginService = null
            loginStateService = null
            MBLoggerKit.e("Login-Task failed: ${it?.requestError}")
            loginTask.fail(it)
        }.onAlways { _, _, _ ->
            loginTask = TaskObject()
        }
        return loginTask.futureTask()
    }

    override fun startLogout(): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Start Logout-Process")
        val deferredLogoutTask = loginService?.startLogout() ?: run {
            MBLoggerKit.e(
                "Logout-Task failed because no LoginService instance provided.",
                LOGGER_TAG_LOGOUT
            )
            logoutTask.fail(ResponseError.requestError(LogoutFailure.FAILED))
            return logoutTask.futureTask()
        }
        deferredLogoutTask.onComplete {
            MBLoggerKit.d("Logout-Task completed")
            logoutTask.complete(it)
        }.onFailure {
            MBLoggerKit.e("Logout-Task failed")
            logoutTask.fail(it)
        }.onAlways { _, _, _ ->
            authStateRepository.clearUserLoginId()
            loginService = null
            loginStateService = null
            logoutTask = TaskObject()
        }
        return logoutTask.futureTask()
    }

    override fun changeAuthenticationType(authenticationType: AuthenticationType) {
        loginService?.changeAuthenticationType(authenticationType)
    }

    override fun logoutConfirmed() = loginService?.logoutConfirmed() ?: run {
        onNoLogoutServiceInstanceProvided()
    }

    // LoginService END

    // AuthenticationService START

    /**
     * returns the users' unique login ID (UUID)
     * this ID does not relate to any user, just to current login session
     * only available for SSO enabled apps
     */
    override fun getUserLoginId(): String? {
        return authStateRepository.readUserLoginId()?.also {
            MBLoggerKit.d("Get App Login ID = $it")
        }
    }

    override fun getTokenState(): TokenState {
        val authState = authStateRepository.readAuthState()
        return AuthenticationStateTokenState(authState).getTokenState()
    }

    override fun needsTokenRefresh(): Boolean {
        // todo: maybe handle state if logged out and throw an exception
        return getTokenState() is TokenState.LOGGEDIN
    }

    // AuthenticationService END

    // LoginStateService START

    override fun authorizationStarted() = loginStateService?.authorizationStarted() ?: run {
        onNoLoginServiceInstanceProvided()
    }

    override fun receivedAuthResponse(
        authResponse: AuthorizationResponse?,
        authException: AuthorizationException?
    ) = loginStateService?.receivedAuthResponse(authResponse, authException) ?: run {
        onNoLoginServiceInstanceProvided()
    }

    override fun loginCancelled() = loginStateService?.loginCancelled() ?: run {
        onNoLoginServiceInstanceProvided()
    }

    override fun receivedLogoutResponse(
        authResponse: AuthorizationResponse?,
        authException: AuthorizationException?
    ) = loginStateService?.receivedLogoutResponse(authResponse, authException) ?: run {
        onNoLogoutServiceInstanceProvided()
    }

    override fun logoutCancelled() = loginStateService?.logoutCancelled() ?: run {
        onNoLogoutServiceInstanceProvided()
    }

    // LoginStateService END

    // RefreshTokenService START
    override fun forceTokenRefresh() {
        MBLoggerKit.d("Forced to refresh Token")
        val authState = authStateRepository.readAuthState().apply {
            forceTokenRefresh()
        }
        authStateRepository.saveAuthState(authState)
    }

    override fun refreshToken(): FutureTask<Token, Throwable?> {
        val handler = Handler(Looper.getMainLooper())
        return when (val state = getTokenState()) {
            is TokenState.LOGGEDOUT -> state.handleTokenRefresh(handler)
            is TokenState.LOGGEDIN -> state.handleTokenRefresh(
                handler,
                sessionExpiredHandler,
                refreshQueue,
                loginService
            )
            else -> {
                MBLoggerKit.d("refreshToken -> no refresh required")
                val tokenTask = TaskObject<Token, Throwable?>()
                handler.post { tokenTask.complete(authStateRepository.readAuthState().getToken()) }
                tokenTask.futureTask()
            }
        }
    }

    // RefreshTokenService START

    // UserService START

    override fun sendTan(
        userName: String,
        countryCode: String
    ): FutureTask<LoginUser, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Send TAN for $userName in country $countryCode.")
        val task: Task<LoginUser, ResponseError<out RequestError>?> = TaskObject()
        userService.sendTan(userName, countryCode).onComplete {
            authenticationTypeProvidedByBFF = it.authenticationType
            task.complete(it)
        }.onFailure {
            task.fail(it)
        }
        return task.futureTask()
    }

    override fun sendPin(
        userName: String,
        countryCode: String
    ) = sendTan(userName, countryCode)

    override fun loadUser(jwtToken: String): FutureTask<User, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Load user: JWTToken=$jwtToken")
        return userService.loadUser(jwtToken)
    }

    override fun createUser(
        useMailAsUsername: Boolean,
        user: User
    ): FutureTask<RegistrationUser, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Create User: useMailAsUsername=$useMailAsUsername, user=$user")
        return userService.createUser(useMailAsUsername, user)
    }

    override fun createUserWithConsent(
        useMailAsUsername: Boolean,
        user: User,
        consent: RegistrationUserAgreementUpdates
    ): FutureTask<RegistrationUser, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Create User with Consent: useMailAsUsername = $useMailAsUsername, user = $user, consent = $consent")
        return userService.createUserWithConsent(useMailAsUsername, user, consent)
    }

    override fun updateUser(
        jwtToken: String,
        user: User
    ): FutureTask<User, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Update User: JWTToken=$jwtToken, $user ")
        return userService.updateUser(jwtToken, user)
    }

    override fun deleteUser(
        jwtToken: String,
        user: User
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Delete User: ${user.email}")
        return userService.deleteUser(jwtToken, user)
    }

    override fun updateProfilePicture(
        jwtToken: String,
        bitmapByteArray: ByteArray,
        mediaType: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Update Profile Picture")
        return userService.updateProfilePicture(jwtToken, bitmapByteArray, mediaType)
    }

    override fun fetchProfilePictureBytes(jwtToken: String): FutureTask<ByteArray, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Fetch Profile Picture Bytes")
        return userService.fetchProfilePictureBytes(jwtToken)
    }

    override fun fetchCountries(): FutureTask<Countries, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Fetch countries")
        return userService.fetchCountries()
    }

    override fun setPin(
        jwtToken: String,
        pin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Set Pin: $pin")
        return userService.setPin(jwtToken, pin)
    }

    override fun changePin(
        jwtToken: String,
        currentPin: String,
        newPin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Change Pin: currentPin = $currentPin, newPin = $newPin")
        return userService.changePin(jwtToken, currentPin, newPin)
    }

    override fun deletePin(
        jwtToken: String,
        currentPin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Delete Pin: $currentPin")
        return userService.deletePin(jwtToken, currentPin)
    }

    override fun resetPin(jwtToken: String): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Reset Pin")
        return userService.resetPin(jwtToken)
    }

    override fun sendBiometricActivation(
        jwtToken: String,
        countryCode: String,
        state: UserBiometricState,
        currentPin: String?
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Send biometric activation: $state")
        return userService.sendBiometricActivation(jwtToken, countryCode, state, currentPin)
    }

    override fun updateUnitPreferences(
        jwtToken: String,
        unitPreferences: UnitPreferences
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Update unit preferences: $unitPreferences")
        return userService.updateUnitPreferences(jwtToken, unitPreferences)
    }

    override fun updateAdaptionValues(
        jwtToken: String,
        bodyHeight: UserBodyHeight
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Update adaption values: $bodyHeight")
        return userService.updateAdaptionValues(jwtToken, bodyHeight)
    }

    override fun updateAdaptionValues(
        jwtToken: String,
        userAdaptionValues: UserAdaptionValues
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Update adaption values: $userAdaptionValues")
        return userService.updateAdaptionValues(jwtToken, userAdaptionValues)
    }

    override fun fetchProfileFields(
        countryCode: String,
        locale: String?
    ): FutureTask<ProfileFieldsData, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Fetch profile fields: countryCode = $countryCode.")
        return userService.fetchProfileFields(countryCode, locale)
    }

    override fun verifyUser(
        jwtToken: String,
        scanReference: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        MBLoggerKit.d("Verify user with scanReference: $scanReference")
        return userService.verifyUser(jwtToken, scanReference)
    }

    // UserService END

    // VerificationService START
    override fun sendTransaction(
        jwtToken: String,
        payload: VerificationTransaction
    ): FutureTask<Unit, ResponseError<out RequestError>?> =
        verificationService.sendTransaction(jwtToken, payload)

    override fun sendConfirmation(
        jwtToken: String,
        payload: VerificationConfirmation
    ): FutureTask<Unit, ResponseError<out RequestError>?> =
        verificationService.sendConfirmation(jwtToken, payload)

    // VerificationService END

    private fun onNoLoginServiceInstanceProvided() {
        MBLoggerKit.e(
            "Login-Task failed because no LoginService instance provided.",
            LOGGER_TAG_AUTHORIZATION
        )
        loginTask.fail(ResponseError.requestError(LoginFailure.FAILED))
        loginTask = TaskObject()
    }

    private fun onNoLogoutServiceInstanceProvided() {
        MBLoggerKit.e(
            "Logout-Task failed because no LoginService instance provided.",
            LOGGER_TAG_LOGOUT
        )
        logoutTask.fail(ResponseError.requestError(LogoutFailure.NOT_LOGGED_IN))
        logoutTask = TaskObject()
    }

    companion object {
        private const val LOGGER_TAG_AUTHORIZATION = "AUTHORIZATION"
        private const val LOGGER_TAG_LOGOUT = "LOGOUT"
    }

    override fun isExchangeTokenPossible(): Boolean = tokenExchangeService.isExchangeTokenPossible()

    override fun exchangeToken(): FutureTask<Unit, TokenExchangeService.Error> {
        val task: Task<Unit, TokenExchangeService.Error> = TaskObject()
        tokenExchangeService.exchangeToken().onComplete {
            MBLoggerKit.d("Switch loginService to AuthenticationType CIAM after token exchange")
            loginService?.changeAuthenticationType(AuthenticationType.CIAM)
            task.complete(Unit)
        }.onFailure {
            task.fail(it)
        }
        return task.futureTask()
    }
}

typealias RefreshQueue = LinkedBlockingQueue<TaskObject<Token, Throwable?>>
