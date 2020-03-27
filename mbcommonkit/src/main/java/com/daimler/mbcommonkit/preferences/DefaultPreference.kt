package com.daimler.mbcommonkit.preferences

import java.util.concurrent.CopyOnWriteArrayList

/**
 * [Preference] implementation that provides methods to read and write objects and also
 * to observe changes.
 *
 * @param reader reads the value from the SharedPreferences
 * @param writer the value to the SharedPreferences
 */
open class DefaultPreference<T>(
    private val reader: () -> T,
    private val writer: (T) -> Unit
) : Preference<T> {

    private val observers = CopyOnWriteArrayList<PreferenceObserver<T>>()

    /**
     * Returns the object returned by the reader.
     */
    override fun get(): T = reader()

    /**
     * Executes the writer and notifies any observers if the given value is different
     * from the current value.
     */
    override fun set(value: T) {
        val oldValue = get()
        if (oldValue != value) {
            writer(value)
            notifyObservers(value)
        }
    }

    override fun observe(observer: PreferenceObserver<T>) {
        synchronized(observers) {
            observers.add(observer)
        }
    }

    override fun stopObserving(observer: PreferenceObserver<T>) {
        synchronized(observers) {
            observers.remove(observer)
        }
    }

    private fun notifyObservers(value: T) {
        synchronized(observers) {
            observers.forEach { it.onChanged(value) }
        }
    }
}
