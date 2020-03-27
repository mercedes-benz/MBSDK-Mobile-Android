package com.daimler.mbmobilesdk.preferences

import android.content.Context
import com.daimler.mbcommonkit.preferences.Preference
import com.daimler.mbmobilesdk.preferences.mbmobilesdk.BaseMBMobileSDKPreferences
import com.daimler.mbmobilesdk.preferences.mbmobilesdk.MBMobileSDKSettings
import com.daimler.mbmobilesdk.preferences.mbmobilesdk.PrivateMBMobileSDKPreferences
import com.daimler.mbmobilesdk.preferences.mbmobilesdk.SharedMBMobileSDKPreferences

internal class PreferencesProxy(
    context: Context,
    enableSharing: Boolean,
    sharedUserId: String
) : MBMobileSDKSettings {

    private val mobileSdkPreferences: BaseMBMobileSDKPreferences by lazy {
        if (enableSharing) SharedMBMobileSDKPreferences(
            context,
            sharedUserId
        ) else PrivateMBMobileSDKPreferences(
            context
        )
    }

    /*
    MBMobileSDK
     */
    override val mobileSdkDeviceId: Preference<String> = mobileSdkPreferences.deviceId
}
