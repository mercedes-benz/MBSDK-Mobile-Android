package com.daimler.mbloggerkit.obfuscation

import com.daimler.mbloggerkit.LogMessage

internal class EqualSequenceObfuscationCondition(private val sequence: CharSequence) : ObfuscatingLogFormatter
    .LogObfuscationCondition {
    override fun shouldObfuscate(logMessage: LogMessage): Boolean = logMessage.message.contentEquals(sequence)
}
