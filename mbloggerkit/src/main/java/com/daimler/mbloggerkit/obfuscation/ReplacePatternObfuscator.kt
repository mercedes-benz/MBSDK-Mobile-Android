package com.daimler.mbloggerkit.obfuscation

internal class ReplacePatternObfuscator(
    private val regex: String,
    private val replacement: String
) : ObfuscatingLogFormatter.LogObfuscator {
    override fun obfuscate(message: String) = message.replace(Regex(regex), replacement)
}
