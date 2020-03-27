package com.daimler.mbnetworkkit

import com.daimler.mbnetworkkit.utils.dummyRequest
import com.daimler.mbnetworkkit.utils.dummyResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import okio.Timeout

class DummyCall : Call {

    private var request: Request = dummyRequest()

    override fun enqueue(responseCallback: Callback) = Unit

    override fun isExecuted(): Boolean = false

    override fun clone(): Call = this

    override fun isCanceled(): Boolean = false

    override fun cancel() = Unit

    override fun request(): Request = request

    override fun execute(): Response = dummyResponse(request())

    override fun timeout(): Timeout = Timeout()
}
