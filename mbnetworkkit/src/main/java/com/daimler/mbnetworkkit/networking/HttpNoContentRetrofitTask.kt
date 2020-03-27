package com.daimler.mbnetworkkit.networking

import okhttp3.ResponseBody
import java.net.HttpURLConnection

/**
 * [BaseRetrofitTask] that completes on a response code of [HttpURLConnection.HTTP_OK]
 * and [HttpURLConnection.HTTP_NO_CONTENT].
 */
class HttpNoContentRetrofitTask : BaseRetrofitTask<ResponseBody, Unit>() {

    override fun onHandleResponseBody(body: ResponseBody?, responseCode: Int) {
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            complete(Unit)
        } else {
            failEmptyBody(responseCode)
        }
    }
}
