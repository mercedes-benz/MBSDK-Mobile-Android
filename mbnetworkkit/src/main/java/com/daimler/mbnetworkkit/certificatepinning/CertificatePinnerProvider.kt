package com.daimler.mbnetworkkit.certificatepinning

import okhttp3.CertificatePinner

interface CertificatePinnerProvider {

    fun createCertificatePinner(configurations: List<CertificateConfiguration>): CertificatePinner
}
