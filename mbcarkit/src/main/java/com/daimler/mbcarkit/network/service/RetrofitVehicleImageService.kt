package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.VehicleImageService
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImage
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImageRequest
import com.daimler.mbcarkit.implementation.TopViewImageTask
import com.daimler.mbcarkit.implementation.VehicleImageTask
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.network.model.ApiFinImageProviderResponse
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.RequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.RequestResult
import com.daimler.mbnetworkkit.networking.coroutines.dispatchError
import com.daimler.mbnetworkkit.networking.coroutines.dispatchResult
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class RetrofitVehicleImageService(
    api: VehicleApi,
    scope: CoroutineScope = NetworkCoroutineScope()
) : BaseRetrofitService<VehicleApi>(api, scope), VehicleImageService {

    override fun fetchVehicleImages(
        token: String,
        imageRequest: VehicleImageRequest,
        useCachedIfAvailable: Boolean
    ): FutureTask<List<VehicleImage>, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<List<VehicleImage>>()
        val imageTask = VehicleImageTask.create(token, api, scope)
        scope.launch {
            when (val result = imageTask.execute(imageRequest)) {
                is RequestResult.Success -> task.dispatchResult(result.body)
                is RequestResult.Error -> task.dispatchError(result.error)
            }
        }
        return task.futureTask()
    }

    override fun fetchFinImageProviderUrl(
        token: String
    ): FutureTask<String, ResponseError<out RequestError>?> {

        val task = ResponseTaskObject<String>()
        scope.launch {
            RequestExecutor<ApiFinImageProviderResponse, String>().executeWithTask(task) {
                api.fetchFinImageProviderUrl(token)
            }
        }
        return task.futureTask()
    }

    override fun fetchTopViewImages(
        token: String,
        finOrVin: String,
        width: Int,
        height: Int,
        isParallelScaleEnabled: Boolean
    ): FutureTask<Map<String, ByteArray>, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Map<String, ByteArray>>()
        val imageTask = TopViewImageTask.create(
            token,
            api,
            isParallelScaleEnabled,
            scope
        )
        scope.launch {
            val result = imageTask.execute(
                TopViewImageTask.TopViewImageRequest(finOrVin, width, height)
            )
            when (result) {
                is RequestResult.Success -> task.dispatchResult(result.body)
                is RequestResult.Error -> task.dispatchError(result.error)
            }
        }
        return task.futureTask()
    }
}
