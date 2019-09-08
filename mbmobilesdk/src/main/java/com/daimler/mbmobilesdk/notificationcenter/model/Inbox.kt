package com.daimler.mbingresskit.notificationcenter.model

import com.daimler.mbmobilesdk.notificationcenter.model.Message

data class Inbox(val messages: List<Message>) {

    fun unreadMessages(): List<Message> = messages.filter { it.read.not() }

    fun readMessages(): List<Message> = messages.filter { it.read }
}