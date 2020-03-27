package com.daimler.mbloggerkit.obfuscation

internal class ReplaceSequenceObfuscator(
    private val oldValue: String,
    private val newValue: String
) : ObfuscatingLogFormatter.LogObfuscator {
    override fun obfuscate(message: String) = message.replace(oldValue, newValue)
}
