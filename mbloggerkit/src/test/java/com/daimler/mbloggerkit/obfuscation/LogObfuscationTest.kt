package com.daimler.mbloggerkit.obfuscation

import com.daimler.mbloggerkit.LogMessage
import com.daimler.mbloggerkit.Priority
import org.junit.jupiter.api.Test

class LogObfuscationTest {

    @Test
    fun containsMailPattern() {
        val mailRegex = "^(.+)@(.+)$"
        val testMail = "test.test@test.de"
        val obfuscation = ContainPatternObfuscationCondition(mailRegex)
        assert(obfuscation.shouldObfuscate(simpleTestLogMessage(testMail, Priority.DEBUG)))
    }

    @Test
    fun containsNoMailPattern() {
        val mailRegex = "^(.+)@(.+)$"
        val testMail = "aösldkfj"
        val obfuscation = ContainPatternObfuscationCondition(mailRegex)
        assert(obfuscation.shouldObfuscate(simpleTestLogMessage(testMail, Priority.DEBUG)).not())
    }

    @Test
    fun matchPattern() {
        val mailRegex = "^(.+)@(.+)$"
        val testMail = "test.test@test.de"
        val obfuscation = MatchPatternObfuscationCondition(mailRegex)
        assert(obfuscation.shouldObfuscate(simpleTestLogMessage(testMail, Priority.DEBUG)))
    }

    @Test
    fun notMatchPattern() {
        val mailRegex = "^(.+)@(.+)$"
        val testMail = "aösld.dlaks"
        val obfuscation = MatchPatternObfuscationCondition(mailRegex)
        assert(obfuscation.shouldObfuscate(simpleTestLogMessage(testMail, Priority.DEBUG)).not())
    }

    @Test
    fun containSameSequence() {
        val sequence = "asdfjklö"
        val obfuscation = ContainSequenceObfuscationCondition(sequence)
        assert(obfuscation.shouldObfuscate(simpleTestLogMessage(sequence, Priority.DEBUG)))
    }

    @Test
    fun containPartOfSequence() {
        val sequence = "asdf"
        val obfuscation = ContainSequenceObfuscationCondition(sequence)
        assert(obfuscation.shouldObfuscate(simpleTestLogMessage("${sequence}jilö", Priority.DEBUG)))
    }

    @Test
    fun containNoSequence() {
        val sequence = "asdf"
        val obfuscation = ContainSequenceObfuscationCondition(sequence)
        assert(obfuscation.shouldObfuscate(simpleTestLogMessage("asd", Priority.DEBUG)).not())
    }

    @Test
    fun equalSequence() {
        val sequence = "asdf"
        val obfuscation = EqualSequenceObfuscationCondition(sequence)
        assert(obfuscation.shouldObfuscate(simpleTestLogMessage(sequence, Priority.DEBUG)))
    }

    @Test
    fun notEqualSequence() {
        val sequence = "asdf"
        val obfuscation = EqualSequenceObfuscationCondition(sequence)
        assert(obfuscation.shouldObfuscate(simpleTestLogMessage("${sequence}jklö", Priority.DEBUG)).not())
    }

    @Test
    fun replaceMessageWithEmptyString() {
        val obfuscator = ReplaceMessageObfuscator()
        val obfuscatedmessage = obfuscator.obfuscate("TEST")
        assert(obfuscatedmessage.isEmpty())
    }

    @Test
    fun replaceMessageWithContent() {
        val secretContent = "Confidential"
        val obfuscator = ReplaceMessageObfuscator(secretContent)
        val obfuscatedMessage = obfuscator.obfuscate("TEST")
        assert(secretContent == obfuscatedMessage)
    }

    @Test
    fun replaceSequenceWithContent() {
        val secretContent = "PASSWORD"
        val changedContent = " * SECRET * "
        val obfuscator = ReplaceSequenceObfuscator(secretContent, changedContent)
        val content = "asdf${secretContent}jklö"
        val obfuscatedMessage = obfuscator.obfuscate(content)
        assert(obfuscatedMessage == "asdf${changedContent}jklö")
    }

    @Test
    fun replacePatternWithContent() {
        val pattern = "^(.+)@(.+)$"
        val changedContent = " * "
        val obfuscator = ReplacePatternObfuscator(pattern, changedContent)
        assert(obfuscator.obfuscate("test.test@test.de") == changedContent)
    }

    private fun simpleTestLogMessage(messge: String, priority: Priority, throwable: Throwable? = null): LogMessage {
        return LogMessage(System.currentTimeMillis(), priority, messge, throwable)
    }
}
