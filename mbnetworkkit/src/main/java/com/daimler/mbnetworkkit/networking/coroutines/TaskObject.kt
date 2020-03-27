package com.daimler.mbnetworkkit.networking.coroutines

import com.daimler.mbnetworkkit.task.TaskObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> TaskObject<T, *>.dispatchResult(result: T) =
    withContext(Dispatchers.Main) { complete(result) }

suspend fun <E> TaskObject<*, E>.dispatchError(error: E) =
    withContext(Dispatchers.Main) { fail(error) }
