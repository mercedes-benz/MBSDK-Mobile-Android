package com.daimler.mbmobilesdk.notificationcenter.network.model

import com.google.gson.annotations.SerializedName

data class MessageListResponse(
    @SerializedName("page") val page: ApiPage,
    @SerializedName("results") val messages: List<ApiInboxMessage>
)
