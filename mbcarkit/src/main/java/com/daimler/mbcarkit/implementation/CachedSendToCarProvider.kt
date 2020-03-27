package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.SendToCarCache
import com.daimler.mbcarkit.business.SendToCarProvider
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapabilities
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPoi
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarRoute
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal class CachedSendToCarProvider(
    private val provider: SendToCarProvider,
    private val cache: SendToCarCache
) : SendToCarProvider {

    override fun fetchCapabilities(
        token: String,
        finOrVin: String
    ): FutureTask<SendToCarCapabilities, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<SendToCarCapabilities, ResponseError<out RequestError>?>()
        val cachedNetworkCapabilities = cache.loadCapabilities(finOrVin)

        // TODO: 03.11.20 general topic: align/discuss caching mechanism with iOS, was like this before
        cachedNetworkCapabilities
            ?.let {
                deferredTask.complete(it)
                loadViaNetworkAndCache(token, finOrVin)
            } ?: run {
            loadViaNetworkAndCache(token, finOrVin)
                .onComplete {
                    deferredTask.complete(it)
                }.onFailure {
                    deferredTask.fail(it)
                }
        }
        return deferredTask.futureTask()
    }

    private fun loadViaNetworkAndCache(
        token: String,
        finOrVin: String
    ): FutureTask<SendToCarCapabilities, ResponseError<out RequestError>?> {
        val task = TaskObject<SendToCarCapabilities, ResponseError<out RequestError>?>()
        provider.fetchCapabilities(token, finOrVin)
            .onComplete {
                cache.saveCapabilities(finOrVin, it)
                task.complete(it)
            }.onFailure {
                task.fail(it)
            }
        return task.futureTask()
    }

    override fun sendPoi(
        token: String,
        finOrVin: String,
        poi: SendToCarPoi
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        provider.sendPoi(token, finOrVin, poi)
            .onComplete {
                deferredTask.complete(Unit)
            }.onFailure {
                deferredTask.fail(it)
            }
        return deferredTask.futureTask()
    }

    override fun sendRoute(
        token: String,
        finOrVin: String,
        route: SendToCarRoute
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        provider.sendRoute(token, finOrVin, route)
            .onComplete {
                deferredTask.complete(Unit)
            }.onFailure {
                deferredTask.fail(it)
            }
        return deferredTask.futureTask()
    }
}
