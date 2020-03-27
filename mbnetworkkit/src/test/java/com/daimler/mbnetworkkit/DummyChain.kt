package com.daimler.mbnetworkkit

import com.daimler.mbnetworkkit.utils.dummyRequest
import com.daimler.mbnetworkkit.utils.dummyResponse
import okhttp3.Request
import okhttp3.Response

class DummyChain : BaseTestChain() {

    override var request: Request = dummyRequest()

    override fun createResponse(request: Request): Response = dummyResponse(request)
}
