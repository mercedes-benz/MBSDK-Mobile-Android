package com.daimler.mbcommonkit.preferences

/**
 * Interface that is used to represent an object that can be read from the
 * SharedPreferences.
 */
interface ReadOnlyPreference<T> {

    /**
     * Returns the current value of this preference.
     */
    fun get(): T

    /**
     * Adds an observer that will be notified on changes; more specifically:
     * if [set] is called with a value different from the value currently returned by [get].
     */
    fun observe(observer: PreferenceObserver<T>)

    /**
     * Removes the given observer.
     */
    fun stopObserving(observer: PreferenceObserver<T>)
}
