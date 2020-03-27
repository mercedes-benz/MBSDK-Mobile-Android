package com.daimler.mbingresskit.testutils

import com.daimler.mbnetworkkit.task.FutureTask
import io.mockk.every
import retrofit2.Response

internal class ResponseTaskTestCase<C, R, E>(
    private val response: Response<C>,
    private val futureAction: () -> FutureTask<R, E>
) {
    var success: R? = null
        private set

    var error: E? = null
        private set

    fun finish(body: C?) {
        response.apply {
            every { isSuccessful } returns true
            every { code() } returns 200
            every { body() } returns body
        }
        keepResult(futureAction())
    }

    fun finishRaw() {
        keepResult(futureAction())
    }

    private fun keepResult(future: FutureTask<R, E>) {
        future
            .onComplete {
                success = it
            }.onFailure {
                error = it
            }
    }
}
