package com.daimler.mbcommonkit.preferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import com.daimler.mbcommonkit.security.Crypto
import com.daimler.mbcommonkit.security.EncryptionAlgorithm
import com.daimler.mbloggerkit.MBLoggerKit
import java.lang.NumberFormatException

/**
 * [SharedPreferences] that encrypt both the key and the value of a key-value pair.
 * Encoding is done by AES (Android >= M) using the Base64 representation
 * of the keys and values.
 *
 * @param context any context
 * @param alias The alias for the key in the AndroidKeyStore. A new key will be created if there
 * is none with the specified name.
 * @param settingsName the name to give the settings
 * @param mode the operating mode
 * @param crypto the [Crypto] object to use for encryption and decryption
 */
open class EncryptedSharedPreferences(
    context: Context,
    private val alias: String,
    settingsName: String,
    mode: Int = Context.MODE_PRIVATE,
    private val crypto: Crypto
) : SharedPreferences {

    private val preferences: SharedPreferences = context.getSharedPreferences(settingsName, mode)

    /**
     * Secondary constructor for [EncryptedSharedPreferences] without a specific [Crypto] object.
     */
    constructor(
        context: Context,
        alias: String,
        settingsName: String,
        mode: Int = Context.MODE_PRIVATE,
        keepEncryptedValuesInMemory: Boolean = false
    ) : this(context, alias, settingsName, mode, Crypto(keepEncryptedValuesInMemory))

    init {
        if (!crypto.keyExists(alias)) crypto.generateKey(alias)
    }

    override fun contains(key: String) = preferences.contains(encrypted(key))

    override fun getBoolean(key: String, defValue: Boolean) = read(key, defValue, String::toBoolean)

    override fun getInt(key: String, defValue: Int) = read(key, defValue, String::toInt)

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    override fun getAll(): Map<String, *> {
        val all = preferences.all
        if (all.isEmpty()) return HashMap<String, Any>()

        val map = HashMap<String, Any?>(all.size)
        all.forEach {
            val key = decrypted(it.key)
            val value = if (it.value is Set<*>) {
                // We need to decrypt every single element of a Set.
                decryptStringSetContent(it.value as Set<String>)
            } else {
                // Just decrypt the value.
                decrypted(it.value?.toString().orEmpty())
            }
            map[key] = value
        }
        return map
    }

    override fun getLong(key: String, defValue: Long) = read(key, defValue, String::toLong)

    override fun getFloat(key: String, defValue: Float) = read(key, defValue, String::toFloat)

    override fun getStringSet(key: String, defValues: Set<String>?): Set<String>? {
        val encryptedSet =
            preferences.getStringSet(encrypted(key), null) ?: return HashSet()
        return decryptStringSetContent(encryptedSet)
    }

    override fun getString(key: String, defValue: String?) =
        read(key, defValue ?: "", String::toString)

    override fun edit(): SharedPreferences.Editor = EncryptedEditor(preferences.edit())

    override fun registerOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener
    ) {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener
    ) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    /**
     * Calculates the Base64 hash of the string representation of the given value and encrypts it.
     */
    protected fun <T : Any> encrypted(value: T) =
        crypto.encrypt(alias, value.toString().toBase64())

    /**
     * Decrypts the given string and reverts its Base64 hash.
     */
    protected fun decrypted(encrypted: String) =
        crypto.decrypt(alias, encrypted).fromBase64()

    protected fun getEncryptedString(key: String, defValue: String) =
        preferences.getString(encrypted(key), defValue) ?: defValue

    private fun decryptStringSetContent(encryptedSet: Set<String>): Set<String> {
        val set = HashSet<String>()
        encryptedSet.forEach { set.add(decrypted(it)) }
        return set
    }

    /**
     * Returns the DECRYPTED value from the preferences.
     */
    private fun <T : Any> read(key: String, defValue: T, format: ((String) -> T)) = try {
        format(decrypted(preferences.getString(encrypted(key), encrypted(defValue)).orEmpty()))
    } catch (exception: EncryptionAlgorithm.AlgorithmException) {
        MBLoggerKit.e("${exception.message}. Therefore the passed defValue will be used")
        defValue
    } catch (exception: NumberFormatException) {
        MBLoggerKit.e("${exception.message}. Therefore the passed defValue will be used")
        defValue
    }

    private fun String.toBase64() =
        Base64.encodeToString(toByteArray(Charsets.UTF_8), Base64.NO_WRAP)

    private fun String.fromBase64() =
        String(Base64.decode(this, Base64.NO_WRAP), Charsets.UTF_8)

    private inner class EncryptedEditor(
        private val editor: SharedPreferences.Editor
    ) : SharedPreferences.Editor by editor {

        override fun putLong(key: String, value: Long): SharedPreferences.Editor {
            encryptAndWrite(key, value)
            return this
        }

        override fun putInt(key: String, value: Int): SharedPreferences.Editor {
            encryptAndWrite(key, value)
            return this
        }

        override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
            encryptAndWrite(key, value)
            return this
        }

        override fun putStringSet(key: String, values: Set<String>?): SharedPreferences.Editor {
            val set = HashSet<String>(values?.size ?: 0)
            values?.forEach { set.add(encrypted(it)) }
            editor.putStringSet(encrypted(key), set)
            return this
        }

        override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
            encryptAndWrite(key, value)
            return this
        }

        override fun putString(key: String, value: String?): SharedPreferences.Editor {
            encryptAndWrite(key, value ?: "")
            return this
        }

        override fun remove(key: String): SharedPreferences.Editor {
            editor.remove(encrypted(key))
            return this
        }

        private fun <T : Any> encryptAndWrite(key: String, value: T) =
            editor.putString(encrypted(key), encrypted(value))
    }
}
