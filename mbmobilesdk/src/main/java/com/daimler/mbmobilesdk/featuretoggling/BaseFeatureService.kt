package com.daimler.mbmobilesdk.featuretoggling

internal abstract class BaseFeatureService : FeatureService {

    protected var defaults: FeatureDefaults? = null

    override fun registerFeatureDefaults(defaults: FeatureDefaults) {
        this.defaults = defaults
    }

    override fun clearFeatureDefaults() {
        defaults = null
    }

    protected inline fun <reified T> default(key: String): T? =
        defaults?.default(key, T::class.java)
}