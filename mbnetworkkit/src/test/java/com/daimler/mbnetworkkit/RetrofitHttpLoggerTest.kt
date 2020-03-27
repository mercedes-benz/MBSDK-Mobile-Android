package com.daimler.mbnetworkkit

import com.daimler.mbloggerkit.LogMessage
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbloggerkit.PrinterConfig
import com.daimler.mbloggerkit.Priority
import com.daimler.mbloggerkit.adapter.LogAdapter
import com.daimler.mbnetworkkit.networking.RetrofitHttpLogger
import org.junit.Assert.assertEquals
import org.junit.Test

class RetrofitHttpLoggerTest {

    @Test
    fun testRetrofitHttpLogger() {
        var currentLog = ""
        val adapter = object : LogAdapter {
            override val isLoggable: Boolean
                get() = true

            override fun log(tag: String, logMessage: LogMessage) {
                currentLog = logMessage.message
            }
        }
        MBLoggerKit.usePrinterConfig(
            PrinterConfig.Builder()
                .addAdapter(adapter)
                .build()
        )
        val logger = RetrofitHttpLogger(Priority.DEBUG, 500)
        var shortString = ""
        repeat(50) { shortString += "a" }
        logger.log(shortString)
        assertEquals(50, currentLog.length)

        currentLog = ""
        var longString = ""
        repeat(1000) { longString += "a" }
        logger.log(longString)
        assertEquals(500, currentLog.length)
    }
}
