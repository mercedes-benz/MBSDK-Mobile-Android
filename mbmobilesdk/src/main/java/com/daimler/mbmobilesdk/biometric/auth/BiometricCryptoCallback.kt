package com.daimler.mbmobilesdk.biometric.auth

/**
 * Callback for authentication processes through biometrics.
 */
internal interface BiometricCryptoCallback {

    /**
     * Called when the encryption finished in case of a successful authentication.
     * @see onAuthenticationSucceeded
     */
    fun onEncryptionSucceeded(encryptedText: String)

    /**
     * Called when the decryption finished in case of a successful authentication.
     * @see onAuthenticationSucceeded
     */
    fun onDecryptionSucceeded(decryptedText: String)

    /**
     * Called when the authentication succeeded. The dialog prompt will be dismissed.
     * This is called before [onEncryptionSucceeded] or [onDecryptionSucceeded].
     */
    fun onAuthenticationSucceeded()

    /**
     * Called when the biometrics are valid but not registered.
     */
    fun onAuthenticationFailed()

    /**
     * Called when an error occurred. The dialog prompt will be dismissed.
     */
    fun onAuthenticationError(errString: String)
}