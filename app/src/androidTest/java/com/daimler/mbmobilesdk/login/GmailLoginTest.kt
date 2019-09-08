package com.daimler.mbmobilesdk.login

import androidx.test.runner.AndroidJUnit4
import com.daimler.mbmobilesdk.utils.GmailConnector
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern

@RunWith(AndroidJUnit4::class)
class GmailLoginTest : GenericLoginTest() {

    override val userName = MAIL

    @Test
    override fun testLogin() {
        super.testLogin()
    }

    override fun fetchPin(): String {
        idlingResource.start(MAIL_IDLE_DELAY_MILLIS)
        val connector = GmailConnector()
        println("Attempting to connect to IMAP server")
        connector.connect(MAIL, PW, PORT)
        println("Connected")

        val mails = connector.getMessages(MAIL_MESSAGES_AMOUNT) {
            it.subject.contains(MAIL_SUBJECT)
        }
        println("Received messages")
        val messages = mails.asReversed()

        val pattern = Pattern.compile("\\d{6}")
        val first = messages.first()
        val matcher = pattern.matcher(first.content.toString())

        connector.disconnect()

        idlingResource.stop()

        if (matcher.find()) {
            return matcher.group()
        } else {
            throw RuntimeException("Could not find PIN.")
        }
    }

    companion object {

        private const val MAIL = "appsfactory.daimler@gmail.com"
        private const val PW = "afDaimler2018"
        private const val PORT = 993

        private const val MAIL_MESSAGES_AMOUNT = 10
        private const val MAIL_IDLE_DELAY_MILLIS = 20 * 1000L
        private const val MAIL_SUBJECT = "RisingStars Login PIN"
    }
}