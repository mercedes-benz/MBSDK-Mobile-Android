package com.daimler.mbcommonkit.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbcommonkit.preferences.DefaultPreference
import com.daimler.mbcommonkit.preferences.EncryptedSharedPreferences
import com.daimler.mbcommonkit.preferences.Preference
import com.daimler.mbcommonkit.preferences.PreferencesConverter
import com.daimler.mbcommonkit.preferences.SharedUserIdPreferences
import com.daimler.mbcommonkit.preferences.chunks.EncryptedPreferencesChunkHelper.shouldUseChunksForCurrentDevice
import com.daimler.mbcommonkit.preferences.chunks.EncryptedSharedPreferencesWithChunks
import com.daimler.mbcommonkit.security.Crypto

/**
 * Creates a Boolean preference.
 */
fun SharedPreferences.booleanPreference(
    key: String,
    default: Boolean = false
): Preference<Boolean> =
    DefaultPreference(
        { getBoolean(key, default) },
        { edit().putBoolean(key, it).apply() }
    )

/**
 * Creates a String preference.
 */
fun SharedPreferences.stringPreference(key: String, default: String = ""): Preference<String> =
    DefaultPreference(
        { getString(key, default) ?: default },
        { edit().putString(key, it).apply() }
    )

/**
 * Creates an Int preference.
 */
fun SharedPreferences.intPreference(key: String, default: Int = 0): Preference<Int> =
    DefaultPreference(
        { getInt(key, default) },
        { edit().putInt(key, it).apply() }
    )

/**
 * Creates a Float preference.
 */
fun SharedPreferences.floatPreference(key: String, default: Float = 0f): Preference<Float> =
    DefaultPreference(
        { getFloat(key, default) },
        { edit().putFloat(key, it).apply() }
    )

/**
 * Creates a Long preference.
 */
fun SharedPreferences.longPreference(key: String, default: Long = 0): Preference<Long> =
    DefaultPreference(
        { getLong(key, default) },
        { edit().putLong(key, it).apply() }
    )

/**
 * Creates a Set<String> preference.
 */
fun SharedPreferences.stringSetPreference(
    key: String,
    default: Set<String> = HashSet()
): Preference<Set<String>> =
    DefaultPreference(
        { getStringSet(key, default) ?: default },
        { edit().putStringSet(key, it).apply() }
    )

/**
 * Creates a custom preference that uses the given converter.
 */
fun <T> SharedPreferences.custom(converter: PreferencesConverter<T>): Preference<T> =
    DefaultPreference(
        { converter.readFromPreferences(this) },
        { converter.writeToPreferences(edit(), it) }
    )

/**
 * Increments the integer value for the given [key] and writes it back to the preferences.
 */
fun SharedPreferences.increment(key: String, default: Int = 0) {
    val value = getInt(key, default)
    edit().putInt(key, value.inc()).apply()
}

/**
 * Decrements the integer value for the given [key] and writes it back to the preferences.
 */
fun SharedPreferences.decrement(key: String, default: Int = 0) {
    val value = getInt(key, default)
    edit().putInt(key, value.dec()).apply()
}

/**
 * Returns [SharedPreferences] that perform all write operations on all preferences with the
 * given [settingsName] for the given [sharedUserId].
 *
 * @throws SharedUserIdNotSetException if no shared user id is defined in the manifest or if the
 * defined sharedUserId is different from the given one
 */
fun Context.getMultiAppSharedPreferences(
    settingsName: String,
    sharedUserId: String
): SharedPreferences {
    val preferences = getSharedPreferences(settingsName, Context.MODE_MULTI_PROCESS)
    return SharedUserIdPreferences(this, preferences, sharedUserId, settingsName)
}

/**
 * Returns [EncryptedSharedPreferences] using the specified parameters.
 */
fun Context.getEncryptedSharedPreferences(
    alias: String,
    settingsName: String,
    mode: Int = Context.MODE_PRIVATE,
    keepEncryptedValuesInMemory: Boolean = false
): EncryptedSharedPreferences {
    return if (shouldUseChunksForCurrentDevice()) {
        EncryptedSharedPreferencesWithChunks(
            this,
            alias,
            settingsName,
            mode,
            keepEncryptedValuesInMemory
        )
    } else {
        EncryptedSharedPreferences(this, alias, settingsName, mode, keepEncryptedValuesInMemory)
    }
}

/**
 * Returns [EncryptedSharedPreferences] using the specified parameters.
 */
fun Context.getEncryptedSharedPreferences(
    alias: String,
    settingsName: String,
    mode: Int = Context.MODE_PRIVATE,
    crypto: Crypto
): EncryptedSharedPreferences {
    return if (shouldUseChunksForCurrentDevice()) {
        EncryptedSharedPreferencesWithChunks(this, alias, settingsName, mode, crypto)
    } else {
        EncryptedSharedPreferences(this, alias, settingsName, mode, crypto)
    }
}

/**
 * Allows editing of this preference instance with a call to [apply][SharedPreferences.Editor.apply]
 * or [commit][SharedPreferences.Editor.commit] to persist the changes.
 * Default behaviour is [apply][SharedPreferences.Editor.apply].
 * ```
 * prefs.edit {
 *     putString("key", value)
 * }
 * ```
 * To [commit][SharedPreferences.Editor.commit] changes:
 * ```
 * prefs.edit(commit = true) {
 *     putString("key", value)
 * }
 * ```
 */
@SuppressLint("ApplySharedPref")
inline fun SharedPreferences.edit(
    commit: Boolean = false,
    action: SharedPreferences.Editor.() -> Unit
) {
    val editor = edit()
    action(editor)
    if (commit) {
        editor.commit()
    } else {
        editor.apply()
    }
}
