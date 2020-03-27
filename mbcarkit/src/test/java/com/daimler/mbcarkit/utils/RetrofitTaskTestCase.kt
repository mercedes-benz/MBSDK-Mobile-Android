package com.daimler.mbcarkit.utils

import com.daimler.mbnetworkkit.task.FutureTask
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class RetrofitTaskTestCase<C, R, E>(
    private val call: Call<C>,
    private val futureAction: () -> FutureTask<R, E>
) {
    private val slot = CapturingSlot<Callback<C>>()

    var success: R? = null
        private set

    var error: E? = null
        private set

    init {
        every { call.enqueue(capture(slot)) } just runs
    }

    fun finishSuccess(data: C) {
        finishSuccessWithResponse(Response.success(data))
    }

    fun finishSuccessWithResponse(response: Response<C>) {
        keepResult(futureAction())
        slot.captured.onResponse(call, response)
        slot.clear()
    }

    fun finishError(error: Throwable) {
        keepResult(futureAction())
        slot.captured.onFailure(call, error)
        slot.clear()
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
