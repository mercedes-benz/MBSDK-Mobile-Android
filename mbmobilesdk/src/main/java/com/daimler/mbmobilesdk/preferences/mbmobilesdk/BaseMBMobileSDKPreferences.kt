package com.daimler.mbmobilesdk.preferences.mbmobilesdk

import android.content.SharedPreferences
import com.daimler.mbmobilesdk.app.Endpoint
import com.daimler.mbmobilesdk.preferences.JsonPreferencesConverter
import com.daimler.mbcommonkit.extensions.custom
import com.daimler.mbcommonkit.extensions.stringPreference
import com.daimler.mbcommonkit.preferences.Preference

internal abstract class BaseMBMobileSDKPreferences(endpoint: Endpoint) {

    protected abstract val prefs: SharedPreferences

    /**
     * Current stage and region, used by all apps.
     */
    val endpoint: Preference<Endpoint> by lazy {
        prefs.custom(endpointConverter)
    }

    /**
     * Random device id, set once and used for all apps. This field is also used to decide
     * whether this preferences need to be initialized.
     */
    val deviceId: Preference<String> by lazy {
        prefs.stringPreference(KEY_DEVICE_ID)
    }

    protected val endpointConverter = JsonPreferencesConverter(
        KEY_ENDPOINT, endpoint, Endpoint::class.java
    )

    protected companion object {
        const val KEY_ENDPOINT = "APP_ENDPOINT"
        const val KEY_DEVICE_ID = "RS_DEVICE_ID"
    }
}