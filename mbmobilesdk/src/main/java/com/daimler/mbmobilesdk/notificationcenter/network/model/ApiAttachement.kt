package com.daimler.mbmobilesdk.notificationcenter.network.model

import com.google.gson.annotations.SerializedName

data class ApiAttachement(
    @SerializedName("contentType") val contentType: ContentType,
    @SerializedName("filename") val filename: String,
    @SerializedName("href") val href: String,
    @SerializedName("size") val size: Int
) {

    enum class ContentType {
        @SerializedName("IMAGE_JPEG")
        JPEG,
        @SerializedName("APPLICATION_PDF")
        PDF,
        @SerializedName("IMAGE_PNG")
        PNG,
        @SerializedName("APPLICATION_MS_WORD")
        MS_WORD
    }
}
