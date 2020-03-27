package com.daimler.mbloggerkit.obfuscation

class Obfuscations {
    companion object {

        fun patternObfuscation(regex: String, replacement: String) = Obfuscation(ContainPatternObfuscationCondition(regex), ReplacePatternObfuscator(regex, replacement))

        fun sequenceObfuscation(sequence: CharSequence, replacement: String) = Obfuscation(ContainSequenceObfuscationCondition(sequence), ReplaceSequenceObfuscator(sequence.toString(), replacement))

        fun messageObfuscation(condition: ObfuscatingLogFormatter.LogObfuscationCondition, replacement: String = "") = Obfuscation(condition, ReplaceMessageObfuscator(replacement))
    }
}
