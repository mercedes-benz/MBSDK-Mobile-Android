package com.daimler.mbingresskit.login

import com.daimler.mbingresskit.common.VerificationConfirmation
import com.daimler.mbingresskit.common.VerificationTransaction
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface VerificationService {

    fun sendTransaction(
        jwtToken: String,
        payload: VerificationTransaction
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    fun sendConfirmation(
        jwtToken: String,
        payload: VerificationConfirmation
    ): FutureTask<Unit, ResponseError<out RequestError>?>
}
