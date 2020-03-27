package com.daimler.mbingresskit.common

data class VerificationTransaction(
    val type: VerificationType,
    val subject: String
)
