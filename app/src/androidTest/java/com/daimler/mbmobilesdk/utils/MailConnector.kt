package com.daimler.mbmobilesdk.utils

import java.util.*
import javax.mail.Folder
import javax.mail.Message
import javax.mail.Session
import javax.mail.Store

abstract class MailConnector {

    private lateinit var store: Store
    private lateinit var folder: Folder

    protected abstract fun getHostName(): String

    protected abstract fun getProtocol(): String

    protected open fun getProperties(): Properties = Properties()

    fun connect(user: String, password: String, port: Int) {
        val session = Session.getDefaultInstance(getProperties())
        store = session.getStore(getProtocol())
        store.connect(getHostName(), port, user, password)
    }

    fun disconnect() {
        closeFolder()
        closeStore()
    }

    fun getMessages(amount: Int, filter: (Message) -> Boolean): List<Message> {
        if (::folder.isInitialized && folder.isOpen) folder.close()
        folder = store.getFolder("INBOX")
        folder.open(Folder.READ_ONLY)
        val count = folder.messageCount
        val messages = if (count < amount) {
            folder.getMessages(1, count)
        } else {
            folder.getMessages(count - amount, count)
        }
        return messages.filter(filter)
    }

    private fun closeFolder() {
        if (::folder.isInitialized && folder.isOpen) folder.close()
    }

    private fun closeStore() {
        if (::store.isInitialized && store.isConnected) store.close()
    }
}