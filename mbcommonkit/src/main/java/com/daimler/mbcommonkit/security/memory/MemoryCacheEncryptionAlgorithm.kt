package com.daimler.mbcommonkit.security.memory

import com.daimler.mbcommonkit.security.EncryptionAlgorithm
import com.daimler.mbcommonkit.security.memory.MemoryCacheEncryptionAlgorithm.Companion.MAX_ENTRIES

/**
 * Abstract [EncryptionAlgorithm] that has the possibility to keep plain texts and their
 * encrypted value in memory.
 * NOTE: This will only work reliably if sub-classes always return the same result for the same
 * parameter of [doEncryption].
 *
 * @param cacheEncryptedValues true to keep plain texts and encrypted values in memory
 * @param hashCreator an object that generates hash codes from strings
 * @param maxEntries the maximum capacity of texts that should be kept in memory, default is [MAX_ENTRIES]
 */
internal abstract class MemoryCacheEncryptionAlgorithm(
    private val cacheEncryptedValues: Boolean,
    hashCreator: EntryHashCreator = HashCodeHashCreator(),
    maxEntries: Int = MAX_ENTRIES
) : EncryptionAlgorithm {

    private val entries = EncryptionEntries(hashCreator, maxEntries)

    internal val encryptionEntries: EncryptionEntries
        get() = entries

    /**
     * Executes the encryption process.
     *
     * @param alias the alias of the KeyStore entry
     * @param plainText the text to encrypt
     */
    protected abstract fun doEncryption(alias: String, plainText: String): String

    /**
     * Executes the decryption process.
     *
     * @param alias the alias of the KeyStore entry
     * @param encryptedText the encrypted text to decrypt
     */
    protected abstract fun doDecryption(alias: String, encryptedText: String): String

    final override fun encrypt(alias: String, plainText: String): String {
        val entry = entries.getEncryptedEntry(plainText)
        return entry ?: run {
            val encrypted = doEncryption(alias, plainText)
            if (cacheEncryptedValues) entries.putEntry(plainText, encrypted)
            encrypted
        }
    }

    final override fun decrypt(alias: String, encryptedText: String): String {
        val entry = entries.getDecryptedEntry(encryptedText)
        return entry ?: run {
            val decrypted = doDecryption(alias, encryptedText)
            if (cacheEncryptedValues) entries.putEntry(decrypted, encryptedText)
            decrypted
        }
    }

    private companion object {
        private const val MAX_ENTRIES = 50
    }
}
