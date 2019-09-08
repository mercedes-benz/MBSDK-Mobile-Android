package com.daimler.mbmobilesdk.biometric.auth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity

@RequiresApi(Build.VERSION_CODES.M)
internal abstract class BaseMBMobileSDKBiometrics : BiometricAuthenticator {

    protected abstract val crypto: BiometricPromptAuthenticator

    override fun startBiometricAuthentication(
        activity: FragmentActivity,
        dialogConfig: BiometricDialogConfig,
        cryptoCallback: BiometricCryptoCallback
    ): Boolean =
        crypto.startBiometricAuthentication(activity, dialogConfig, cryptoCallback)

    override fun startBiometricEncryption(
        activity: FragmentActivity,
        dialogConfig: BiometricDialogConfig,
        cryptoCallback: BiometricCryptoCallback,
        tag: String,
        plainText: String
    ): Boolean =
        crypto.startBiometricEncryption(activity, dialogConfig, cryptoCallback, tag, plainText)

    override fun startBiometricDecryption(
        activity: FragmentActivity,
        dialogConfig: BiometricDialogConfig,
        cryptoCallback: BiometricCryptoCallback,
        tag: String,
        encryptedText: String
    ): Boolean =
        crypto.startBiometricDecryption(activity, dialogConfig, cryptoCallback, tag, encryptedText)

    override fun deleteBiometricKey(): Boolean = crypto.deleteBiometricKey()
}