package com.daimler.mbmobilesdk.login

import androidx.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DummyLoginTest : GenericLoginTest() {

    override val userName = "therisingstars.email+integrationtest@gmail.com"

    override fun fetchPin(): String = "280888"

    @Test
    override fun testLogin() {
        super.testLogin()
    }
}