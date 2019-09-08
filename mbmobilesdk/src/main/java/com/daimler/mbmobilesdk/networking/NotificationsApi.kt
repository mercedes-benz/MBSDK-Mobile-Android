package com.daimler.mbmobilesdk.networking

import com.daimler.mbmobilesdk.networking.model.ApiNotificationsRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

internal interface NotificationsApi {

    companion object {

        private const val PATH_VERSION = "/v1"
        private const val PATH_NOTIFICATIONS = "/notifications"
        private const val PATH_REGISTRATIONS = "/registrations"
        private const val PATH_PARAM_DEVICE_ID = "deviceId"

        private const val HEADER_SESSION_ID = "X-SessionId"
        private const val HEADER_TRACKING_ID = "X-TrackingId"
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val HEADER_OS_NAME = "X-OperatingSystem"

        const val OS_NAME = "android"
    }

    @POST("$PATH_VERSION$PATH_NOTIFICATIONS$PATH_REGISTRATIONS")
    fun register(
        @Header(HEADER_AUTHORIZATION) token: String,
        @Header(HEADER_SESSION_ID) sessionId: String,
        @Header(HEADER_TRACKING_ID) trackingId: String,
        @Header(HEADER_OS_NAME) osName: String,
        @Body body: ApiNotificationsRequest
    ): Call<ResponseBody>

    @DELETE("$PATH_VERSION$PATH_NOTIFICATIONS$PATH_REGISTRATIONS/{$PATH_PARAM_DEVICE_ID}")
    fun unregister(
        @Header(HEADER_AUTHORIZATION) token: String,
        @Header(HEADER_SESSION_ID) sessionId: String,
        @Header(HEADER_TRACKING_ID) trackingId: String,
        @Header(HEADER_OS_NAME) osName: String,
        @Path(PATH_PARAM_DEVICE_ID) deviceId: String
    ): Call<ResponseBody>
}