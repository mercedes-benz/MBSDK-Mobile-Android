package com.daimler.mbingresskit.implementation.network.service

import kotlinx.coroutines.CoroutineScope
import java.util.UUID

internal abstract class BaseRetrofitService<T>(
    protected val api: T,
    protected val scope: CoroutineScope
) {

    fun newNonce() = UUID.randomUUID().toString()
}
