package com.daimler.mbmobilesdk.notificationcenter.network.model

import com.google.gson.annotations.SerializedName

data class ApiPage(
    @SerializedName("number") val number: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("totalElements") val totalElements: Int,
    @SerializedName("totalPages") val totalPages: Int
)