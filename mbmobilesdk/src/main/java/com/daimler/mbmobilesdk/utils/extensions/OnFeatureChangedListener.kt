package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.featuretoggling.OnFeatureChangedListener

fun OnFeatureChangedListener.registerForFeatureKeys(vararg keys: String) {
    keys.forEach { MBMobileSDK.featureToggleService().registerFeatureListener(it, this) }
}

fun OnFeatureChangedListener.unregisterForFeatureKeys(vararg keys: String) {
    keys.forEach { MBMobileSDK.featureToggleService().unregisterFeatureListener(it, this) }
}