package com.daimler.mbmobilesdk.login

import android.view.TextureView
import android.view.View
import androidx.annotation.CallSuper
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.daimler.mbmobilesdk.example.MainActivity
import com.daimler.mbmobilesdk.example.R
import com.daimler.mbmobilesdk.utils.TimedIdlingResource
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseCredentialsTest {

    @Rule
    @JvmField
    val mainActivityRule = ActivityTestRule(MainActivity::class.java)

    protected abstract val userName: String
    protected abstract val idlingResourceTitle: String

    protected lateinit var idlingResource: TimedIdlingResource

    @Before
    @CallSuper
    open fun before() {
        idlingResource = TimedIdlingResource(idlingResourceTitle)
        Intents.init()
    }

    @After
    @CallSuper
    open fun after() {
        idlingResource.unregister()
        Intents.release()
    }

    protected fun prepareTestCases() {
        forceLogout()
        prepareCredentials()

        Espresso
            .onView(ViewMatchers.withId(R.id.btn_sign_in))
            .perform(ViewActions.click())
        idlingResource.registerAndStart()
    }

    private fun forceLogout() {
        try {
            Espresso
                .onView(ViewMatchers.withId(R.id.btn_logout))
                .perform(ViewActions.click())
        } catch (ignore: Exception) {
        }
    }

    private fun prepareCredentials() {
        Espresso
            .onView(ViewMatchers.withId(R.id.btn_show_login_ui))
            .perform(ViewActions.click())

        Espresso
            .onView(ViewMatchers.withId(R.id.texture_video_view))
            .perform(HideTextureViewAction())

        Espresso
            .onView(ViewMatchers.withId(R.id.edit_username))
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText(userName))
            .perform(ViewActions.closeSoftKeyboard())

        Espresso
            .onView(ViewMatchers.withId(R.id.edit_username))
            .check(ViewAssertions.matches(ViewMatchers.withText(userName)))
    }

    private class HideTextureViewAction : ViewAction {

        override fun getDescription(): String = HideTextureViewAction::class.java.simpleName

        override fun getConstraints(): Matcher<View> = ViewMatchers.isAssignableFrom(TextureView::class.java)

        override fun perform(uiController: UiController, view: View) {
            view.visibility = View.GONE
        }
    }
}