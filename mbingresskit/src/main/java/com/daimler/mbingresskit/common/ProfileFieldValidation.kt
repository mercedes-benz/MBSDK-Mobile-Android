package com.daimler.mbingresskit.common

data class ProfileFieldValidation(
    val minLength: Int?,
    val maxLength: Int?,
    val regularExpression: String?
)
