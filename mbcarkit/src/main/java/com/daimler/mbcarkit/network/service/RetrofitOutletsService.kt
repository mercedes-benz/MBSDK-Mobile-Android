package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.OutletsService
import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.daimler.mbcarkit.business.model.merchants.Merchants
import com.daimler.mbcarkit.business.model.merchants.OutletActivity
import com.daimler.mbcarkit.business.model.merchants.Radius
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.network.model.ApiCenter
import com.daimler.mbcarkit.network.model.ApiMerchantRequest
import com.daimler.mbcarkit.network.model.ApiMerchantResponse
import com.daimler.mbcarkit.network.model.ApiOutletActivity
import com.daimler.mbcarkit.network.model.ApiRadius
import com.daimler.mbcarkit.network.model.ApiSearchArea
import com.daimler.mbcarkit.network.model.toMerchants
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.MappableRequestExecutor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class RetrofitOutletsService(
    api: VehicleApi,
    scope: CoroutineScope = NetworkCoroutineScope()
) : BaseRetrofitService<VehicleApi>(api, scope), OutletsService {

    override fun fetchOutlets(
        token: String?,
        zipOrCity: String?,
        countryIsoCode: String?,
        geoCoordinates: GeoCoordinates?,
        radius: Radius,
        outletActivity: OutletActivity?
    ): FutureTask<Merchants, ResponseError<out RequestError>?> {
        val task = TaskObject<Merchants, ResponseError<out RequestError>?>()
        val merchantsRequest = mapMerchantsRequest(
            zipOrCity,
            countryIsoCode,
            geoCoordinates,
            radius,
            outletActivity
        )
        scope.launch {
            MappableRequestExecutor<List<ApiMerchantResponse>, Merchants> {
                it.toMerchants()
            }.executeWithTask(task) {
                api.fetchMerchants(
                    token,
                    merchantsRequest
                )
            }
        }
        return task.futureTask()
    }

    private fun mapMerchantsRequest(
        zipOrCity: String?,
        countryIsoCode: String?,
        geoCoordinates: GeoCoordinates?,
        radius: Radius,
        outletActivity: OutletActivity?
    ): ApiMerchantRequest {
        val searchArea = geoCoordinates?.let {
            ApiSearchArea(
                ApiCenter.fromGeoCoordinates(it),
                ApiRadius.fromRadius(radius)
            )
        }

        return ApiMerchantRequest(
            zipOrCity,
            countryIsoCode,
            searchArea,
            activity = ApiOutletActivity.fromOutletActivity(outletActivity)
        )
    }
}
