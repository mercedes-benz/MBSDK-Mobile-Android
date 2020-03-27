package com.daimler.mbingresskit.common

data class RegistrationUser(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val password: String,
    val userName: String,
    val countryCode: String,
    val communicationPreference: CommunicationPreference
)
