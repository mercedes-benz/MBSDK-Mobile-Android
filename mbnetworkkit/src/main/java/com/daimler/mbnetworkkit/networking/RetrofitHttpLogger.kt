package com.daimler.mbnetworkkit.networking

import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbloggerkit.Priority
import okhttp3.logging.HttpLoggingInterceptor

internal class RetrofitHttpLogger(
    private val logLevel: Priority,
    private val maxChars: Int = 0
) : HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        val msg = if (maxChars > 0) {
            message.substring(0 until message.length.coerceAtMost(maxChars))
        } else {
            message
        }
        MBLoggerKit.log(logLevel, msg, null, null)
    }
}
