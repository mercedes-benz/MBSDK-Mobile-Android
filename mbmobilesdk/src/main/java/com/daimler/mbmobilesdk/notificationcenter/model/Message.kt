package com.daimler.mbmobilesdk.notificationcenter.model

import com.daimler.mbingresskit.notificationcenter.model.Attachment

data class Message(val key: String, val title: String, val sender: String, val content: String, val time: Long, val read: Boolean, val attachments: List<Attachment>)