package com.daimler.mbprotokit

object MessageParsers {

    fun createUserMessageParser(): UserMessageParser = UserMessageParserImpl()
}
