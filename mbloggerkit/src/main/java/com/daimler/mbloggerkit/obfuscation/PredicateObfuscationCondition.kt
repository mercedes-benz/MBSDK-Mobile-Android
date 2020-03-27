package com.daimler.mbloggerkit.obfuscation

import com.daimler.mbloggerkit.LogMessage

class PredicateObfuscationCondition(private val predicate: (LogMessage) -> Boolean) : ObfuscatingLogFormatter.LogObfuscationCondition {
    override fun shouldObfuscate(logMessage: LogMessage): Boolean = predicate.invoke(logMessage)
}
