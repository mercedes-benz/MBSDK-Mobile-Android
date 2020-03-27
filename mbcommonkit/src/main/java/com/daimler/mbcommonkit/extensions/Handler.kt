package com.daimler.mbcommonkit.extensions

import android.os.Handler

/**
 * Executes the given action in the given interval. Clear it if you do not need it anymore using
 * [Handler.removeCallbacksAndMessages] with null as parameter.
 *
 * ```
 *  val handler = Handler()
 *  handler.doRepeated({
 *      val current = myImageView.rotation
 *      myImageView.rotation = current + 90
 *  }, 1000)
 * ```
 *
 * @param action the action that shall be executed repeatedly
 * @param delay the delay for the action in ms
 */
inline fun Handler.doRepeated(crossinline action: () -> Unit, delay: Long) =
    postDelayed(
        runnable {
            action()
            postDelayed(this, delay)
        },
        delay
    )

/**
 * Returns a [Runnable] that executes the given operation. The returned object can be accessed
 * with `this`. As an example see the implementation of [doRepeated].
 */
inline fun runnable(crossinline action: Runnable.() -> Unit) = object : Runnable {
    override fun run() = action()
}
