package com.daimler.mbmobilesdk.notificationcenter.presentation

import com.daimler.mbmobilesdk.notificationcenter.model.Message

internal interface Navigation {

    fun onClose()

    fun onShowMessage(message: Message)
}