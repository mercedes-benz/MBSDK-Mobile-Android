package com.daimler.mbnetworkkit.certificatepinning

interface CertificatePinningErrorProcessor {
    fun onCertificatePinningError(error: Throwable)
}
