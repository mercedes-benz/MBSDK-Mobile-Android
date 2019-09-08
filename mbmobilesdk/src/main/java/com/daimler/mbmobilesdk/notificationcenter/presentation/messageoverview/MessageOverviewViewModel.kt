package com.daimler.mbmobilesdk.notificationcenter.presentation.messageoverview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.notificationcenter.model.Message
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbuikit.lifecycle.events.LiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent

class MessageOverviewViewModel(app: Application) : AndroidViewModel(app) {

    private val inboxData = MutableLiveData<InboxResult>()

    private val messageSelected = MutableLiveEvent<Message>()

    val loading = MutableLiveData<Boolean>()

    private val refreshing = MutableLiveData<Boolean>()

    init {
        loadItems()
    }

    private fun loadItems() {
        loading.postValue(true)
        loadItemsWithTokenCheck()
    }

    fun inboxData(): LiveData<InboxResult> = inboxData

    fun messageSelected(): LiveData<LiveEvent<Message>> = messageSelected

    fun refreshing(): LiveData<Boolean> = refreshing

    fun refreshMessages() {
        if (shouldUpdate()) {
            refreshing.postValue(true)
            loadItemsWithTokenCheck()
        }
    }

    private fun loadItemsWithTokenCheck() {
    }

    private fun shouldUpdate(): Boolean {
        return (loading.value == false && refreshing.value == false)
    }

    private fun mapMessageItems(messages: List<Message>): List<InboxMessageItem> {
        return messages.map { InboxMessageItem(it, messageSelected) }
    }

    sealed class InboxResult {
        class Success(val messages: List<InboxMessageItem>) : InboxResult()
        class Error(val cause: ResponseError<out RequestError>?) : InboxResult()
    }
}