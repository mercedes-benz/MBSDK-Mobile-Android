package com.daimler.mbloggerkit.obfuscation

import com.daimler.mbloggerkit.LogMessage

class Obfuscation(
    private val obfuscationCondition: ObfuscatingLogFormatter.LogObfuscationCondition,
    private val logObfuscator: ObfuscatingLogFormatter.LogObfuscator
) {
    fun shouldObfuscate(logMessage: LogMessage): Boolean = obfuscationCondition.shouldObfuscate(logMessage)
    fun obfuscate(logMessage: LogMessage): LogMessage = logMessage.copy(message = logObfuscator.obfuscate(logMessage.message))
}
