package com.daimler.mbmobilesdk.utils

import android.os.Build

/**
 * Returns true if the current SDK version is at least [version].
 */
fun isAndroidSdkVersion(version: Int) = Build.VERSION.SDK_INT >= version

/**
 * Returns true if the device runs on Android Lollipop or higher.
 */
fun isLollipop() = isAndroidSdkVersion(Build.VERSION_CODES.LOLLIPOP)

/**
 * Returns true if the device runs on Android Marshmallow or higher.
 */
fun isMarshmallow() = isAndroidSdkVersion(Build.VERSION_CODES.M)

/**
 * Returns true if the device runs on Android Nougat or higher.
 */
fun isNougat() = isAndroidSdkVersion(Build.VERSION_CODES.N)

/**
 * Returns true if the device runs on Android Oreo or higher.
 */
fun isOreo() = isAndroidSdkVersion(Build.VERSION_CODES.O)

/**
 * Returns true if the device runs on Android Pie or higher.
 */
fun isPie() = isAndroidSdkVersion(Build.VERSION_CODES.P)