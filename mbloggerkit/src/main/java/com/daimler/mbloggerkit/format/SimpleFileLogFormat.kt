package com.daimler.mbloggerkit.format

import com.daimler.mbloggerkit.LogMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SimpleFileLogFormat : LogFormatter {

    private val dateFormatter = SimpleDateFormat(DATE_TIME_PATTERN, Locale.ENGLISH)

    override fun format(logMessage: LogMessage): String =
        "${dateFormatter.format(Date(logMessage.timestamp))} | ${logMessage.priority.name} | ${logMessage.message}${errorLogInfoIfAvailable(logMessage.throwable)}"

    private fun errorLogInfoIfAvailable(throwable: Throwable?): String = throwable?.let {
        " | ${it.message}"
    } ?: ""

    companion object {
        private const val DATE_TIME_PATTERN = "yy-MM-dd HH:mm:ss.SSS"
    }
}
