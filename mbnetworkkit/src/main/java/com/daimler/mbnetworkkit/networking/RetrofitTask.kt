package com.daimler.mbnetworkkit.networking

@Deprecated("Migrate to coroutines and use RequestExecutor.")
class RetrofitTask<C> : BaseRetrofitTask<C, C>() {

    override fun onHandleResponseBody(body: C?, responseCode: Int) {
        body?.let {
            complete(it)
        } ?: failEmptyBody(responseCode)
    }
}
