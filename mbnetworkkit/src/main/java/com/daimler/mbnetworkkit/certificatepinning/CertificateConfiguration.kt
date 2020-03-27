package com.daimler.mbnetworkkit.certificatepinning

data class CertificateConfiguration(
    val host: String,
    val publicKeyHashes: List<String>
)
