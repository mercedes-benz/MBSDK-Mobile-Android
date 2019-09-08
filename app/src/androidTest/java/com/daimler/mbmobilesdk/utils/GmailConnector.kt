package com.daimler.mbmobilesdk.utils

class GmailConnector : ImapsMailConnector() {

    override fun getHostName(): String = "imap.gmail.com"
}