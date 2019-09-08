package com.daimler.mbmobilesdk.notificationcenter

import com.daimler.mbmobilesdk.notificationcenter.model.MessageDetail
import com.daimler.mbingresskit.notificationcenter.model.Inbox
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface InboxMessageService {

    /**
     * Load all available Messages from Server.
     */
    fun fetchMessages(jwtToken: String): FutureTask<Inbox, ResponseError<out RequestError>?>

    /**
     * load details of a message
     */
    fun fetchMessage(jwtToken: String, messageKey: String): FutureTask<MessageDetail, ResponseError<out RequestError>?>
}