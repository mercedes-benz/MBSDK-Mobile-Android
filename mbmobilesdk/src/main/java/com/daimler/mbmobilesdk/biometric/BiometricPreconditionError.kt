package com.daimler.mbmobilesdk.biometric

internal enum class BiometricPreconditionError {

    API_LEVEL_INSUFFICIENT,
    NO_PERMISSION,
    NO_FINGERPRINT_SENSOR,
    LOCK_SCREEN_NOT_SECURED,
    NO_FINGERPRINT_CONFIGURED
}