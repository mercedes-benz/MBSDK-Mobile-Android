package com.daimler.mbmobilesdk.preferences.user

import com.daimler.mbcommonkit.preferences.Preference

/**
 * Contains preferences that are related to all users shared over MBMobileSDK Apps.
 */
internal interface UserSettings {

    /**
     * True if the user wants to confirm his/ her pin through biometrics.
     */
    val pinBiometricsEnabled: Preference<Boolean>

    /**
     * True if the user was already asked to register a biometric id.
     */
    val promptedForBiometrics: Preference<Boolean>

    /**
     * Clears all user settings.
     */
    fun reset()
}