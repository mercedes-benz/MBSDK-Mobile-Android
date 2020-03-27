package com.daimler.mbcommonkit.preferences.chunks

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbcommonkit.preferences.EncryptedSharedPreferences
import com.daimler.mbcommonkit.preferences.chunks.EncryptedPreferencesChunkHelper.chunkKey
import com.daimler.mbcommonkit.preferences.chunks.EncryptedPreferencesChunkHelper.isCrashingAndroidSixDevice
import com.daimler.mbcommonkit.preferences.chunks.EncryptedSharedPreferencesChunkWriter.Companion.MAX_STRING_LENGTH
import com.daimler.mbcommonkit.security.Crypto
import com.daimler.mbcommonkit.security.EncryptionAlgorithm
import com.daimler.mbloggerkit.MBLoggerKit

/**
 * Most Huawei devices with Android 6 crash when they have to decrypt more than 4096 bytes.
 *
 * Use this class instead of the normal [EncryptedSharedPreferences] to work around this issue,
 * by writing and reading strings in chunks.
 */
internal class EncryptedSharedPreferencesWithChunks(
    context: Context,
    alias: String,
    settingsName: String,
    mode: Int = Context.MODE_PRIVATE,
    crypto: Crypto
) : EncryptedSharedPreferences(context, alias, settingsName, mode, crypto) {

    constructor(
        context: Context,
        alias: String,
        settingsName: String,
        mode: Int = Context.MODE_PRIVATE,
        keepEncryptedValuesInMemory: Boolean = false
    ) : this(context, alias, settingsName, mode, Crypto(keepEncryptedValuesInMemory))

    override fun getString(key: String, defValue: String?): String {
        return if (shouldUseChunksForKey(key)) {
            readChunksUntilNoMoreChunkKeys(key)
        } else {
            getOrDeleteStringIfDecryptionFailed(key, defValue ?: "")
        }
    }

    override fun contains(key: String) =
        super.contains(key) || super.contains(chunkKey(key, 0))

    override fun edit(): SharedPreferences.Editor =
        EncryptedEditorWithChunks(this, super.edit())

    internal fun shouldUseChunksForKey(key: String) = super.contains(chunkKey(key, 0))

    /**
     * Ensure strings that already caused a crash on decryption are deleted,
     * so they can be written in chunks instead.
     */
    private fun getOrDeleteStringIfDecryptionFailed(
        key: String,
        defaultValue: String
    ): String {
        val encryptedString = getEncryptedString(key, defaultValue)
        return if (encryptedString == defaultValue) {
            encryptedString
        } else {
            decryptOrDeleteEncryptedString(key, encryptedString) ?: defaultValue
        }
    }

    private fun decryptOrDeleteEncryptedString(key: String, encryptedString: String): String? {
        return if (getDecryptionStatus(key) == DecryptionStatus.FAIL ||
            isCrashingDeviceAndTooLongString(encryptedString)
        ) {
            MBLoggerKit.w("Deleting entry for key $key since decryption has probably failed before!")
            super.edit().remove(key).apply()
            null
        } else {
            decryptSafely(key, encryptedString)
        }
    }

    private fun isCrashingDeviceAndTooLongString(encryptedString: String) =
        isCrashingAndroidSixDevice() && encryptedString.length > (MAX_STRING_LENGTH + CRYPTO_HEADER_LENGTH_BYTES)

    private fun decryptSafely(key: String, encryptedString: String): String? {
        putDecryptionStatus(key, DecryptionStatus.FAIL)
        return try {
            // the flag is an additional safety measure to detect a failed decryption when the device crashes and we can't catch an exception
            super.decrypted(encryptedString).also {
                putDecryptionStatus(key, DecryptionStatus.SUCCESS)
            }
        } catch (e: EncryptionAlgorithm.AlgorithmException) {
            super.edit().remove(key).apply()
            super.edit().remove(decryptionStatusKey(key)).apply()
            null
        }
    }

    private fun getDecryptionStatus(key: String): DecryptionStatus {
        val decryptionStatus =
            super.getString(decryptionStatusKey(key), DecryptionStatus.UNKNOWN.name)
        return DecryptionStatus.valueOf(decryptionStatus)
    }

    private fun putDecryptionStatus(key: String, status: DecryptionStatus) =
        super.edit().putString(decryptionStatusKey(key), status.name).apply()

    private fun decryptionStatusKey(key: String) = "$key-$DECRYPTION_STATUS"

    private fun readChunksUntilNoMoreChunkKeys(key: String): String {
        MBLoggerKit.d("Reading chunks with key $key")
        var chunks = ""
        chunkKeyIterator(key).forEach { chunkKey ->
            chunks += super.getString(chunkKey, "")
        }
        return chunks
    }

    companion object {
        // AES GCM encryption adds 36 bytes
        internal const val CRYPTO_HEADER_LENGTH_BYTES = 36
        internal const val DECRYPTION_STATUS = "decryption-status"
    }
}
