package com.daimler.mbmobilesdk.biometric.auth

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.daimler.mbmobilesdk.biometric.biometricsPreconditionsError
import com.daimler.mbmobilesdk.biometric.iv.IvProvider
import com.daimler.mbmobilesdk.utils.postToMainThread
import com.daimler.mbcommonkit.security.Crypto
import com.daimler.mbcommonkit.security.Crypto.CryptoException
import com.daimler.mbloggerkit.MBLoggerKit
import java.io.IOException
import java.nio.charset.Charset
import java.security.*
import java.security.cert.CertificateException
import java.util.concurrent.Executors
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

@RequiresApi(Build.VERSION_CODES.M)
internal class BiometricPromptAuthenticator(
    private val ivProvider: IvProvider,
    private val keyAlias: String
) : BiometricAuthenticator {

    private val keyStore: KeyStore by lazy { getAndroidKeyStore() }

    override fun startBiometricAuthentication(
        activity: FragmentActivity,
        dialogConfig: BiometricDialogConfig,
        cryptoCallback: BiometricCryptoCallback
    ): Boolean =
        startBiometricCryptoProcess(activity, dialogConfig, cryptoCallback,
            "", "", BiometricPurpose.AUTHENTICATE)

    override fun startBiometricEncryption(
        activity: FragmentActivity,
        dialogConfig: BiometricDialogConfig,
        cryptoCallback: BiometricCryptoCallback,
        tag: String,
        plainText: String
    ): Boolean =
        startBiometricCryptoProcess(activity, dialogConfig, cryptoCallback,
            tag, plainText, BiometricPurpose.ENCRYPT)

    override fun startBiometricDecryption(
        activity: FragmentActivity,
        dialogConfig: BiometricDialogConfig,
        cryptoCallback: BiometricCryptoCallback,
        tag: String,
        encryptedText: String
    ): Boolean =
        startBiometricCryptoProcess(activity, dialogConfig, cryptoCallback,
            tag, encryptedText, BiometricPurpose.DECRYPT)

    private fun startBiometricCryptoProcess(
        activity: FragmentActivity,
        config: BiometricDialogConfig,
        cryptoCallback: BiometricCryptoCallback,
        tag: String,
        text: String,
        purpose: BiometricPurpose
    ): Boolean {
        return try {
            if (biometricsPreconditionsError(activity) != null) return false
            removeFragmentIfAdded(activity)
            val callback = BiometricCallbackInternal(purpose, text, cryptoCallback)
            val cipher = initBiometricCipher(purpose, keyAlias, tag)
            val cryptoObject = BiometricPrompt.CryptoObject(cipher)
            val prompt = BiometricPrompt(activity, Executors.newSingleThreadExecutor(), callback)
            val info = BiometricPrompt.PromptInfo.Builder()
                .setTitle(config.title)
                .setSubtitle(config.subtitle)
                .setDescription(config.description)
                .setNegativeButtonText(config.negativeButtonText)
                .build()
            prompt.authenticate(info, cryptoObject)
            true
        } catch (e: SecurityException) {
            // when permission was not granted
            MBLoggerKit.e("Could not start fingerprint authentication.", throwable = e)
            false
        } catch (e: CryptoException) {
            // When any keystore operation failed; e.g. when the key was permanently invalidated
            // due to biometric changes on the device (new fingerprint registered/ deleted)
            MBLoggerKit.e("Could not start fingerprint authentication.", throwable = e)
            // We just delete the key here if it exists, since it won't be usable anymore.
            val deleted = deleteBiometricKey()
            MBLoggerKit.e("Key invalidated. Deleted = $deleted.")
            false
        }
    }

    override fun deleteBiometricKey(): Boolean =
        if (keyStore.containsAlias(keyAlias)) {
            keyStore.deleteEntry(keyAlias)
            ivProvider.deleteIvForAlias(keyAlias)
            true
        } else {
            false
        }

    /* This is just a workaround for now, since the helper fragment seems to be not cleared up. */
    private fun removeFragmentIfAdded(activity: FragmentActivity) {
        activity.supportFragmentManager.apply {
            findFragmentByTag("FingerprintHelperFragment")?.let {
                beginTransaction()
                    .remove(it)
                    .commitNowAllowingStateLoss()
            }
        }
    }

    private fun encode(cipher: Cipher, text: String) =
        Base64.encodeToString(cipher.doFinal(text.toByteArray(Charset.defaultCharset())),
            Base64.DEFAULT)

    private fun decode(cipher: Cipher, encryptedText: String) =
        String(cipher.doFinal(Base64.decode(encryptedText.toByteArray(Charset.defaultCharset()),
            Base64.DEFAULT)), Charset.defaultCharset())

    private fun loadFingerprintKey(alias: String): Key =
        if (keyStore.containsAlias(alias)) {
            keyStore.getKey(alias, null)
        } else {
            // safety delete remaining ivs
            ivProvider.deleteIvForAlias(alias)
            generateFingerprintKey(alias)
        }

    private fun generateFingerprintKey(alias: String): SecretKey {
        val parameterSpec = KeyGenParameterSpec.Builder(alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(BLOCK_MODE)
            .setUserAuthenticationRequired(true)
            .setEncryptionPaddings(ENCRYPTION_PADDING)
            .build()
        try {
            val keyGenerator = KeyGenerator.getInstance(ALGORITHM,
                ANDROID_KEYSTORE_TYPE)
            keyGenerator.init(parameterSpec)
            return keyGenerator.generateKey()
        } catch (e: NoSuchAlgorithmException) {
            throw CryptoException(e.localizedMessage, e)
        } catch (e: NoSuchProviderException) {
            throw CryptoException(e.localizedMessage, e)
        } catch (e: InvalidAlgorithmParameterException) {
            throw CryptoException(e.localizedMessage, e)
        }
    }

    private fun initBiometricCipher(purpose: BiometricPurpose, alias: String, tag: String): Cipher {
        val cipher: Cipher
        try {
            cipher = Cipher.getInstance(CIPHER_MODE)
            val key = loadFingerprintKey(alias)
            when (purpose) {
                BiometricPurpose.ENCRYPT -> {
                    cipher.init(Cipher.ENCRYPT_MODE, key)
                    ivProvider.saveIvForAlias(alias, tag, cipher.iv)
                }
                BiometricPurpose.DECRYPT -> {
                    val currentIv = ivProvider.getIvForAlias(alias, tag)
                        ?: throw IllegalStateException("No encryption was done using this alias yet.")
                    cipher.init(Cipher.DECRYPT_MODE, loadFingerprintKey(alias), IvParameterSpec(currentIv))
                }
                BiometricPurpose.AUTHENTICATE -> {
                    cipher.init(Cipher.ENCRYPT_MODE, key)
                }
            }
        } catch (e: NoSuchAlgorithmException) {
            throw CryptoException(e.localizedMessage, e)
        } catch (e: NoSuchPaddingException) {
            throw CryptoException(e.localizedMessage, e)
        } catch (e: UnrecoverableKeyException) {
            throw CryptoException(e.localizedMessage, e)
        } catch (e: KeyStoreException) {
            throw CryptoException(e.localizedMessage, e)
        } catch (e: InvalidKeyException) {
            throw CryptoException(e.localizedMessage, e)
        }

        return cipher
    }

    private fun getAndroidKeyStore(): KeyStore {
        try {
            val androidKeyStore = KeyStore.getInstance(Crypto.ANDROID_KEY_STORE)
            androidKeyStore.load(null)
            return androidKeyStore
        } catch (ioe: IOException) {
            throw CryptoException("Failed to load AndroidKeyStore", ioe)
        } catch (nsa: NoSuchAlgorithmException) {
            throw CryptoException("Failed to load AndroidKeyStore", nsa)
        } catch (ce: CertificateException) {
            throw CryptoException("Failed to load AndroidKeyStore", ce)
        } catch (ke: KeyStoreException) {
            throw CryptoException("Failed to load AndroidKeyStore", ke)
        }
    }

    private inner class BiometricCallbackInternal(
        private val purpose: BiometricPurpose,
        private val text: String,
        private val callback: BiometricCryptoCallback
    ) : BiometricPrompt.AuthenticationCallback() {

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            postToMainThread { callback.onAuthenticationError(errString.toString()) }
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            postToMainThread { callback.onAuthenticationFailed() }
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            postToMainThread { callback.onAuthenticationSucceeded() }
            result.cryptoObject?.cipher?.let {
                when (purpose) {
                    BiometricPurpose.ENCRYPT -> {
                        val encrypted = encode(it, text)
                        postToMainThread { callback.onEncryptionSucceeded(encrypted) }
                    }
                    BiometricPurpose.DECRYPT -> {
                        val decrypted = decode(it, text)
                        postToMainThread { callback.onDecryptionSucceeded(decrypted) }
                    }
                    BiometricPurpose.AUTHENTICATE -> Unit
                }
            } // TODO determine if it can happen that the Cipher object is null
        }
    }

    private companion object {
        private const val ANDROID_KEYSTORE_TYPE = "AndroidKeyStore"

        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7

        private const val CIPHER_MODE = "$ALGORITHM/$BLOCK_MODE/$ENCRYPTION_PADDING"
    }
}