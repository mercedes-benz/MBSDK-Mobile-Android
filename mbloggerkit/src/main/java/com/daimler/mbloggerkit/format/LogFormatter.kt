package com.daimler.mbloggerkit.format

import com.daimler.mbloggerkit.LogMessage

interface LogFormatter {
    fun format(logMessage: LogMessage): String
}
