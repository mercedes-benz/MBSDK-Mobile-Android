package com.daimler.mbloggerkit.format

import com.daimler.mbloggerkit.LogMessage

internal class SimpleLogFormatter : LogFormatter {
    override fun format(logMessage: LogMessage): String = "${logMessage.message}${formattedMessageOrEmpty(logMessage.throwable)}"

    private fun formattedMessageOrEmpty(throwable: Throwable?): String = throwable?.let { "\nError: ${it.message}" } ?: ""
}
