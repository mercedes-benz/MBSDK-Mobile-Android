package com.daimler.mbmobilesdk.notificationcenter.presentation.messageoverview

import android.text.format.DateFormat
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.notificationcenter.model.Message
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import java.util.*

class InboxMessageItem(val message: Message, private val selected: MutableLiveEvent<Message>) : MBBaseRecyclerItem() {

    companion object {
        const val FORMATTED_TIME_PATTERN = "HH:mm"
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_inbox_message
    }

    override fun getModelId(): Int {
        return BR.item
    }

    fun onSelect() {
        selected.sendEvent(message)
    }

    fun sender(): String {
        return message.sender
    }

    fun content(): String {
        return message.content
    }

    fun title(): String {
        return message.title
    }

    fun read(): Boolean {
        return message.read
    }

    fun time(): String {
        val timeInCalendar = Calendar.getInstance(Locale.getDefault()).apply {
            timeInMillis = message.time
        }
        return DateFormat.format(FORMATTED_TIME_PATTERN, timeInCalendar).toString()
    }
}