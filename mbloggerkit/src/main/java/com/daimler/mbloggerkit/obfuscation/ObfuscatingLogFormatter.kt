package com.daimler.mbloggerkit.obfuscation

import com.daimler.mbloggerkit.LogMessage
import com.daimler.mbloggerkit.format.LogFormatter

class ObfuscatingLogFormatter(private val logFormatter: LogFormatter, private vararg val obfuscations: Obfuscation) : LogFormatter {

    override fun format(logMessage: LogMessage): String {
        return obfuscations.firstOrNull { it.shouldObfuscate(logMessage) }
            ?.let {
                logFormatter.format(it.obfuscate(logMessage))
            } ?: logFormatter.format(logMessage)
    }

    interface LogObfuscator {

        /**
         * Should return the obfuscated message. This can be a replaced string or just an empty one.
         */
        fun obfuscate(message: String): String
    }

    interface LogObfuscationCondition {
        /**
         * Should return true if the [logMessage] should be obfuscated
         */
        fun shouldObfuscate(logMessage: LogMessage): Boolean
    }
}
