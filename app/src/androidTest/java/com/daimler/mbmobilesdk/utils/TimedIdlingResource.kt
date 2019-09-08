package com.daimler.mbmobilesdk.utils

import android.os.Handler
import android.os.Looper
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource

/**
 * Utility class that lets an instrumented test wait for a given time.
 *
 * ```
 *  @RunWith(AndroidJUnit4::class)
 *  class MyTest {
 *      private val idleResource = TimedIdlingResource("myResource")
 *
 *      @After
 *      fun after() {
 *          idleResource.unregister()
 *      }
 *
 *      @Test
 *      fun doMyTest() {
 *          // ... some navigation and actions
 *          myAsyncCommand()
 *          idleResource.registerAndStart() // the test now waits for the given time
 *          myNextCommand() // this is called after the delay
 *      }
 *  }
 * ```
 *
 * @param title the name for this idle resource
 * @param delay the amount of time the test shall be idle (in ms)
 */
class TimedIdlingResource(private val title: String, private var delay: Long = DEFAULT_DELAY_MILLIS) : IdlingResource {

    private val handler = Handler(Looper.getMainLooper())
    private var idle = false
    private var callback: IdlingResource.ResourceCallback? = null

    fun registerAndStart() = IdlingRegistry.getInstance().register(apply { start() })

    fun unregister() = IdlingRegistry.getInstance().unregister(apply { stop() })

    fun start() = start(delay)

    fun start(delay: Long) {
        idle = false
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({ idle = true }, delay)
    }

    fun stop() {
        handler.removeCallbacksAndMessages(null)
        idle = true
    }

    override fun getName(): String = title

    override fun isIdleNow(): Boolean {
        if (idle) callback?.onTransitionToIdle()
        return idle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    companion object {
        private const val DEFAULT_DELAY_MILLIS = 5000L
    }
}