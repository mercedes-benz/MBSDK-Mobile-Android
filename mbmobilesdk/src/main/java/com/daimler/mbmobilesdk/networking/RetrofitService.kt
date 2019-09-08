package com.daimler.mbmobilesdk.networking

import android.content.Context
import android.net.ConnectivityManager
import com.daimler.mbmobilesdk.BuildConfig
import com.daimler.mbmobilesdk.business.NotificationsService
import com.daimler.mbmobilesdk.business.model.PushDistributionProfile
import com.daimler.mbmobilesdk.networking.model.ApiDistributionProfile
import com.daimler.mbmobilesdk.networking.model.ApiNotificationsRequest
import com.daimler.mbmobilesdk.notificationcenter.InboxMessageService
import com.daimler.mbmobilesdk.notificationcenter.model.MessageDetail
import com.daimler.mbmobilesdk.notificationcenter.network.model.ApiAttachement
import com.daimler.mbmobilesdk.notificationcenter.network.model.MessageListResponse
import com.daimler.mbloggerkit.Priority
import com.daimler.mbingresskit.notificationcenter.model.Attachment
import com.daimler.mbingresskit.notificationcenter.model.Inbox
import com.daimler.mbmobilesdk.notificationcenter.model.Message
import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.mbnetworkkit.networking.*
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

internal class RetrofitService(
    context: Context,
    private val baseUrl: String,
    private val inboxUrl: String,
    private val deviceId: String,
    private val pushDistributionProfile: PushDistributionProfile,
    private val sessionId: String,
    private val headerService: HeaderService
) : NotificationsService, InboxMessageService {

    companion object {
        const val INBOX_MESSAGE_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    }

    private val notificationsApi: NotificationsApi by lazy {
        val loggingInterceptor = createHttpLoggingInterceptor(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.HEADERS,
            Priority.INFO
        )

        return@lazy Retrofit.Builder()
            .client(OkHttpClient.Builder()
                .addInterceptor(ConnectivityInterceptor(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager))
                .addInterceptor(headerService.createRisHeaderInterceptor())
                .addInterceptor(loggingInterceptor)
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(NotificationsApi::class.java)
    }

    private val inboxApi: InboxApi by lazy {
        val loggingInterceptor = createHttpLoggingInterceptor(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.HEADERS,
            Priority.INFO
        )
        return@lazy Retrofit.Builder()
            .client(OkHttpClient.Builder()
                .addInterceptor(ConnectivityInterceptor(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager))
                .addInterceptor(loggingInterceptor)
                .build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(inboxUrl)
            .build()
            .create(InboxApi::class.java)
    }

    /*
    NotificationsApi
     */

    override fun registerForNotifications(token: String, deviceToken: String): FutureTask<Unit, ResponseError<out RequestError>?> {
        val request = ApiNotificationsRequest(
            mapDistributionProfileToApiProfile(pushDistributionProfile), deviceId, deviceToken
        )
        val call = notificationsApi.register(
            token,
            sessionId,
            newTrackingId(),
            NotificationsApi.OS_NAME,
            request
        )
        val task = TaskObject<Unit, ResponseError<out RequestError>?>()
        val callback = RetrofitTask<ResponseBody>()
        callback.futureTask()
            .onComplete { task.complete(Unit) }
            .onFailure { task.fail(defaultErrorMapping(it)) }
        call.enqueue(callback)
        return task.futureTask()
    }

    override fun unregisterFromNotifications(token: String): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = TaskObject<Unit, ResponseError<out RequestError>?>()
        val call = notificationsApi.unregister(
            token,
            sessionId,
            newTrackingId(),
            NotificationsApi.OS_NAME,
            deviceId
        )
        val callback = RetrofitTask<ResponseBody>()
        callback.futureTask()
            .onComplete { task.complete(Unit) }
            .onFailure { task.fail(defaultErrorMapping(it)) }
        call.enqueue(callback)
        return task.futureTask()
    }

    override fun fetchMessages(jwtToken: String): FutureTask<Inbox, ResponseError<out RequestError>?> {
        val task = TaskObject<Inbox, ResponseError<out RequestError>?>()
        val call = inboxApi.list(
            "",
            newTrackingId(),
            ""
        )
        val callback = RetrofitTask<MessageListResponse>()
        callback.futureTask()
            .onComplete {
                try {
                    task.complete(mapApiInbox(it))
                } catch (iae: IllegalArgumentException) {
                    task.fail(defaultErrorMapping(iae))
                }
            }.onFailure {
                task.fail(defaultErrorMapping(it))
            }
        call.enqueue(callback)
        return task.futureTask()
    }

    override fun fetchMessage(jwtToken: String, messageKey: String): FutureTask<MessageDetail, ResponseError<out RequestError>?> {
        val task = TaskObject<MessageDetail, ResponseError<out RequestError>?>()
        val call = inboxApi.detail(
            "",
            newTrackingId(),
            "",
            messageKey)
        val callback = RetrofitTask<String>()
        callback.futureTask()
            .onComplete {
                task.complete(MessageDetail(messageKey, "", it))
            }.onFailure {
                task.fail(defaultErrorMapping(it))
            }
        call.enqueue(callback)
        return task.futureTask()
    }

    /*
    Mapping
     */

    private fun mapDistributionProfileToApiProfile(profile: PushDistributionProfile) =
        when (profile) {
            PushDistributionProfile.DEV -> ApiDistributionProfile.DEVELOPMENT
            PushDistributionProfile.AD_HOC -> ApiDistributionProfile.AD_HOC
            PushDistributionProfile.STORE -> ApiDistributionProfile.STORE
        }

    private fun mapApiInbox(apiMessagesResponse: MessageListResponse): Inbox {
        return Inbox(apiMessagesResponse.messages.map { message ->
            Message(
                message.key,
                message.subject,
                "",
                message.description,
                mapInboxTime(message.createdAt),
                false,
                message.attachements?.map {
                    Attachment(it.filename, it.href, mapAttachementType(it.contentType))
                } ?: emptyList())
        })
    }

    private fun mapInboxTime(formattedTime: String): Long {
        val dateFormat = SimpleDateFormat(INBOX_MESSAGE_DATE_PATTERN, Locale.getDefault())
        return dateFormat.parse(formattedTime).time
    }

    private fun mapAttachementType(attachementType: ApiAttachement.ContentType): Attachment.Type {
        return when (attachementType) {
            ApiAttachement.ContentType.JPEG -> Attachment.Type.IMAGE
            ApiAttachement.ContentType.PNG -> Attachment.Type.IMAGE
            ApiAttachement.ContentType.PDF -> Attachment.Type.PDF
            ApiAttachement.ContentType.MS_WORD -> Attachment.Type.DOC
        }
    }

    private fun newTrackingId() = UUID.randomUUID().toString()
}