package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImage
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImageRequest
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface VehicleImageService {

    /**
     * Fetches exterior/interior images for a given vehicle with lots of configuration possibilities
     * @param token Authentication token
     * @param imageRequest Configuration options, e.g. dark/light background
     * @return Observable Task, which delivers a list of vehicle images
     */
    fun fetchVehicleImages(token: String, imageRequest: VehicleImageRequest, useCachedIfAvailable: Boolean = true): FutureTask<List<VehicleImage>, ResponseError<out RequestError>?>

    /**
     * Fetches a base url for market specific 'vehicle registration' images
     * @param token Authentication token
     * @return Observable Task, which delivers a url
     */
    fun fetchFinImageProviderUrl(token: String): FutureTask<String, ResponseError<out RequestError>?>

    /**
     * Fetches a stack of TopViewImages for a given vehicle in a specified size
     * @param token Authentication token
     * @param finOrVin Vehicle identification number
     * @param width Specified Bitmap width
     * @param height Specified Bitmap height
     * @param isParallelScaleEnabled Specify for parallel scaling for quicker processing
     * @return Observable Task, which delivers a Map of Bitmaps for specific vehicle parts, e.g. open trunk image
     */
    fun fetchTopViewImages(
        token: String,
        finOrVin: String,
        width: Int,
        height: Int,
        isParallelScaleEnabled: Boolean = false
    ): FutureTask<Map<String, ByteArray>, ResponseError<out RequestError>?>
}
