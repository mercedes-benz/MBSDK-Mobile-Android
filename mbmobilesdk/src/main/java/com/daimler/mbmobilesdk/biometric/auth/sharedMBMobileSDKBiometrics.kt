package com.daimler.mbmobilesdk.biometric.auth

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.daimler.mbmobilesdk.biometric.iv.SharedAppPreferencesIvProvider

@RequiresApi(Build.VERSION_CODES.M)
internal class sharedMBMobileSDKBiometrics(
    app: Application,
    sharedUserId: String
) : BaseMBMobileSDKBiometrics() {

    override val crypto: BiometricPromptAuthenticator by lazy {
        BiometricPromptAuthenticator(SharedAppPreferencesIvProvider(app, sharedUserId), KEY_ALIAS)
    }

    private companion object {
        private const val KEY_ALIAS = "com.daimler.mb.biometric.id.auth.alias"
    }
}