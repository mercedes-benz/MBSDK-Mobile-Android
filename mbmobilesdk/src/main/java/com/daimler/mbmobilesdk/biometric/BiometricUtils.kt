package com.daimler.mbmobilesdk.biometric

import android.Manifest
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.daimler.mbmobilesdk.utils.isMarshmallow
import com.daimler.mbmobilesdk.utils.isPie
import com.daimler.mbuikit.utils.extensions.hasPermission

/**
 * Checks for preconditions for biometric scans.
 * Preconditions are checked in the following order:
 *  1. API Level (min. Android M)
 *  2. USE_FINGERPRINT permission
 *  3. Lock Screen security
 *  4. Fingerprint hardware
 *  5. Fingerprint configured
 *
 * @return the first found error or null, if all preconditions are fulfilled
 */
internal fun biometricsPreconditionsError(activity: Activity): BiometricPreconditionError? {
    if (!isMarshmallow()) return BiometricPreconditionError.API_LEVEL_INSUFFICIENT
    if (!activity.hasPermission(Manifest.permission.USE_FINGERPRINT)) return BiometricPreconditionError.NO_PERMISSION

    val keyguardManager = activity.keyguardManager()
    val fingerPrintManager = activity.fingerprintManager()

    return when {
        keyguardManager == null -> BiometricPreconditionError.NO_FINGERPRINT_SENSOR
        fingerPrintManager == null -> BiometricPreconditionError.NO_FINGERPRINT_SENSOR
        !keyguardManager.isKeyguardSecure -> BiometricPreconditionError.LOCK_SCREEN_NOT_SECURED
        !fingerPrintManager.isHardwareDetected -> BiometricPreconditionError.NO_FINGERPRINT_SENSOR
        !fingerPrintManager.hasEnrolledFingerprints() -> BiometricPreconditionError.NO_FINGERPRINT_CONFIGURED
        else -> null
    }
}

/**
 * Returns true if the current device configuration supports biometrics.
 */
internal fun biometricsPreconditionsFulfilled(activity: Activity) =
    biometricsPreconditionsError(activity) == null

internal fun biometricSensorAvailable(activity: Activity) =
    isMarshmallow() &&
        biometricPermissionGiven(activity) &&
        activity.fingerprintManager()?.isHardwareDetected == true

internal fun biometricPermissionGiven(activity: Activity) =
    when {
        isPie() -> activity.hasPermission(Manifest.permission.USE_BIOMETRIC)
        isMarshmallow() -> activity.hasPermission(Manifest.permission.USE_FINGERPRINT)
        else -> false
    }

private fun Context.keyguardManager() =
    getSystemService(Context.KEYGUARD_SERVICE) as? KeyguardManager

@RequiresApi(Build.VERSION_CODES.M)
private fun Context.fingerprintManager() =
    getSystemService(Context.FINGERPRINT_SERVICE) as? FingerprintManager