package com.daimler.mbcommonkit.preferences

import android.content.Context
import android.os.Build
import android.util.Base64
import com.daimler.mbcommonkit.preferences.chunks.EncryptedPreferencesChunkHelper
import com.daimler.mbcommonkit.preferences.chunks.EncryptedSharedPreferencesWithChunks
import com.daimler.mbcommonkit.security.Crypto
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.slot

internal fun mockContext(sharedPreferencesFake: SharedPreferencesFake): Context = mockk {
    every { getSharedPreferences(any(), any()) } returns sharedPreferencesFake
}

/**
 * Fake encryption by adding a fake AES GCM header
 */
internal fun encrypt(unencrypted: String) = AES_GCM_HEADER + unencrypted

internal fun mockCrypto(): Crypto = mockk {
    val decryptedTextSlot = slot<String>()
    every { encrypt(any(), capture(decryptedTextSlot)) } answers {
        encrypt(decryptedTextSlot.captured)
    }
    val encryptedTextSlot = slot<String>()
    every { decrypt(any(), capture(encryptedTextSlot)) } answers {
        encryptedTextSlot.captured.removePrefix(AES_GCM_HEADER)
    }

    every { keyExists(any()) } returns true
}

internal fun mockBase64() {
    mockkStatic(Base64::class)
    val base64DecodeSlot = slot<String>()
    every {
        Base64.decode(capture(base64DecodeSlot), any())
    } answers {
        base64DecodeSlot.captured.toByteArray()
    }

    val base64EncodeSlot = slot<ByteArray>()
    every {
        Base64.encodeToString(capture(base64EncodeSlot), any())
    } answers {
        String(base64EncodeSlot.captured)
    }
}

internal fun getEncryptedSharedPreferencesWithMockContext(
    sharedPreferencesFake: SharedPreferencesFake
): EncryptedSharedPreferencesWithChunks {
    mockBase64()
    return EncryptedSharedPreferencesWithChunks(
        mockContext(sharedPreferencesFake),
        TEST_ALIAS,
        "test",
        crypto = mockCrypto()
    )
}

internal fun mockCurrentDeviceHuaweiWithAndroid6() {
    mockkObject(EncryptedPreferencesChunkHelper)
    every { EncryptedPreferencesChunkHelper.getAndroidRelease() } returns Build.VERSION_CODES.M
    every { EncryptedPreferencesChunkHelper.getDeviceBrand() } returns HUAWEI
}

internal fun mockCurrentDeviceAndroidOreoMinor() {
    mockkObject(EncryptedPreferencesChunkHelper)
    every { EncryptedPreferencesChunkHelper.getAndroidRelease() } returns Build.VERSION_CODES.O_MR1
    every { EncryptedPreferencesChunkHelper.getDeviceBrand() } returns "any"
}

internal fun mockStandardDeviceBrand() {
    mockkObject(EncryptedPreferencesChunkHelper)
    every { EncryptedPreferencesChunkHelper.getDeviceBrand() } returns DEVICE_BRAND
}

// AES GCM header is 36 bytes long
internal const val AES_GCM_HEADER = "abcabcabcabcabcabcabcabcabcabcabcabc"

private const val DEVICE_BRAND = "deviceBrand"
private const val HUAWEI = "HUAWEI"
private const val TEST_ALIAS = "testAlias"
internal const val TEST_KEY = "testKey"
