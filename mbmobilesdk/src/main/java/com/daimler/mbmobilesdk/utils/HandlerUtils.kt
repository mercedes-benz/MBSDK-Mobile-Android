package com.daimler.mbmobilesdk.utils

import android.os.Handler
import android.os.Looper

fun postToMainThread(runnable: Runnable) = post(runnable, Looper.getMainLooper())

fun postToMainThread(action: () -> Unit) = post(action, Looper.getMainLooper())

fun postToMainThread(runnable: Runnable, delay: Long) =
    postDelayed(runnable, delay, Looper.getMainLooper())

fun postToMainThread(action: () -> Unit, delay: Long) =
    postDelayed(action, delay, Looper.getMainLooper())

fun post(action: () -> Unit, looper: Looper? = null) = handler(looper).post(action)

fun post(runnable: Runnable, looper: Looper? = null) = handler(looper).post(runnable)

fun postDelayed(action: () -> Unit, delay: Long, looper: Looper? = null) =
    handler(looper).postDelayed(action, delay)

fun postDelayed(runnable: Runnable, delay: Long, looper: Looper? = null) =
    handler(looper).postDelayed(runnable, delay)

private fun handler(looper: Looper?) = looper?.let { Handler(it) } ?: Handler()