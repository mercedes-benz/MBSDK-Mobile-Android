package com.daimler.mbmobilesdk.preferences.mbmobilesdk

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class SharedMBMobileSDKPreferencesTest {

    private lateinit var preferences: SharedMBMobileSDKPreferences

    @Before
    fun before() {
        val context: Context = InstrumentationRegistry.getInstrumentation().context
        preferences = SharedMBMobileSDKPreferences(context, "com.daimler")
    }

    @Test
    fun test_getDeviceId_shouldReturnGeneratedDeviceId() {
        assertNotNull(preferences.deviceId.get())
    }
}
