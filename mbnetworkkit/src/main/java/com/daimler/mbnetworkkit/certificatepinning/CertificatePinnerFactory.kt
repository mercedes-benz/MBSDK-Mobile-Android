package com.daimler.mbnetworkkit.certificatepinning

import okhttp3.CertificatePinner

class CertificatePinnerFactory : CertificatePinnerProvider {

    override fun createCertificatePinner(configurations: List<CertificateConfiguration>): CertificatePinner {
        return CertificatePinner.Builder().apply {
            configurations.forEach { configuration ->
                configuration.publicKeyHashes.forEach { publicKeyHash ->
                    add(configuration.host, publicKeyHash)
                }
            }
        }
            .build()
    }
}
