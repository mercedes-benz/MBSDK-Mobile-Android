package com.daimler.mbmobilesdk.app

enum class Region(internal val regionSuffix: String, internal val displayName: String) {
    ECE("", "ECE"),
    AMAP("-amap", "AMAP"),
    CN("-cn", "CN")
}