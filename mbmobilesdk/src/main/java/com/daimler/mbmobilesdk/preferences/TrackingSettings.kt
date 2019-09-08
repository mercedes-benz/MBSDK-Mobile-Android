package com.daimler.mbmobilesdk.preferences

import com.daimler.mbcommonkit.preferences.Preference

internal interface TrackingSettings {

    val trackingEnabled: Preference<Boolean>

    fun resetTrackingSettings()
}