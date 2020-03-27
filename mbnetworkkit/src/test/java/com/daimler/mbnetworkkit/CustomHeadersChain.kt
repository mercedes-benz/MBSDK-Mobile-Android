package com.daimler.mbnetworkkit

import com.daimler.mbnetworkkit.utils.dummyRequest
import com.daimler.mbnetworkkit.utils.dummyResponse
import okhttp3.Request
import okhttp3.Response

class CustomHeadersChain(
    headers: Map<String, String>
) : BaseTestChain() {

    override var request: Request = dummyRequest()
        .newBuilder()
        .apply {
            headers.forEach {
                header(it.key, it.value)
            }
        }.build()

    override fun createResponse(request: Request): Response =
        dummyResponse(request)
}
