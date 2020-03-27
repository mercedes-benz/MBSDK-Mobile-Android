package com.daimler.mbingresskit.implementation.network.service

import com.daimler.mbingresskit.common.VerificationConfirmation
import com.daimler.mbingresskit.common.VerificationTransaction
import com.daimler.mbingresskit.implementation.network.api.VerificationApi
import com.daimler.mbingresskit.implementation.network.mapDefaultInputError
import com.daimler.mbingresskit.implementation.network.model.verification.ApiVerificationConfirmation
import com.daimler.mbingresskit.implementation.network.model.verification.ApiVerificationTransaction
import com.daimler.mbingresskit.login.VerificationService
import com.daimler.mbnetworkkit.common.ErrorMapStrategy
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.NoContentRequestExecutor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class RetrofitVerificationService(
    verificationApi: VerificationApi,
    scope: CoroutineScope = NetworkCoroutineScope()
) : VerificationService, BaseRetrofitService<VerificationApi>(verificationApi, scope) {

    override fun sendTransaction(
        jwtToken: String,
        payload: VerificationTransaction
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor(getInputErrorMapping()).executeWithTask(task) {
                api.sendTransaction(
                    jwtToken,
                    ApiVerificationTransaction.fromVerificationTransaction(payload)
                )
            }
        }
        return task.futureTask()
    }

    override fun sendConfirmation(
        jwtToken: String,
        payload: VerificationConfirmation
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor(getInputErrorMapping()).executeWithTask(task) {
                api.sendConfirmation(
                    jwtToken,
                    ApiVerificationConfirmation.fromVerificationConfirmation(payload)
                )
            }
        }
        return task.futureTask()
    }

    private fun getInputErrorMapping() = ErrorMapStrategy.Custom {
        mapDefaultInputError(it)
    }
}
