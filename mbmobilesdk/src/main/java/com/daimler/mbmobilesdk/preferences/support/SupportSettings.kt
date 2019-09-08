package com.daimler.mbmobilesdk.preferences.support

import com.daimler.mbcommonkit.preferences.Preference

/**
 * Interface for support preferences, containg phone numbers and last locale for caching
 * These settings are exclusively set by the SDK and cannot be changed from outside of the SDK.
 */
internal interface SupportSettings {

    /**
     * The switch state for additional data send on call
     */
    val cacCallDataSwitchEnabled: Preference<Boolean>

    /**
     * The own phone number, to verify at CAC
     */
    val ownPhoneNumber: Preference<String>

    /**
     * The cached CAC phone number, as received from MBSupportKit
     */
    val cacPhoneNumber: Preference<String>

    /**
     * Clears all support prefs, if user logs out
     */
    fun resetSupportSettings()
}