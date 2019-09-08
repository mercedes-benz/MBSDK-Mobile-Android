package com.daimler.mbmobilesdk.notificationcenter.presentation

import com.daimler.mbmobilesdk.notificationcenter.model.Message

sealed class ReachMeScreen {

    class Messages(val wasBackNavigation: Boolean) : ReachMeScreen()

    class MessageDetails(val message: Message) : ReachMeScreen()
}