package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImage
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImageRequest
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.network.model.ApiVehicleImageResponse
import com.daimler.mbnetworkkit.common.ErrorMapStrategy
import com.daimler.mbnetworkkit.networking.coroutines.RequestResult
import com.daimler.mbnetworkkit.networking.exception.ResponseException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

internal class VehicleImageTask private constructor(
    private val jwtToken: String,
    private val vehicleApi: VehicleApi,
    private val scope: CoroutineScope,
    private val errorMapStrategy: ErrorMapStrategy = ErrorMapStrategy.Default
) {

    suspend fun execute(
        request: VehicleImageRequest,
    ): RequestResult<List<VehicleImage>> =
        try {
            loadAllImagesUrls(request)
        } catch (t: Throwable) {
            RequestResult.Error(errorMapStrategy.get(t))
        }

    private suspend fun loadAllImagesUrls(
        imageRequest: VehicleImageRequest
    ): RequestResult<List<VehicleImage>> = withContext(Dispatchers.IO) {
        val response = vehicleApi.fetchVehicleImages(
            jwtToken,
            imageRequest.finOrVin,
            imageRequest.keysString(),
            imageRequest.background(),
            imageRequest.isRoofOpen(),
            imageRequest.isCentered(),
            imageRequest.isNight(),
            imageRequest.allowFallbackImage()
        )
        if (response.isSuccessful) {
            loadVehicleImages(imageRequest.finOrVin, response.body())
        } else {
            RequestResult.Error(
                errorMapStrategy.get(
                    ResponseException(
                        response.code(),
                        "Response was not successful.",
                        response.errorBody()?.toString()
                    )
                )
            )
        }
    }

    private suspend fun loadVehicleImages(
        vin: String,
        imageUrls: List<ApiVehicleImageResponse>?
    ): RequestResult<List<VehicleImage>> =
        imageUrls?.forEachAsync {
            loadImageFromUrl(vin, it)
        }?.let {
            RequestResult.Success(it)
        } ?: RequestResult.Error(
            errorMapStrategy.get(RuntimeException())
        )

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun loadImageFromUrl(
        vin: String,
        imageResponse: ApiVehicleImageResponse
    ): VehicleImage = withContext(Dispatchers.IO) {
        val response = vehicleApi.fetchFile(imageResponse.imageUrl)
        VehicleImage(vin, imageResponse.imageKey, response.body()?.bytes())
    }

    private suspend fun <S, T> Iterable<S>.forEachAsync(action: suspend (S) -> T): List<T> =
        map {
            scope.async(Dispatchers.IO) {
                action(it)
            }
        }.awaitAll()

    companion object {

        fun create(
            jwtToken: String,
            vehicleApi: VehicleApi,
            scope: CoroutineScope
        ) = VehicleImageTask(jwtToken, vehicleApi, scope)
    }
}
