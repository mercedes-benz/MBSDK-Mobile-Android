package com.daimler.mbmobilesdk.featuretoggling

/**
 * Interface used to provide feature defaults.
 */
interface FeatureDefaults {

    /**
     * Returns the default value for the given feature key or null if the key does not exist
     * in the defaults or if the value corresponding to the given key has another type than
     * specified.
     */
    fun <T> default(key: String, type: Class<T>): T?

    /**
     * Returns a list that contains all feature keys.
     */
    fun keys(): List<String>

    /**
     * Returns a list that contains all default feature values.
     */
    fun values(): List<Any?>

    /**
     * Merges the given FeatureDefaults into this FeatureDefaults.
     * If there are duplicated keys, the value from this FeatureDefaults will be used.
     */
    fun merge(defaults: FeatureDefaults)
}