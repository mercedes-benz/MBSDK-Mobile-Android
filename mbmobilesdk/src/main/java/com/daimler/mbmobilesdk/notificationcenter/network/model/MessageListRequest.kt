package com.daimler.mbmobilesdk.notificationcenter.network.model

data class MessageListRequest(val jwtToken: String, val apiKey: String, val pageNumber: Int, val pageSize: Int)