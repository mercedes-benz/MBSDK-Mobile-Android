package com.daimler.mbmobilesdk.settings

import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbingresskit.MBIngressKit

internal object SettingsManager {

    fun pushSettingsHandler(): PushSettingsHandler = PushSettingsHandlerImpl(
        MBMobileSDK.notificationsService(),
        MBMobileSDK.pushSettings()
    )

    fun biometricsSettingsHandler(): BiometricsSettingsHandler = BiometricsSettingsHandlerImpl(
        MBIngressKit.userService(),
        MBMobileSDK.userPinCache(),
        MBMobileSDK.userSettings(),
        MBMobileSDK.biometrics()
    )
}