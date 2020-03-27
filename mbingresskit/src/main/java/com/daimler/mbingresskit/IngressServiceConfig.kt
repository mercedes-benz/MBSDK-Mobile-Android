package com.daimler.mbingresskit

import android.content.Context
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.common.authentication.ROPCAuthenticationConfig
import com.daimler.mbingresskit.implementation.filestorage.JsonFileWriter
import com.daimler.mbingresskit.implementation.filestorage.ProfileFieldsFileStorage
import com.daimler.mbingresskit.login.SessionExpiredHandler
import com.daimler.mbingresskit.persistence.CountryCache
import com.daimler.mbingresskit.persistence.FileProfileFieldsCache
import com.daimler.mbingresskit.persistence.MBIngressRealmModule
import com.daimler.mbingresskit.persistence.ProfileFieldsCache
import com.daimler.mbingresskit.persistence.RealmCountryCache
import com.daimler.mbingresskit.persistence.RealmUserCache
import com.daimler.mbingresskit.persistence.RealmUtil
import com.daimler.mbingresskit.persistence.UserCache
import com.daimler.mbnetworkkit.certificatepinning.CertificateConfiguration
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinningErrorProcessor
import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.mbrealmkit.MBRealmKit
import com.daimler.mbrealmkit.RealmServiceConfig

class IngressServiceConfig private constructor(
    val context: Context,
    val authenticationConfigs: List<ROPCAuthenticationConfig>,
    val userUrl: String,
    val sharedUserId: String,
    val ingressStage: String,
    val sessionExpiredHandler: SessionExpiredHandler?,
    val keyStoreAlias: String,
    val deviceUuid: String,
    val userCache: UserCache,
    val profileFieldsCache: ProfileFieldsCache,
    val countryCache: CountryCache,
    val headerService: HeaderService,
    val pinningErrorProcessor: CertificatePinningErrorProcessor?,
    val pinningConfigurations: List<CertificateConfiguration>,
    val preferredAuthenticationType: AuthenticationType,
    val logHttpBody: Boolean
) {

    class Builder(
        /**
         * Android Context
         */
        private val context: Context,
        /**
         * Base URL for user service
         */
        private val userUrl: String,
        /**
         * identifier of IngressStage
         */
        private val ingressStage: String,
        /**
         * Key store alias for authentication module
         */
        private val keyStoreAlias: String,
        /**
         * [HeaderService] that is used to intercept network requests and add headers
         */
        private val headerService: HeaderService,
        /**
         * List of configurations for both [AuthenticationType.KEYCLOAK] and [AuthenticationType.CIAM]
         */
        private val authenticationConfigurations: List<ROPCAuthenticationConfig>
    ) {
        private var sharedUserId = ""
        private var deviceUuid: String = ""
        private var sessionExpiredHandler: SessionExpiredHandler? = null
        private var pinningConfigurations: List<CertificateConfiguration> = emptyList()
        private var pinningErrorProcessor: CertificatePinningErrorProcessor? = null
        private var preferredAuthenticationType: AuthenticationType = AuthenticationType.KEYCLOAK
        private var logHttpBody: Boolean = false

        /**
         * Define a device UUID
         */
        fun useDeviceId(deviceId: String) = apply {
            this.deviceUuid = deviceId
        }

        /**
         * Enables single sign-on
         */
        fun enableSso(sharedUserId: String) = apply {
            this.sharedUserId = sharedUserId
        }

        /**
         * Define a callback to be invoked when the session is expired.
         */
        fun useSessionExpiredHandler(sessionExpiredHandler: SessionExpiredHandler?) = apply {
            this.sessionExpiredHandler = sessionExpiredHandler
        }

        /**
         * Configures certificate pinning
         */
        fun useCertificatePinning(
            pinningConfigurations: List<CertificateConfiguration>,
            errorProcessor: CertificatePinningErrorProcessor? = null
        ) = apply {
            this.pinningConfigurations = pinningConfigurations
            this.pinningErrorProcessor = errorProcessor
        }

        /**
         * Sets the prefered auth type. One of [AuthenticationType.KEYCLOAK] or [AuthenticationType.CIAM]
         */
        fun preferredAuthMethod(authenticationType: AuthenticationType) = apply {
            this.preferredAuthenticationType = authenticationType
        }

        /**
         * Configures the HTTP interceptor to also log the HTTP bodies
         */
        fun logHttpBody() = apply {
            logHttpBody = true
        }

        fun build(): IngressServiceConfig {
            setupIngressRealm()
            val userCache: UserCache = RealmUserCache(
                MBRealmKit.realm(RealmUtil.ID_ENCR_INGRESS_REALM)
            )
            val profileFieldsCache: ProfileFieldsCache = FileProfileFieldsCache(
                ProfileFieldsFileStorage(context, JsonFileWriter())
            )
            val countryCache: CountryCache = RealmCountryCache(
                MBRealmKit.realm(RealmUtil.ID_ENCR_INGRESS_REALM)
            )
            return IngressServiceConfig(
                context,
                authenticationConfigurations,
                userUrl,
                sharedUserId,
                ingressStage,
                sessionExpiredHandler,
                keyStoreAlias,
                deviceUuid,
                userCache,
                profileFieldsCache,
                countryCache,
                headerService,
                pinningErrorProcessor,
                pinningConfigurations,
                preferredAuthenticationType,
                logHttpBody
            )
        }

        private fun setupIngressRealm() {
            MBRealmKit.createRealmInstance(
                RealmUtil.ID_ENCR_INGRESS_REALM,
                RealmServiceConfig.Builder(
                    context,
                    RealmUtil.REALM_ENCR_SCHEMA_VERSION,
                    MBIngressRealmModule()
                )
                    .useDbName(RealmUtil.ENCR_INGRESS_FILE_NAME)
                    .encrypt()
                    .build()
            )
        }
    }
}
