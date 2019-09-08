package com.daimler.mbmobilesdk.biometric.pincache

internal interface PinCache {

    var pin: String

    fun clear()
}