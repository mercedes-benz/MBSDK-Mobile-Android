package com.daimler.mbnetworkkit.networking.coroutines

import com.daimler.mbnetworkkit.common.ErrorMapStrategy
import com.daimler.mbnetworkkit.common.Mappable

/**
 * Convenient [MappableRequestExecutor] where the API model must implement [Mappable].
 *
 * @param T the result model type, must implement [Mappable]
 * @param R the API response type
 */
class RequestExecutor<T : Mappable<R>, R>(
    errorMapStrategy: ErrorMapStrategy = ErrorMapStrategy.Default
) : MappableRequestExecutor<T, R>(errorMapStrategy, { it.map() })
