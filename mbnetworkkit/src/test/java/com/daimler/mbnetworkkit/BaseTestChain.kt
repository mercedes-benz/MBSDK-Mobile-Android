package com.daimler.mbnetworkkit

import okhttp3.Call
import okhttp3.Connection
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

abstract class BaseTestChain : Interceptor.Chain {

    protected abstract var request: Request

    protected abstract fun createResponse(request: Request): Response

    override fun writeTimeoutMillis(): Int = 0

    override fun call(): Call = DummyCall()

    override fun proceed(request: Request): Response {
        this.request = request
        return createResponse(request)
    }

    override fun withWriteTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain = this

    override fun connectTimeoutMillis(): Int = 0

    override fun connection(): Connection? = null

    override fun withConnectTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain = this

    override fun withReadTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain = this

    override fun request(): Request = request

    override fun readTimeoutMillis(): Int = 0
}
