package com.daimler.mbingresskit.common

data class VerificationConfirmation(
    val type: VerificationType,
    val subject: String,
    val code: String
)
