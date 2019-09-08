package com.daimler.mbmobilesdk.biometric

import com.daimler.mbmobilesdk.biometric.auth.BiometricCryptoCallback

internal open class SimpleBiometricCallback : BiometricCryptoCallback {

    override fun onEncryptionSucceeded(encryptedText: String) = Unit

    override fun onDecryptionSucceeded(decryptedText: String) = Unit

    override fun onAuthenticationSucceeded() = Unit

    override fun onAuthenticationFailed() = Unit

    override fun onAuthenticationError(errString: String) = Unit

    companion object {

        fun succeed(action: () -> Unit) = object : SimpleBiometricCallback() {
            override fun onAuthenticationSucceeded() {
                action()
            }
        }
    }
}