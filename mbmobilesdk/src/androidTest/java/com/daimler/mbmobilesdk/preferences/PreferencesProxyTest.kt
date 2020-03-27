package com.daimler.mbmobilesdk.preferences

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class PreferencesProxyTest {

    private lateinit var context: Context

    @Before
    fun before() {
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun test_getMobileSdkDeviceId_sharingDisabled_shouldReturnDeviceId() {
        val preferencesProxy = PreferencesProxy(context, false, "com.daimler")
        assertNotNull(preferencesProxy.mobileSdkDeviceId.get())
    }

    @Test
    fun test_getMobileSdkDeviceId_sharingEnabled_shouldReturnDeviceId() {
        val preferencesProxy = PreferencesProxy(context, true, "com.daimler")
        assertNotNull(preferencesProxy.mobileSdkDeviceId.get())
    }
}
