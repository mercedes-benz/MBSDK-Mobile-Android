package com.daimler.mbmobilesdk.featuretoggling

import com.daimler.mbmobilesdk.app.MBMobileSDK

internal const val FLAG_SOE_TERMS_OF_USE = "ris-sdk-soe-terms-of-use"
internal const val FLAG_PROFILE_SHOW_ADDRESS = "ris-sdk-profile-show-address"
internal const val FLAG_NATCON_TERMS_OF_USE = "ris-sdk-show-nat-con"
internal const val FLAG_BETA_PHASE_CONTENT = "ris-sdk-beta-phase-content"
internal const val FLAG_SUPPORT_BETA_NUMBER = "ris-sdk-support-show-beta-phone-number"
internal const val FLAG_IDENTITY_CHECK_MODULE = "ris-sdk-show-identity-check"
internal const val FLAG_SUPPORT_SHOW_MODULE = "ris-sdk-show-support-module"
internal const val FLAG_SUPPORT_TRANSFER_DATA_WITH_CALL = "ris-sdk-transfer-data-with-support-call"
internal const val FLAG_SHOW_STAGE_INDICATORS = "ris-sdk-registration-show-stages"
internal const val FLAG_NOTIFICATION_CENTER = "ris-sdk-show-inbox"
internal const val FLAG_BLUETOOTH_ENABLEMENT = "ris-sdk-send-to-car-bluetooth"

internal val FEATURE_DEFAULTS: FeatureDefaults by lazy { createDefaults() }

internal fun isFeatureToggleEnabled(key: String) =
    MBMobileSDK.featureToggleService().isToggleEnabled(key)

private fun createDefaults(): FeatureDefaults {
    val features = HashMap<String, Any?>()
    features.apply {
        put(FLAG_SOE_TERMS_OF_USE, true)
        put(FLAG_PROFILE_SHOW_ADDRESS, true)
        put(FLAG_NATCON_TERMS_OF_USE, true)
        put(FLAG_BETA_PHASE_CONTENT, true)
        put(FLAG_SUPPORT_BETA_NUMBER, true)
        put(FLAG_IDENTITY_CHECK_MODULE, true)
        put(FLAG_SUPPORT_SHOW_MODULE, false)
        put(FLAG_SUPPORT_TRANSFER_DATA_WITH_CALL, false)
        put(FLAG_SHOW_STAGE_INDICATORS, false)
        put(FLAG_NOTIFICATION_CENTER, false)
        put(FLAG_BLUETOOTH_ENABLEMENT, false)
    }
    return FeatureCollection(features)
}

private class FeatureCollection(
    private val inner: HashMap<String, Any?>
) : Map<String, Any?> by inner, FeatureDefaults {

    @Suppress("UNCHECKED_CAST")
    override fun <T> default(key: String, type: Class<T>): T? {
        val value = this[key]
        return value?.let {
            if (type.isAssignableFrom(it.javaClass)) {
                it as T
            } else {
                null
            }
        }
    }

    override fun keys(): List<String> = ArrayList(keys)

    override fun values(): List<Any?> = ArrayList(values)

    override fun merge(defaults: FeatureDefaults) {
        val keys = defaults.keys()
        keys.forEach {
            if (!containsKey(it)) inner[it] = defaults.default(it, Any::class.java)
        }
    }
}