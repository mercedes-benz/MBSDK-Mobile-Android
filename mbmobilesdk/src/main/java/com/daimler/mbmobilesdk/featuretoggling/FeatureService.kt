package com.daimler.mbmobilesdk.featuretoggling

import com.daimler.mbnetworkkit.task.FutureTask

interface FeatureService {

    /**
     * Registers the given [UserContext] as the current user for this service.
     * All features will update according to the new rule set that applies to this user.
     *
     * @return a [FutureTask] that receives the id of the new user as completion result
     */
    fun swapUserContext(userContext: UserContext): FutureTask<String, Throwable>

    /**
     * Registers [FeatureDefaults] to this service that are used when the service
     * cannot load a feature flag or variation for a specific key.
     */
    fun registerFeatureDefaults(defaults: FeatureDefaults)

    /**
     * Clears the [FeatureDefaults].
     */
    fun clearFeatureDefaults()

    /**
     * Registers a [OnFeatureChangedListener] that gets called when the feature with the
     * given key changes.
     */
    fun registerFeatureListener(key: String, listener: OnFeatureChangedListener)

    /**
     * Unregisters the given [OnFeatureChangedListener] for the given feature key.
     */
    fun unregisterFeatureListener(key: String, listener: OnFeatureChangedListener)

    /**
     * Returns true if the toggle for the given [key] is enabled. Returns [default] if the key
     * is non-existent or if the toggle is not of type [Boolean].
     */
    fun isToggleEnabled(key: String, default: Boolean): Boolean

    /**
     * Same as [isToggleEnabled] but uses the registered [FeatureDefaults] if available.
     * False is taken as default value if no defaults are available.
     */
    fun isToggleEnabled(key: String): Boolean

    /**
     * Returns a map that contains all currently known feature flags with their values
     * for the current [UserContext].
     */
    fun getAllFlags(): Map<String, Any?>
}