package com.daimler.mbmobilesdk.preferences.mbmobilesdk

import android.content.SharedPreferences
import com.daimler.mbcommonkit.extensions.stringPreference
import com.daimler.mbcommonkit.preferences.Preference
import java.util.UUID

internal abstract class BaseMBMobileSDKPreferences {

    protected abstract val prefs: SharedPreferences

    /**
     * Random device id, set once and used for all apps. This field is also used to decide
     * whether this preferences need to be initialized.
     */
    val deviceId: Preference<String> by lazy {
        prefs.stringPreference(KEY_DEVICE_ID)
    }

    protected fun generateDeviceId(): String = UUID.randomUUID().toString()

    protected companion object {
        const val KEY_DEVICE_ID = "RS_DEVICE_ID"
    }
}
