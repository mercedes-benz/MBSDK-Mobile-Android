package com.daimler.mbcommonkit.security.memory

/**
 * Wraps combinations of plain texts and their respective encrypted variant.
 */
internal class EncryptionEntries(
    private val hashCreator: EntryHashCreator,
    private val maxEntries: Int
) {

    /** Holds the hash of a plain text as key and the encrypted text as value. */
    private val encryptedEntries = mutableMapOf<String, String>()

    /** Holds the hash of an encrypted text as key and the plain text as value. */
    private val decryptedEntries = mutableMapOf<String, String>()

    val size: Int
        get() = (encryptedEntries.size + decryptedEntries.size) / 2

    /**
     * Returns the encrypted variant of the given [plainText] or null if there is no entry.
     *
     * @param plainText the plain text
     */
    fun getEncryptedEntry(plainText: String): String? {
        val hash = hashCreator.generateHash(plainText)
        return encryptedEntries[hash]
    }

    /**
     * Returns the decrypted variant of the given [encryptedText] or null if there is no entry.
     *
     * @param encryptedText the encrypted text
     */
    fun getDecryptedEntry(encryptedText: String): String? {
        val hash = hashCreator.generateHash(encryptedText)
        return decryptedEntries[hash]
    }

    /**
     * Adds the given combination of plain- and encrypted text if there are less than [maxEntries]
     * added.
     * Old entries that have the same hash provided by [EntryHashCreator.generateHash] will be replaced.
     */
    fun putEntry(plain: String, encrypted: String) {
        if (size > maxEntries) return
        encryptedEntries[hashCreator.generateHash(plain)] = encrypted
        decryptedEntries[hashCreator.generateHash(encrypted)] = plain
    }
}
