package com.daimler.mbloggerkit.adapter

import com.daimler.mbloggerkit.format.LogFormatter
import com.daimler.mbloggerkit.format.SimpleLogFormatter
import com.daimler.mbloggerkit.strategy.LogCatLogging

class AndroidLogAdapter private constructor(
    logFormatter: LogFormatter,
    loggingEnabled: Boolean = false
) : FormattedLogAdapter(logFormatter, LogCatLogging()) {

    override val isLoggable: Boolean = loggingEnabled

    class Builder {
        private var loggingEnabled = false
        private var logFormatter: LogFormatter = SimpleLogFormatter()

        fun setLoggingEnabled(enabled: Boolean) = apply {
            loggingEnabled = enabled
        }

        fun logFormatter(logFormatter: LogFormatter) = apply {
            this.logFormatter = logFormatter
        }

        fun build(): AndroidLogAdapter = AndroidLogAdapter(logFormatter, loggingEnabled = loggingEnabled)
    }
}
