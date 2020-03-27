package com.daimler.mbcommonkit.security

import java.security.Key

interface EncryptionAlgorithm {
    fun generateKey(alias: String)
    fun getKey(alias: String): Key
    fun removeKey(alias: String)
    fun encrypt(alias: String, plainText: String): String
    fun decrypt(alias: String, encryptedText: String): String

    class AlgorithmException(message: String, cause: Throwable? = null) : Crypto.CryptoException(message, cause)
}
