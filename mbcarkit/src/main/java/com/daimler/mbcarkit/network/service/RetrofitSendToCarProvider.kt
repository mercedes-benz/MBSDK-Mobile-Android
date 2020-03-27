package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.SendToCarProvider
import com.daimler.mbcarkit.business.model.sendtocar.RouteType
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapabilities
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPoi
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarRoute
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.network.model.ApiSendToCarCapabilities
import com.daimler.mbcarkit.network.model.ApiSendToCarRoute
import com.daimler.mbcarkit.network.model.toSendToCarCapabilities
import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.MappableRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.NoContentRequestExecutor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class RetrofitSendToCarProvider(
    api: VehicleApi,
    private val headerService: HeaderService,
    scope: CoroutineScope = NetworkCoroutineScope()
) : BaseRetrofitService<VehicleApi>(api, scope), SendToCarProvider {

    override fun fetchCapabilities(
        token: String,
        finOrVin: String
    ): FutureTask<SendToCarCapabilities, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<SendToCarCapabilities>()
        scope.launch {
            MappableRequestExecutor<ApiSendToCarCapabilities, SendToCarCapabilities> {
                it.toSendToCarCapabilities()
            }.executeWithTask(task) {
                api.fetchSendToCarCapabilities(
                    token,
                    finOrVin,
                    headerService.networkLocale
                )
            }
        }
        return task.futureTask()
    }

    override fun sendPoi(
        token: String,
        finOrVin: String,
        poi: SendToCarPoi
    ): FutureTask<Unit, ResponseError<out RequestError>?> =
        sendRoute(token, finOrVin, SendToCarRoute(RouteType.SINGLE_POI, listOf(poi.location), poi.title, poi.notificationText))

    override fun sendRoute(
        token: String,
        finOrVin: String,
        route: SendToCarRoute
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.sendRoute(
                    token,
                    finOrVin,
                    ApiSendToCarRoute.fromSendToCarRoute(route)
                )
            }
        }
        return task.futureTask()
    }
}
