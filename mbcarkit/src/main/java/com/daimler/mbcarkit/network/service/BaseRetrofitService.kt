package com.daimler.mbcarkit.network.service

import kotlinx.coroutines.CoroutineScope

internal abstract class BaseRetrofitService<Api>(
    protected val api: Api,
    protected val scope: CoroutineScope
)
