package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.VehicleImageCache
import com.daimler.mbcarkit.business.VehicleImageService
import com.daimler.mbcarkit.business.model.vehicle.image.ImageKey
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImage
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImageRequest
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal class CachedVehicleImageService(
    private val imageCache: VehicleImageCache,
    private val vehicleImageService: VehicleImageService
) : VehicleImageService {

    private var cachedFinImageProviderUrl: String? = null

    /**
     * This will load the required images.
     * @param useCachedIfAvailable
     *  if true, all required Images will be loaded from local cache first.
     *  If an Image is not cached, it will be requested from BBD and updated
     *  in cache afterwards.
     */
    override fun fetchVehicleImages(
        token: String,
        imageRequest: VehicleImageRequest,
        useCachedIfAvailable: Boolean
    ): FutureTask<List<VehicleImage>, ResponseError<out RequestError>?> {
        val imageTask = TaskObject<List<VehicleImage>, ResponseError<out RequestError>?>()
        if (useCachedIfAvailable) {
            MBLoggerKit.d("Load images for ${imageRequest.finOrVin} from cache")
            val cachedImages = mutableListOf<VehicleImage>()
            val notCachedImages = mutableListOf<ImageKey>()
            imageRequest.keys.forEach { imageKey ->
                val cachedImage = imageCache.loadImage(imageRequest.finOrVin, imageKey)
                if (cachedImage.imageBytes != null) {
                    cachedImages.add(cachedImage)
                } else {
                    notCachedImages.add(imageKey)
                }
            }
            if (notCachedImages.isNotEmpty()) {
                MBLoggerKit.d("$notCachedImages were not cached. Load from BBD")
                val notCachedRequestBuilder = VehicleImageRequest.Builder(imageRequest.finOrVin, imageRequest.config)
                notCachedImages.forEach { notCachedKey ->
                    notCachedRequestBuilder.addImage(notCachedKey)
                }
                vehicleImageService.fetchVehicleImages(token, notCachedRequestBuilder.build())
                    .onComplete {
                        updateImageInCache(it)
                        cachedImages.addAll(it)
                        imageTask.complete(cachedImages)
                    }.onFailure {
                        imageTask.fail(it)
                    }
            } else {
                MBLoggerKit.d("Loaded cached images.")
                imageTask.complete(cachedImages)
            }
        } else {
            MBLoggerKit.d("Load images from BBD")
            vehicleImageService.fetchVehicleImages(token, imageRequest)
                .onComplete {
                    updateImageInCache(it)
                    imageTask.complete(it)
                }.onFailure {
                    imageTask.fail(it)
                }
        }
        return imageTask.futureTask()
    }

    override fun fetchFinImageProviderUrl(
        token: String
    ): FutureTask<String, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<String, ResponseError<out RequestError>?>()

        return cachedFinImageProviderUrl?.let { url ->
            deferredTask.complete(url)
            deferredTask
        } ?: run {
            vehicleImageService.fetchFinImageProviderUrl(token)
                .onComplete {
                    cachedFinImageProviderUrl = it
                    deferredTask.complete(it)
                }
                .onFailure {
                    deferredTask.fail(it)
                }
            deferredTask.futureTask()
        }
    }

    private fun updateImageInCache(images: List<VehicleImage>) {
        images.forEach {
            try {
                imageCache.updateImage(it)
                MBLoggerKit.d("Updated ${it.imageKey} (${it.imageBytes?.size} Bytes) in ImageCache")
            } catch (e: Exception) {
                MBLoggerKit.e("Could not update ${it.imageKey} in Image-Cache", throwable = e)
            }
        }
    }

    override fun fetchTopViewImages(
        token: String,
        finOrVin: String,
        width: Int,
        height: Int,
        isParallelScaleEnabled: Boolean
    ): FutureTask<Map<String, ByteArray>, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<Map<String, ByteArray>, ResponseError<out RequestError>?>()

        val cachedImages = HashMap<String, ByteArray>()

        TopViewImages.names.forEach { topViewImageName ->
            imageCache.loadImage(finOrVin, ImageKey.TopView(topViewImageName, width, height)).imageBytes?.let {
                cachedImages[topViewImageName] = it
            }
        }

        if (cachedImages.isNotEmpty()) {
            MBLoggerKit.d("Found and returned cached TopViewImages for fin $finOrVin: ${cachedImages.keys}")
            deferredTask.complete(cachedImages)
        } else {
            MBLoggerKit.d("Didn't find cached TopViewImages for fin $finOrVin. Fetching from server...")
            vehicleImageService.fetchTopViewImages(token, finOrVin, width, height, isParallelScaleEnabled)
                .onComplete {
                    val vehicleImages = it.map { rawImage ->
                        VehicleImage(finOrVin, ImageKey.TopView(rawImage.key, width, height).key, rawImage.value)
                    }
                    updateImageInCache(vehicleImages)

                    MBLoggerKit.d("Fetched and returned fresh TopViewImages for fin $finOrVin")
                    deferredTask.complete(it)
                }
                .onFailure {
                    MBLoggerKit.d("Failed to fetch TopViewImages for fin $finOrVin")
                    deferredTask.fail(it)
                }
        }

        return deferredTask.futureTask()
    }
}
