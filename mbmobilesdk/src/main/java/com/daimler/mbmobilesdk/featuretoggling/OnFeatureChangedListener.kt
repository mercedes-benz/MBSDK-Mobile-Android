package com.daimler.mbmobilesdk.featuretoggling

/**
 * Listener that indicates feature flag changes.
 */
interface OnFeatureChangedListener {

    /**
     * Called when a feature flag changed.
     *
     * @param key the key for the changed feature flag
     */
    fun onFeatureChanged(key: String)
}