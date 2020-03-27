package com.daimler.mbcommonkit.preferences.chunks

import com.daimler.mbcommonkit.preferences.chunks.EncryptedSharedPreferencesWithChunks.Companion.CRYPTO_HEADER_LENGTH_BYTES
import com.daimler.mbcommonkit.security.RandomStringGenerator

internal fun tooLongString() =
    RandomStringGenerator().generateString(EncryptedSharedPreferencesChunkWriter.MAX_STRING_LENGTH + CRYPTO_HEADER_LENGTH_BYTES + 1)
