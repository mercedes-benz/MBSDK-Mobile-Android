package com.daimler.mbmobilesdk.example

import androidx.multidex.MultiDexApplication
import com.daimler.mbmobilesdk.app.*
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbloggerkit.PrinterConfig
import com.daimler.mbloggerkit.adapter.AndroidLogAdapter
import com.daimler.mbloggerkit.adapter.PersistingLogAdapter
import com.daimler.mbloggerkit.shake.LogOverlay

class ExampleApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        MBLoggerKit.usePrinterConfig(PrinterConfig.Builder()
            .showLogMenuWithShake(this, LogOverlay.Order.CHRONOLOGICAL_DESCENDING)
            .addAdapter(AndroidLogAdapter.Builder()
                .setLoggingEnabled(BuildConfig.DEBUG)
                .build())
            .addAdapter(PersistingLogAdapter.Builder(this)
                .useMemoryLogging()
                .setLoggingEnabled(BuildConfig.DEBUG)
                .build())
            .build())

        LogoutSessionExpiredHandler.init(this, LoginActivity::class.java)
        MBMobileSDK.init(MBMobileSDKConfig.Builder(this, BuildConfig.DEBUG, "reference", "app", BuildConfig.DEBUG )
            .enableFeatureToggling(true)
            .useSessionExpiredHandler(LogoutSessionExpiredHandler)
            .useInitialActivity(MainActivity::class.java)
            .enableSso(true)
            .defaultRegionAndStage(Region.ECE, Stage.PROD)
            .build())
    }
}