package com.daimler.mbcommonkit.preferences

/**
 * Interface that is used to represent an object that can be read and written to the
 * SharedPreferences.
 */
interface Preference<T> : ReadOnlyPreference<T> {

    /**
     * Sets a new value to this preference.
     */
    fun set(value: T)
}
