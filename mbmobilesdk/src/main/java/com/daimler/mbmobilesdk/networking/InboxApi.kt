package com.daimler.mbmobilesdk.networking

import com.daimler.mbmobilesdk.notificationcenter.network.model.MessageListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface InboxApi {

    companion object {
        const val PATH_VERSION = "v1/"
        const val PATH_CONSUMER = "consumer/"
        const val PATH_MESSAGES = "messages/"
        const val PATH_RENDER = "render"

        const val HEADER_AUTHORIZATION = "X-Api-Key"
        const val HEADER_TRACKING_ID = "Tracking-Id"
        const val HEADER_CIAM_ID = "Ciam-Id"
        const val HEADER_PAGE = "pageNumber"
        const val HEADER_SIZE = "pageSize"

        const val PARAM_MESSAGE_KEY = "messageKey"

        const val DEFAULT_PAGE = 1
        const val DEFAULT_SIZE = 10
    }

    @GET("$PATH_VERSION$PATH_CONSUMER$PATH_MESSAGES{$PARAM_MESSAGE_KEY}/$PATH_RENDER")
    fun detail(
        @Header(HEADER_AUTHORIZATION) apiKey: String,
        @Header(HEADER_TRACKING_ID) trackingId: String,
        @Header(HEADER_CIAM_ID) ciamId: String,
        @Path(PARAM_MESSAGE_KEY) messageKey: String
    ): Call<String>

    @GET("$PATH_VERSION$PATH_CONSUMER$PATH_MESSAGES")
    fun list(
        @Header(HEADER_AUTHORIZATION) apiKey: String,
        @Header(HEADER_TRACKING_ID) trackingId: String,
        @Header(HEADER_CIAM_ID) ciamId: String,
        @Header(HEADER_PAGE) page: Int = DEFAULT_PAGE,
        @Header(HEADER_SIZE) size: Int = DEFAULT_SIZE
    ): Call<MessageListResponse>
}