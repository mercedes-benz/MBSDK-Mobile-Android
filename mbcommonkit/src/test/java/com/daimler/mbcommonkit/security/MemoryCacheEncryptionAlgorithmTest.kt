package com.daimler.mbcommonkit.security

import com.daimler.mbcommonkit.security.memory.HashCodeHashCreator
import com.daimler.mbcommonkit.security.memory.MemoryCacheEncryptionAlgorithm
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import java.security.Key

class MemoryCacheEncryptionAlgorithmTest {

    @Test
    fun testEncryption() {
        val plain1 = "plain1"
        val plain2 = "plain2"
        val plain3 = "plain3"
        val algorithm = TestAlgorithm(true)
        val encrypted1 = algorithm.encrypt("", plain1)
        val encrypted2 = algorithm.encrypt("", plain2)
        val encrypted3 = algorithm.encrypt("", plain3)
        assertEquals("xplain1x", encrypted1)
        assertEquals("xplain2x", encrypted2)
        assertEquals("xplain3x", encrypted3)
    }

    @Test
    fun testDecryption() {
        val plain1 = "plain1"
        val plain2 = "plain2"
        val plain3 = "plain3"
        val algorithm = TestAlgorithm(true)
        val encrypted1 = algorithm.encrypt("", plain1)
        val encrypted2 = algorithm.encrypt("", plain2)
        val encrypted3 = algorithm.encrypt("", plain3)
        val decrypted1 = algorithm.decrypt("", encrypted1)
        val decrypted2 = algorithm.decrypt("", encrypted2)
        val decrypted3 = algorithm.decrypt("", encrypted3)
        assertEquals(plain1, decrypted1)
        assertEquals(plain2, decrypted2)
        assertEquals(plain3, decrypted3)
        val decrypted11 = algorithm.decrypt("", encrypted1)
        assertEquals(decrypted1, decrypted11)
    }

    @Test
    fun testCachedValues() {
        val plain1 = "plain1"
        val plain2 = "plain2"
        val plain3 = "plain3"
        val algorithm = TestAlgorithm(true)
        algorithm.encrypt("", plain1)
        algorithm.encrypt("", plain2)
        algorithm.encrypt("", plain3)
        algorithm.encrypt("", plain3)
        algorithm.encrypt("", plain3)
        assertEquals(3, algorithm.encryptionEntries.size)
    }

    @Test
    fun testCachedContent() {
        val plain1 = "plain1"
        val plain2 = "plain2"
        val plain3 = "plain3"
        val algorithm = TestAlgorithm(true)
        val encrypted1 = algorithm.encrypt("", plain1)
        val encrypted2 = algorithm.encrypt("", plain2)
        val encrypted3 = algorithm.encrypt("", plain3)

        val entries = algorithm.encryptionEntries

        assertEquals(encrypted1, entries.getEncryptedEntry(plain1))
        assertEquals(encrypted2, entries.getEncryptedEntry(plain2))
        assertEquals(encrypted3, entries.getEncryptedEntry(plain3))

        assertEquals(plain1, entries.getDecryptedEntry(encrypted1))
        assertEquals(plain2, entries.getDecryptedEntry(encrypted2))
        assertEquals(plain3, entries.getDecryptedEntry(encrypted3))
    }

    @Test
    fun testNonCached() {
        val plain1 = "plain1"
        val plain2 = "plain2"
        val plain3 = "plain3"
        val algorithm = TestAlgorithm(false)
        algorithm.encrypt("", plain1)
        algorithm.encrypt("", plain2)
        algorithm.encrypt("", plain3)
        algorithm.encrypt("", plain3)
        algorithm.encrypt("", plain3)
        assertEquals(0, algorithm.encryptionEntries.size)
    }

    private class TestAlgorithm(
        keepValues: Boolean
    ) : MemoryCacheEncryptionAlgorithm(keepValues, HashCodeHashCreator()) {

        override fun generateKey(alias: String) = Unit

        override fun getKey(alias: String): Key = object : Key {

            override fun getAlgorithm(): String = ""

            override fun getEncoded(): ByteArray = ByteArray(0)

            override fun getFormat(): String = ""
        }

        override fun removeKey(alias: String) = Unit

        override fun doEncryption(alias: String, plainText: String): String {
            return "x${plainText}x"
        }

        override fun doDecryption(alias: String, encryptedText: String): String {
            return encryptedText.removePrefix("x").removeSuffix("x")
        }
    }
}
