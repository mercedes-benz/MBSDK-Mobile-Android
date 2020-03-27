package com.daimler.mbnetworkkit.certificatepinning

import okhttp3.Interceptor
import okhttp3.Response
import javax.net.ssl.SSLPeerUnverifiedException

class CertificatePinningInterceptor(private val processor: CertificatePinningErrorProcessor) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        } catch (e: SSLPeerUnverifiedException) {
            processor.onCertificatePinningError(e)
            throw e
        }
    }
}
