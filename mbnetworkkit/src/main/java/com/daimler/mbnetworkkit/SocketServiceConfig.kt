package com.daimler.mbnetworkkit

import com.daimler.mbnetworkkit.certificatepinning.CertificateConfiguration
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinnerFactory
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinningErrorProcessor
import com.daimler.mbnetworkkit.certificatepinning.CertificatePinningInterceptor
import com.daimler.mbnetworkkit.common.TokenProvider
import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.mbnetworkkit.networking.OkhttpSocketConnection
import com.daimler.mbnetworkkit.socket.SocketConnection
import com.daimler.mbnetworkkit.socket.message.MessageProcessor
import com.daimler.mbnetworkkit.socket.reconnect.FixPeriodicReconnection
import com.daimler.mbnetworkkit.socket.reconnect.NoReconnection
import com.daimler.mbnetworkkit.socket.reconnect.Reconnection
import java.util.UUID
import java.util.concurrent.TimeUnit

class SocketServiceConfig private constructor(
    val sessionId: String,
    internal val socketConnection: SocketConnection
) {

    class Builder(private val url: String, private val messageProcessor: MessageProcessor) {

        private var reconnection: Reconnection = NoReconnection()
        private var tokenProvider: TokenProvider? = null
        private var headerService: HeaderService? = null
        private var pinningConfigurations: List<CertificateConfiguration> = emptyList()
        private var pinningErrorProcessor: CertificatePinningErrorProcessor? = null

        private var sessionId: UUID? = null

        /**
         * This will configure the socket to automatically try to reconnect if connection failure occurs.
         * Reconnect will only be executed if cause of lost connection was a network error or an expired token
         * if the given token provider is not null.
         * If manual connection was initiated while reconnect was in progress, the reconnect will be reset.
         */
        fun tryPeriodicReconnect(periodInSeconds: Long, maxRetries: Int, tokenProvider: TokenProvider? = null): Builder {
            this.reconnection = FixPeriodicReconnection(TimeUnit.SECONDS.toMillis(periodInSeconds), maxRetries)
            this.tokenProvider = tokenProvider
            return this
        }

        fun useAppSessionId(appSessionId: UUID): Builder {
            this.sessionId = appSessionId
            return this
        }

        fun useHeaderService(headerService: HeaderService): Builder {
            this.headerService = headerService
            return this
        }

        fun useCertificatePinning(pinningConfigurations: List<CertificateConfiguration>, errorProcessor: CertificatePinningErrorProcessor? = null): Builder {
            this.pinningConfigurations = pinningConfigurations
            this.pinningErrorProcessor = errorProcessor
            return this
        }

        fun create(): SocketServiceConfig {
            val sessionId = sessionId?.toString() ?: UUID.randomUUID().toString()
            return SocketServiceConfig(sessionId, createSocketConnection())
        }

        private fun createSocketConnection(): SocketConnection {
            return OkhttpSocketConnection(
                reconnection,
                messageProcessor,
                url,
                tokenProvider,
                headerService?.createRisHeaderInterceptor(),
                pinningErrorProcessor?.let { CertificatePinningInterceptor(it) },
                CertificatePinnerFactory(),
                pinningConfigurations
            )
        }
    }
}
