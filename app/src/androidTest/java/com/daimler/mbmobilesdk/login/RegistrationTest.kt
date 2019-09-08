package com.daimler.mbmobilesdk.login

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.runner.AndroidJUnit4
import com.daimler.mbmobilesdk.example.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrationTest : BaseCredentialsTest() {

    override val userName = "appsfactory.daimler+unregistered@gmail.com"

    override val idlingResourceTitle = "registration"

    @Test
    fun testRegistration() {
        prepareTestCases()

        Espresso
            .onView(ViewMatchers.withId(R.id.registration_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso
            .onView(ViewMatchers.withId(R.id.checkbox_mail))
            .check(ViewAssertions.matches(ViewMatchers.isChecked()))
        Espresso
            .onView(ViewMatchers.withId(R.id.checkbox_phone))
            .check(ViewAssertions.matches(ViewMatchers.isNotChecked()))

        Espresso
            .onView(ViewMatchers.withId(R.id.edit_mail))
            .check(ViewAssertions.matches(ViewMatchers.withText(userName)))
    }
}