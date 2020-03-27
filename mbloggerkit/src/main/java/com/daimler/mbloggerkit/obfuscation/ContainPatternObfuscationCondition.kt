package com.daimler.mbloggerkit.obfuscation

import com.daimler.mbloggerkit.LogMessage

internal class ContainPatternObfuscationCondition(private val regex: String) : ObfuscatingLogFormatter.LogObfuscationCondition {
    override fun shouldObfuscate(logMessage: LogMessage): Boolean = logMessage.message.contains(Regex(pattern = regex))
}
