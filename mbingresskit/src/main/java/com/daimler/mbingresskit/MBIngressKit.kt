package com.daimler.mbingresskit

import android.annotation.SuppressLint
import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.common.UserCredentials
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.implementation.KeepResultsCryptoProvider
import com.daimler.mbingresskit.implementation.PrivateAccountPrefs
import com.daimler.mbingresskit.implementation.SsoAccountPrefs
import com.daimler.mbingresskit.implementation.etag.ETagProvider
import com.daimler.mbingresskit.implementation.etag.PreferencesETagProvider
import com.daimler.mbingresskit.implementation.network.ropc.ROPCTokenRepository
import com.daimler.mbingresskit.implementation.network.ropc.nonce.NonceProvider
import com.daimler.mbingresskit.implementation.network.ropc.nonce.PreferencesNonceProvider
import com.daimler.mbingresskit.implementation.network.ropc.tokenexchange.TokenExchangeService
import com.daimler.mbingresskit.implementation.network.ropc.tokenexchange.TokenExchanger
import com.daimler.mbingresskit.implementation.network.ropc.tokenexchange.ciam.CiamTokenExchangeService
import com.daimler.mbingresskit.implementation.network.ropc.tokenexchange.ciam.CiamTokenExchanger
import com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.TokenServiceFactory
import com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.ciam.CiamApi
import com.daimler.mbingresskit.implementation.network.service.ServiceProvider
import com.daimler.mbingresskit.implementation.network.service.ServiceProviderImpl
import com.daimler.mbingresskit.login.AuthenticationService
import com.daimler.mbingresskit.login.CachedUserService
import com.daimler.mbingresskit.login.LoginServiceFactory
import com.daimler.mbingresskit.login.LoginStateService
import com.daimler.mbingresskit.login.UserService
import com.daimler.mbingresskit.login.VerificationService
import com.daimler.mbingresskit.socket.UserMessageProcessor
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinnerFactory
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.RetrofitHelper
import com.daimler.mbnetworkkit.socket.message.MessageProcessor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbprotokit.MessageParsers

object MBIngressKit {

    @SuppressLint("StaticFieldLeak")
    private lateinit var serviceProxy: ServiceProxy

    fun init(ingressServiceConfig: IngressServiceConfig): MBIngressKit {
        val eTagProvider = PreferencesETagProvider(ingressServiceConfig.context)
        val nonceProvider = PreferencesNonceProvider(ingressServiceConfig.context)
        val retrofitHelper = createRetrofitHelper(ingressServiceConfig)
        val serviceProvider = createServiceProvider(
            config = ingressServiceConfig,
            eTagProvider = eTagProvider,
            nonceProvider = nonceProvider,
            retrofitHelper = retrofitHelper
        )

        val authstateRepository = if (ingressServiceConfig.sharedUserId.isEmpty()) {
            PrivateAccountPrefs(ingressServiceConfig.context, ingressServiceConfig.keyStoreAlias)
        } else {
            SsoAccountPrefs(
                ingressServiceConfig.context,
                ingressServiceConfig.sharedUserId,
                ingressServiceConfig.keyStoreAlias,
                KeepResultsCryptoProvider()
            ).apply {
                initialize()
            }
        }
        val tokenServiceFactory =
            createTokenServiceFactory(ingressServiceConfig, nonceProvider, retrofitHelper)
        val tokenRepository =
            ROPCTokenRepository(
                tokenServiceFactory.createTokenService(ingressServiceConfig.preferredAuthenticationType),
                tokenServiceFactory
            )

        val loginServiceFactory = LoginServiceFactory(
            authStateRepository = authstateRepository,
            tokenRepository = tokenRepository,
            deviceId = ingressServiceConfig.deviceUuid
        )
        val tokenExchangeService =
            CiamTokenExchangeService(
                tokenExchanger = createCiamTokenExchanger(ingressServiceConfig, retrofitHelper),
                authStateRepository = authstateRepository,
                userCache = ingressServiceConfig.userCache,
                deviceId = ingressServiceConfig.deviceUuid
            )

        val userService = createUserService(ingressServiceConfig, serviceProvider, eTagProvider)
        val verificationService = createVerificationService(ingressServiceConfig, serviceProvider)

        serviceProxy = ServiceProxy(
            authStateRepository = authstateRepository,
            sessionExpiredHandler = ingressServiceConfig.sessionExpiredHandler,
            userCache = ingressServiceConfig.userCache,
            profileFieldsCache = ingressServiceConfig.profileFieldsCache,
            eTagProvider = eTagProvider,
            loginServiceFactory = loginServiceFactory,
            userService = userService,
            verificationService = verificationService,
            tokenExchangeService = tokenExchangeService
        )

        return this
    }

    private fun createCiamTokenExchanger(
        config: IngressServiceConfig,
        retrofitHelper: RetrofitHelper
    ): TokenExchanger {
        val authConfig =
            config.authenticationConfigs.find { it.authenticationType == AuthenticationType.CIAM }
                ?: throw IllegalArgumentException("CIAM is not configured correctly")
        return CiamTokenExchanger(
            ciamApi = retrofitHelper.createRetrofit(
                CiamApi::class.java,
                authConfig.baseUrl,
                true,
                RetrofitHelper.LONG_TIMEOUT
            ),
            clientId = authConfig.clientId,
            stage = config.ingressStage,
        )
    }

    private fun createRetrofitHelper(
        config: IngressServiceConfig,
    ) = RetrofitHelper(
        context = config.context,
        headerService = config.headerService,
        certificatePinningErrorProcessor = config.pinningErrorProcessor,
        certificatePinnerProvider = CertificatePinnerFactory(),
        pinningConfigurations = config.pinningConfigurations,
        shouldLogBody = config.logHttpBody
    )

    private fun createTokenServiceFactory(
        config: IngressServiceConfig,
        nonceProvider: NonceProvider,
        retrofitHelper: RetrofitHelper
    ) = TokenServiceFactory(
        stage = config.ingressStage,
        retrofitHelper = retrofitHelper,
        enableLogging = true,
        authenticationConfigs = config.authenticationConfigs.associateBy(
            { it.authenticationType },
            { it }
        ),
        nonceProvider = nonceProvider
    )

    private fun createServiceProvider(
        config: IngressServiceConfig,
        eTagProvider: ETagProvider,
        nonceProvider: NonceProvider,
        retrofitHelper: RetrofitHelper
    ): ServiceProvider =
        ServiceProviderImpl(
            headerService = config.headerService,
            eTagProvider = eTagProvider,
            nonceProvider = nonceProvider,
            retrofitHelper = retrofitHelper
        )

    private fun createUserService(
        config: IngressServiceConfig,
        serviceProvider: ServiceProvider,
        eTagProvider: ETagProvider
    ): UserService =
        CachedUserService(
            serviceProvider.createUserService(config.userUrl, true),
            config.userCache,
            config.profileFieldsCache,
            config.countryCache,
            config.headerService,
            eTagProvider
        )

    private fun createVerificationService(
        config: IngressServiceConfig,
        serviceProvider: ServiceProvider
    ): VerificationService =
        serviceProvider.createVerificationService(config.userUrl, true)

    /**
     * Starts a new login.
     * @param userCredentials contains the user's e-mail address or phone number and optionally a verification code
     * The response error is either a NetworkError, a LoginFailure or an HttpError.
     */
    fun login(userCredentials: UserCredentials): FutureTask<Unit, ResponseError<out RequestError>?> {
        checkServiceInitialized()
        return serviceProxy.login(userCredentials)
    }

    /**
     * Starts a new Logout.
     */
    fun logout(): FutureTask<Unit, ResponseError<out RequestError>?> {
        checkServiceInitialized()
        return serviceProxy.startLogout()
    }

    /**
     * Refreshes the auth token if it is expired, and returns the auth token as a result of the returned [FutureTask].
     * The task will fail if the user is logged out.
     */
    fun refreshTokenIfRequired(): FutureTask<Token, Throwable?> {
        checkServiceInitialized()
        return serviceProxy.refreshToken()
    }

    /**
     * Get the [TokenExchangeService]
     */
    fun tokenExchangeService(): TokenExchangeService {
        checkServiceInitialized()
        return serviceProxy
    }

    /**
     * Get the [AuthenticationService] to access auth token related functions
     */
    fun authenticationService(): AuthenticationService = checkServiceInitializedAndGetProxy()

    /**
     * Get the [VerificationService]
     */
    fun verificationService(): VerificationService = checkServiceInitializedAndGetProxy()

    /**
     * Get the [UserService] to access all user related functionality
     */
    fun userService(): UserService = checkServiceInitializedAndGetProxy()

    private fun checkServiceInitializedAndGetProxy(): ServiceProxy {
        checkServiceInitialized()
        return serviceProxy
    }

    internal fun loginStateService(): LoginStateService = serviceProxy

    fun cachedUser(): User? {
        checkServiceInitialized()
        return serviceProxy.userCache.loadUser()
    }

    /**
     * Returns the cached image/photo (as ByteArray) of the user
     */
    fun cachedUserImageBytes(): ByteArray? {
        checkServiceInitialized()
        return serviceProxy.userCache.loadUserImage()
    }

    /**
     * Load the locally cached fields for user's profile
     */
    fun cachedProfileFields(countryCode: String, locale: String): ProfileFieldsData? {
        checkServiceInitialized()
        return serviceProxy.profileFieldsCache.loadProfileFields(countryCode, locale)
    }

    /**
     * Deletes all databases and file storages.
     */
    fun clearLocalCache() {
        checkServiceInitialized()
        serviceProxy.userCache.clear()
        serviceProxy.profileFieldsCache.clear()
        serviceProxy.eTagProvider.clearAll()
    }

    fun createMessageProcessor(
        nextProcessor: MessageProcessor? = null
    ): MessageProcessor =
        UserMessageProcessor(
            MessageParsers.createUserMessageParser(),
            nextProcessor
        )

    private fun checkServiceInitialized() {
        if (::serviceProxy.isInitialized.not()) throw CiamServiceNotInitializedException()
    }

    class CiamServiceNotInitializedException :
        IllegalStateException("MBIngressKit was not initialized!")
}
