package com.daimler.mbloggerkit.adapter

import com.daimler.mbloggerkit.LogMessage
import com.daimler.mbloggerkit.format.LogFormatter
import com.daimler.mbloggerkit.strategy.LogStrategy

abstract class FormattedLogAdapter(private val logFormatter: LogFormatter, protected val logStrategy: LogStrategy) : LogAdapter {

    override fun log(tag: String, logMessage: LogMessage) {
        val formattedLog = logFormatter.format(logMessage)
        logStrategy.log(logMessage.priority, tag, formattedLog)
    }
}
