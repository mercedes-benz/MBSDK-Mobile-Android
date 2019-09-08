package com.daimler.mbmobilesdk.notificationcenter.network.model

import com.google.gson.annotations.SerializedName

data class ApiInboxMessage(
    @SerializedName("attachements") val attachements: List<ApiAttachement>?,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("description") val description: String,
    @SerializedName("key") val key: String,
    @SerializedName("subject") val subject: String
)
