package com.daimler.mbmobilesdk.preferences.mbmobilesdk

import com.daimler.mbcommonkit.preferences.Preference

/**
 * Interface for preferences and settings used by all apps of the MBMobileSDK which might be
 * useful to be readable.
 * These settings are exclusively set by the SDK and cannot be changed from outside of the SDK.
 */
internal interface MBMobileSDKSettings {

    /**
     * Contains the unique device id for the user's device. This is a random generated UUID
     * that is equal for all MBMobileSDK-Apps on the same device.
     */
    val mobileSdkDeviceId: Preference<String>
}
