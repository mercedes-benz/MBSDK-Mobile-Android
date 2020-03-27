package com.daimler.mbcommonkit.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.daimler.mbcommonkit.security.memory.MemoryCacheEncryptionAlgorithm
import java.nio.charset.Charset
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.Key
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.UnrecoverableKeyException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.GCMParameterSpec

internal class AesEncryption(
    private val keyStore: KeyStore,
    cacheEncryptedValues: Boolean
) : MemoryCacheEncryptionAlgorithm(cacheEncryptedValues) {

    companion object {
        private const val AES_MODE = "AES/GCM/NoPadding"

        private const val GCM_LENGTH = 128

        /**
         * Cipher requires an Initialization Vector for decryption and encryption.
         */
        private val FIX_IV = byteArrayOf(0, 0, 0, 77, 121, 84, 104, 101, 114, 101, 115, 97)
    }

    //region EncryptionAlgorithm
    override fun generateKey(alias: String) {
        if (keyStore.containsAlias(alias).not()) {
            val spec = KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT.or(KeyProperties.PURPOSE_DECRYPT))
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setRandomizedEncryptionRequired(false)
                .build()
            try {
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, keyStore.type).apply {
                    init(spec)
                    generateKey()
                }
            } catch (nsa: NoSuchAlgorithmException) {
                throw EncryptionAlgorithm.AlgorithmException("Failed to generate Key $alias.", nsa)
            } catch (nsp: NoSuchProviderException) {
                throw EncryptionAlgorithm.AlgorithmException("Failed to generate Key $alias.", nsp)
            } catch (iape: InvalidAlgorithmParameterException) {
                throw EncryptionAlgorithm.AlgorithmException("Failed to generate Key $alias.", iape)
            }
        }
    }

    override fun getKey(alias: String): Key {
        return try {
            keyStore.getKey(alias, null)
        } catch (ke: KeyStoreException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to load Key $alias.", ke)
        } catch (nsa: NoSuchAlgorithmException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to load Key $alias.", nsa)
        } catch (uke: UnrecoverableKeyException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to load Key $alias.", uke)
        }
    }

    override fun removeKey(alias: String) {
        try {
            if (keyStore.containsAlias(alias)) {
                keyStore.deleteEntry(alias)
            }
        } catch (ke: KeyStoreException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to remove Key $alias.", ke)
        }
    }

    override fun doEncryption(alias: String, plainText: String): String {
        try {
            val cipher = Cipher.getInstance(AES_MODE).apply {
                init(Cipher.ENCRYPT_MODE, getKey(alias), GCMParameterSpec(GCM_LENGTH, FIX_IV))
            }
            return Base64.encodeToString(cipher.doFinal(plainText.toByteArray(Charset.defaultCharset())), Base64.NO_WRAP)
        } catch (nsa: NoSuchAlgorithmException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to encrypt with alias $alias. Text: $plainText", nsa)
        } catch (nsp: NoSuchPaddingException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to encrypt with alias $alias. Text: $plainText", nsp)
        } catch (iape: InvalidAlgorithmParameterException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to encrypt with alias $alias. Text: $plainText", iape)
        } catch (ike: InvalidKeyException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to encrypt with alias $alias. Text: $plainText", ike)
        } catch (bpe: BadPaddingException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to encrypt with alias $alias. Text: $plainText", bpe)
        } catch (ibse: IllegalBlockSizeException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to encrypt with alias $alias. Text: $plainText", ibse)
        }
    }

    override fun doDecryption(alias: String, encryptedText: String): String {
        try {
            val cipher = Cipher.getInstance(AES_MODE).apply {
                init(Cipher.DECRYPT_MODE, getKey(alias), GCMParameterSpec(GCM_LENGTH, FIX_IV))
            }
            return String(cipher.doFinal(Base64.decode(encryptedText.toByteArray(Charset.defaultCharset()), Base64.NO_WRAP)), Charset.defaultCharset())
        } catch (nsa: NoSuchAlgorithmException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to decrypt with alias $alias. Text: $encryptedText", nsa)
        } catch (nsp: NoSuchPaddingException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to decrypt with alias $alias. Text: $encryptedText", nsp)
        } catch (iape: InvalidAlgorithmParameterException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to decrypt with alias $alias. Text: $encryptedText", iape)
        } catch (ike: InvalidKeyException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to decrypt with alias $alias. Text: $encryptedText", ike)
        } catch (bpe: BadPaddingException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to decrypt with alias $alias. Text: $encryptedText", bpe)
        } catch (ibse: IllegalBlockSizeException) {
            throw EncryptionAlgorithm.AlgorithmException("Failed to decrypt with alias $alias. Text: $encryptedText", ibse)
        }
    }
    //endregion
}
