package com.daimler.mbmobilesdk.biometric.pincache

internal abstract class BasePreferencesPinCache : PinCache {

    protected companion object {
        const val KEY_PIN = "mb.biometric.pin"
    }
}