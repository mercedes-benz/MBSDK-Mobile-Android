package com.daimler.mbloggerkit.obfuscation

internal class ReplaceMessageObfuscator(private val replacement: String = "") : ObfuscatingLogFormatter.LogObfuscator {
    override fun obfuscate(message: String) = replacement
}
