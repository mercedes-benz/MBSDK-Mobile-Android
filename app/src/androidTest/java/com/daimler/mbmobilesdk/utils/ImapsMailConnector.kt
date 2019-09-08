package com.daimler.mbmobilesdk.utils

abstract class ImapsMailConnector : MailConnector() {

    override fun getProtocol(): String = "imaps"
}