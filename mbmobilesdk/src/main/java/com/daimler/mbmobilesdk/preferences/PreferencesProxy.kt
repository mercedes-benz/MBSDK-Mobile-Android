package com.daimler.mbmobilesdk.preferences

import android.content.Context
import com.daimler.mbmobilesdk.app.Endpoint
import com.daimler.mbmobilesdk.biometric.pincache.PinCache
import com.daimler.mbmobilesdk.biometric.pincache.PrivatePinCachePreferences
import com.daimler.mbmobilesdk.biometric.pincache.SharedPinCachePreferences
import com.daimler.mbmobilesdk.preferences.mbmobilesdk.MBMobileSDKSettings
import com.daimler.mbmobilesdk.preferences.mbmobilesdk.privateMBMobileSDKPreferences
import com.daimler.mbmobilesdk.preferences.mbmobilesdk.sharedMBMobileSDKPreferences
import com.daimler.mbmobilesdk.preferences.jumio.JumioSettings
import com.daimler.mbmobilesdk.preferences.jumio.PrivateJumioPreferences
import com.daimler.mbmobilesdk.preferences.jumio.SharedJumioPreferences
import com.daimler.mbmobilesdk.preferences.support.PrivateSupportPreferences
import com.daimler.mbmobilesdk.preferences.support.SharedSupportPreferences
import com.daimler.mbmobilesdk.preferences.support.SupportSettings
import com.daimler.mbmobilesdk.preferences.user.PrivateUserPreferences
import com.daimler.mbmobilesdk.preferences.user.SharedUserPreferences
import com.daimler.mbmobilesdk.preferences.user.UserSettings
import com.daimler.mbcommonkit.preferences.Preference

internal class PreferencesProxy(
    context: Context,
    enableSharing: Boolean,
    sharedUserId: String,
    endpoint: Endpoint
) : MBMobileSDKSettings, PushSettings, UserSettings, PinCache, TrackingSettings, SupportSettings, JumioSettings {

    private val mobileSdkPreferences = if (enableSharing) sharedMBMobileSDKPreferences(context, sharedUserId, endpoint) else privateMBMobileSDKPreferences(context, endpoint)
    private val pushPreferences = PushPreferences(context)
    private val userPreferences = if (enableSharing) SharedUserPreferences(context, sharedUserId) else PrivateUserPreferences(context)
    private val pinCache = if (enableSharing) SharedPinCachePreferences(context, sharedUserId) else PrivatePinCachePreferences(context)
    private val trackingPreferences = TrackingPreferences(context)
    private val supportPreferences = if (enableSharing) SharedSupportPreferences(context, sharedUserId) else PrivateSupportPreferences(context)
    private val jumioPreferences = if (enableSharing) SharedJumioPreferences(context, sharedUserId) else PrivateJumioPreferences(context)

    /*
    MBMobileSDK
     */
    override val endPoint: Preference<Endpoint> = mobileSdkPreferences.endpoint
    override val mobileSdkDeviceId: Preference<String> = mobileSdkPreferences.deviceId

    /*
    PUSH
     */
    override val fcmToken: Preference<String> = pushPreferences.fcmPushToken
    override val pushEnabled: Preference<Boolean> = pushPreferences.pushEnabled
    override val registrationId: Preference<String> = pushPreferences.registrationId

    /*
    USER
     */
    override val pinBiometricsEnabled: Preference<Boolean> = userPreferences.pinBiometricsEnabled
    override val promptedForBiometrics: Preference<Boolean> = userPreferences.promptedForBiometrics
    override fun reset() = userPreferences.reset()

    /*
    JUMIO
     */
    override val jumioTermsAccepted: Preference<Boolean> = jumioPreferences.jumioTermsAccepted

    /*
    PIN
     */
    override var pin: String
        get() = pinCache.pin
        set(value) {
            pinCache.pin = value
        }

    override fun clear() = pinCache.clear()

    /*
    TRACKING
     */
    override val trackingEnabled: Preference<Boolean> = trackingPreferences.trackingEnabled

    override fun resetTrackingSettings() = trackingPreferences.reset()

    override val cacCallDataSwitchEnabled: Preference<Boolean> = supportPreferences.cacCallDataSwitchEnabled
    override val ownPhoneNumber: Preference<String> = supportPreferences.ownPhoneNumber
    override val cacPhoneNumber: Preference<String> = supportPreferences.cacPhoneNumber
    override fun resetSupportSettings() = supportPreferences.resetSupportSettings()
}