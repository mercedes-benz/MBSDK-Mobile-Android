package com.daimler.mbnetworkkit

import com.daimler.mbnetworkkit.networking.HttpNoContentRetrofitTask
import com.daimler.mbnetworkkit.utils.dummyRequest
import com.daimler.mbnetworkkit.utils.dummyResponse
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class HttpNoContentRetrofitTaskTest {

    @Test
    fun testResponsesSuccessIf200or204() {
        (200..500).forEach {
            testResponse(it, ACCEPTED_RESPONSE_CODES.contains(it))
        }
    }

    private fun testResponse(code: Int, shouldBeSuccessful: Boolean) {
        val subject = HttpNoContentRetrofitTask()
        var success: Boolean? = null
        subject
            .onComplete {
                success = true
            }.onFailure {
                success = false
            }
        subject.onResponse(EmptyCall(), response(code))
        Assert.assertEquals(shouldBeSuccessful, success)
    }

    private fun response(code: Int) =
        with(dummyResponse(dummyRequest(), code)) {
            if (isSuccessful) {
                Response.success<ResponseBody>(null, this)
            } else {
                Response.error(ResponseBody.create(null, ""), this)
            }
        }

    private class EmptyCall : Call<ResponseBody> {

        override fun enqueue(callback: Callback<ResponseBody>) = Unit

        override fun isExecuted(): Boolean = false

        override fun clone(): Call<ResponseBody> = this

        override fun isCanceled(): Boolean = false

        override fun cancel() = Unit

        override fun execute(): Response<ResponseBody> = Response.success(null)

        override fun request(): Request = dummyRequest()

        override fun timeout(): Timeout = Timeout()
    }

    private companion object {

        private val ACCEPTED_RESPONSE_CODES = listOf(
            HttpURLConnection.HTTP_OK,
            HttpURLConnection.HTTP_NO_CONTENT
        )
    }
}
