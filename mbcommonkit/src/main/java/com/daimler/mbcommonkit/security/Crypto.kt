package com.daimler.mbcommonkit.security

import java.io.IOException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException

/**
 * Facade class used to generate key in local KeyStore depending on the OS and to encrypt/decrypt some date.
 * <p>
 *     <ul>
 *         <li>{@link AesEncryption} for devices with SDK >= marshmallow</li>
 *     </ul>
 * </p>
 *
 * @see AesEncryption
 */
class Crypto(keepResults: Boolean = false) {

    private val keyStore by lazy { getAndroidKeyStore() }

    private val encryptionAlgorithm: EncryptionAlgorithm = AesEncryption(keyStore, keepResults)

    /**
     * Simply delegates the generation to the defined algorithm.
     */
    fun generateKey(alias: String) = encryptionAlgorithm.generateKey(alias)

    /**
     * Check if the passed alias exists in AndroidKeyStore.
     *
     * @param alias
     *                      The alias that references a Key in AndroidKeyStore
     * @return
     *                      true if alias is not empty and androidKeyStore contains the alias
     *
     * @throws CryptoException
     *                      When loading of KeyStore failed
     */
    fun keyExists(alias: String): Boolean {
        return try {
            alias.isNotEmpty() && keyStore.containsAlias(alias)
        } catch (ke: KeyStoreException) {
            throw CryptoException("Failed to check if key $alias exists.", ke)
        }
    }

    /**
     * Removes the Key that is referenced by the passed alias by calling configured algorithm
     *
     * @param alias
     *                      The alias that references a Key in AndroidKeyStore
     *
     * @throws CryptoException
     *                      if the key cannot be removed from KeyStore
     */
    fun removeKey(alias: String) = encryptionAlgorithm.removeKey(alias)

    /**
     * Encrypts the passed text with defined IEncryptionAlgorithm.
     *
     * @param plainText
     *                          The text to encrypt
     * @param alias
     *                          The alias that references the Key in local AndroidKeyStore
     * @return
     *                          The encrypted text
     *
     * @throws CryptoException
     *                          If the passed alias does not exist in local AndroidKeyStore. This can
     *                          be checked first with {@link #keyExists(String)} or when any other problem
     *                          occured in {@link EncryptionAlgorithm#encrypt(String, String)}
     *
     * @throws IllegalArgumentException
     *                          If passed alias was empty
     */
    fun encrypt(alias: String, plainText: String): String {
        if (plainText.isEmpty()) return ""
        if (keyExists(alias).not()) throw CryptoException("Key ($alias) does not exists in KeyStore.")
        return encryptionAlgorithm.encrypt(alias, plainText)
    }

    /**
     * Decrypt the passed text with defined IEncryptionAlgorithm.
     *
     * @param encryptedText
     *                          The text to decrypt
     * @param alias
     *                          The alias that references the Key in local AndroidKeyStore
     * @return
     *                          The decrypted text
     *
     * @throws CryptoException
     *                          If the passed alias does not exist in local AndroidKeyStore. This can
     *                          be checked first with {@link #keyExists(String)} or when any other problem
     *                          occured in {@link EncryptionAlgorithm#decrypt(String, String)}
     *
     * @throws IllegalArgumentException
     *                          If passed alias was empty
     */
    fun decrypt(alias: String, encryptedText: String): String {
        if (encryptedText.isEmpty()) return ""
        if (keyExists(alias).not()) throw CryptoException("Key ($alias) does not exists in KeyStore.")
        return encryptionAlgorithm.decrypt(alias, encryptedText)
    }

    /**
     * Gets the default Android KeyStore
     */
    fun getAndroidKeyStore(): KeyStore {
        try {
            val androidKeyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
            androidKeyStore.load(null)
            return androidKeyStore
        } catch (ioe: IOException) {
            throw CryptoException(FAILED_TO_LOAD_KEY_STORE, ioe)
        } catch (nsa: NoSuchAlgorithmException) {
            throw CryptoException(FAILED_TO_LOAD_KEY_STORE, nsa)
        } catch (ce: CertificateException) {
            throw CryptoException(FAILED_TO_LOAD_KEY_STORE, ce)
        } catch (ke: KeyStoreException) {
            throw CryptoException(FAILED_TO_LOAD_KEY_STORE, ke)
        }
    }

    open class CryptoException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)

    private companion object {
        const val ANDROID_KEY_STORE = "AndroidKeyStore"
        const val FAILED_TO_LOAD_KEY_STORE = "Failed to load $ANDROID_KEY_STORE"
    }
}
