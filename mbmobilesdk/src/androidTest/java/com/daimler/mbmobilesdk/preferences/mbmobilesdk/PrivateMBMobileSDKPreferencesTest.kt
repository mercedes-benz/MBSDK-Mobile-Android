package com.daimler.mbmobilesdk.preferences.mbmobilesdk

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class PrivateMBMobileSDKPreferencesTest {

    private lateinit var preferences: PrivateMBMobileSDKPreferences

    @Before
    fun before() {
        val context: Context = InstrumentationRegistry.getInstrumentation().context
        preferences = PrivateMBMobileSDKPreferences(context)
    }

    @Test
    fun test_getDeviceId_shouldReturnGeneratedDeviceId() {
        assertNotNull(preferences.deviceId.get())
    }
}
