package com.daimler.mbcommonkit.preferences.chunks

import android.os.Build
import java.util.Locale

/**
 * Devices of certain vendors crash when decrypting larger values (e.g. Huawei > 4096 bytes).
 *
 * Use this helper to identify these devices.
 */
internal object EncryptedPreferencesChunkHelper {

    /**
     * Gives each key an index, so when we know that a string in shared preferences
     * was saved in chunks.
     */
    internal fun chunkKey(key: String, index: Int) = "$key-chunk-$index"

    /**
     * Use chunks for devices from Android 6 to Android 8.1
     */
    internal fun shouldUseChunksForCurrentDevice(): Boolean {
        val currentRelease = getAndroidRelease()
        return currentRelease <= Build.VERSION_CODES.O_MR1
    }

    internal fun getDeviceBrand() = Build.BRAND

    internal fun getAndroidRelease() = Build.VERSION.SDK_INT

    internal fun isCrashingAndroidSixDevice() = isAndroidSix() && this.isCrashingDevice()

    private fun isAndroidSix() = getAndroidRelease() == Build.VERSION_CODES.M

    private fun isCrashingDevice() =
        getDeviceBrand().toLowerCase(Locale.ROOT) in CrashingDeviceBrand.lowerCaseValues()
}

/**
 * Certain Android vendors which are known to crash on decryption of larger values.
 * We always delete too large Strings and write them as chunks on these devices.
 */
internal enum class CrashingDeviceBrand {
    HUAWEI, HONOR, ASUS;

    companion object {
        fun lowerCaseValues() = values().map {
            it.name.toLowerCase(Locale.ROOT)
        }
    }
}
