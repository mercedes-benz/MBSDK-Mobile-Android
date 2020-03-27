package com.daimler.mbcommonkit.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import com.daimler.mbcommonkit.preferences.SharedUserIdNotSetException

/**
 * Returns a list of [PackageInfo] where the sharedUserId is equal to [sharedUserId].
 */
fun getPackagesWithSharedUserId(context: Context, sharedUserId: String): List<PackageInfo> {
    val packages = context.packageManager.getInstalledPackages(0)
    return packages.filter {
        it.sharedUserId == sharedUserId
    }
}

/**
 * Returns SharedPreferences created with the package context for the provided [packageName].
 * The SharedPreferences are created with the mode [Context.MODE_MULTI_PROCESS].
 *
 * @param context context
 * @param packageName the package for which to create the preferences object
 * @param settingsName the name for the shared preferences
 */
fun preferencesWithPackageContext(
    context: Context,
    packageName: String,
    settingsName: String
): SharedPreferences =
    preferences(context.createPackageContext(packageName, 0), settingsName)

/**
 * Returns a list containing [SharedPreferences] for packages with the given sharedUserId.
 */
fun preferencesForSharedUserId(
    context: Context,
    settingsName: String,
    sharedUserId: String
): List<SharedPreferences> {
    val packages = getPackagesWithSharedUserId(context, sharedUserId)
    return packages.map { preferencesWithPackageContext(context, it.packageName, settingsName) }
}

/**
 * Throws an [SharedUserIdNotSetException] if there is no sharedUserId configured in the manifest
 * or if this sharedUserId is different from the configured one.
 */
internal fun checkSharedUserIdConfigured(context: Context, sharedUserId: String) {
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    if (packageInfo.sharedUserId != sharedUserId) throw SharedUserIdNotSetException(sharedUserId)
}

private fun preferences(context: Context, settingsName: String) =
    context.getSharedPreferences(settingsName, Context.MODE_MULTI_PROCESS)
