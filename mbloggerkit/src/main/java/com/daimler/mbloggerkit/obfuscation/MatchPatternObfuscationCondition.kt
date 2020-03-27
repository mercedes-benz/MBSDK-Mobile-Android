package com.daimler.mbloggerkit.obfuscation

import com.daimler.mbloggerkit.LogMessage
import java.util.regex.Pattern

internal class MatchPatternObfuscationCondition(private val regex: String) : ObfuscatingLogFormatter.LogObfuscationCondition {

    override fun shouldObfuscate(logMessage: LogMessage): Boolean = matches(logMessage.message, Pattern.compile(regex))

    private fun matches(message: String, pattern: Pattern): Boolean = pattern.matcher(message).matches()
}
