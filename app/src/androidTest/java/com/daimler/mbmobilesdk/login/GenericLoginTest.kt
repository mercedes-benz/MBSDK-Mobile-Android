package com.daimler.mbmobilesdk.login

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import com.daimler.mbmobilesdk.example.R
import com.daimler.mbmobilesdk.example.menu.MenuActivity

abstract class GenericLoginTest : BaseCredentialsTest() {

    override val idlingResourceTitle = "login"

    open fun testLogin() {
        prepareTestCases()

        Espresso.onView(ViewMatchers.withId(R.id.pin_title)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        val pin = fetchPin()
        println("Received PIN: $pin")
        Espresso.onView(ViewMatchers.withId(R.id.edit_pin)).perform(ViewActions.clearText()).perform(ViewActions.typeText(pin)).perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.btn_continue)).perform(ViewActions.click())
        idlingResource.start()

        Intents.intended(IntentMatchers.hasComponent(MenuActivity::class.java.name))
    }

    protected abstract fun fetchPin(): String
}