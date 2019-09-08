package com.daimler.mbmobilesdk.biometric.auth

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.daimler.mbmobilesdk.biometric.iv.PrivateAppPreferencesIvProvider

@RequiresApi(Build.VERSION_CODES.M)
internal class privateMBMobileSDKBiometrics(app: Application) : BaseMBMobileSDKBiometrics() {

    override val crypto: BiometricPromptAuthenticator by lazy {
        BiometricPromptAuthenticator(PrivateAppPreferencesIvProvider(app), KEY_ALIAS)
    }

    private companion object {
        private const val KEY_ALIAS = "com.daimler.mb.biometric.id.auth.private.alias"
    }
}