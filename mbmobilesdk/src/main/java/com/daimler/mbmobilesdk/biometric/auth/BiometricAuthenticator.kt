package com.daimler.mbmobilesdk.biometric.auth

import androidx.fragment.app.FragmentActivity

internal interface BiometricAuthenticator {

    fun startBiometricAuthentication(
        activity: FragmentActivity,
        dialogConfig: BiometricDialogConfig,
        cryptoCallback: BiometricCryptoCallback
    ): Boolean

    fun startBiometricEncryption(
        activity: FragmentActivity,
        dialogConfig: BiometricDialogConfig,
        cryptoCallback: BiometricCryptoCallback,
        tag: String,
        plainText: String
    ): Boolean

    fun startBiometricDecryption(
        activity: FragmentActivity,
        dialogConfig: BiometricDialogConfig,
        cryptoCallback: BiometricCryptoCallback,
        tag: String,
        encryptedText: String
    ): Boolean

    fun deleteBiometricKey(): Boolean
}