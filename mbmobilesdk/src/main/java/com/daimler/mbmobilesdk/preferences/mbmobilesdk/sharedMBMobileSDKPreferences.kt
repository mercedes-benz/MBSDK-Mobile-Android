package com.daimler.mbmobilesdk.preferences.mbmobilesdk

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbmobilesdk.app.Endpoint
import com.daimler.mbmobilesdk.preferences.PreferencesInitializable
import com.daimler.mbmobilesdk.preferences.PreferencesInitializer
import com.daimler.mbcommonkit.extensions.custom
import com.daimler.mbcommonkit.extensions.getMultiAppSharedPreferences
import java.util.*

internal class sharedMBMobileSDKPreferences(
    context: Context,
    sharedUserId: String,
    endpoint: Endpoint
) : PreferencesInitializable, BaseMBMobileSDKPreferences(endpoint) {

    override val prefs: SharedPreferences =
        context.getMultiAppSharedPreferences(SETTINGS_SHARED, sharedUserId)

    init {
        PreferencesInitializer(this).initialize(context, sharedUserId, SETTINGS_SHARED)
    }

    override fun needsInitialization(): Boolean = deviceId.get().isBlank()

    override fun arePreferencesInitialized(preferences: SharedPreferences): Boolean =
        !preferences.getString(KEY_DEVICE_ID, null).isNullOrBlank()

    override fun copyFromPreferences(preferences: SharedPreferences) {
        // copy endpoint
        val tmpEndpoint = preferences.custom(endpointConverter)
        endpoint.set(tmpEndpoint.get())

        // copy device id
        val tmpDeviceId = preferences.getString(KEY_DEVICE_ID, "").orEmpty()
        deviceId.set(tmpDeviceId)
    }

    override fun onNoInitializedPreferencesFound() {
        deviceId.set(UUID.randomUUID().toString())
    }

    private companion object {

        @Suppress("UNUSED")
        private const val SETTINGS_INTERNAL = "mb.app.family.settings.internal"
        private const val SETTINGS_SHARED = "mb.app.family.settings.shared"
    }
}