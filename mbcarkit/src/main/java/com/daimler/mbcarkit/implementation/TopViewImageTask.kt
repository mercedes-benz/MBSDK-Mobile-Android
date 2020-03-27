package com.daimler.mbcarkit.implementation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.util.toByteArray
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.common.ErrorMapStrategy
import com.daimler.mbnetworkkit.networking.coroutines.RequestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.ByteArrayInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

internal class TopViewImageTask private constructor(
    private val jwtToken: String,
    private val vehicleApi: VehicleApi,
    private val shouldScaleInParallel: Boolean,
    private val scope: CoroutineScope,
    private val errorMapStrategy: ErrorMapStrategy = ErrorMapStrategy.Default
) {

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun execute(
        request: TopViewImageRequest
    ): RequestResult<Map<String, ByteArray>> =
        try {
            withContext(Dispatchers.IO) {
                MBLoggerKit.d("Fetching TopViewImages for VIN: ${request.vin}, Width: ${request.width}, Height: ${request.height}")
                val fileResponse = vehicleApi.fetchTopViewImages(
                    jwtToken,
                    request.vin
                )
                MBLoggerKit.d("Successfully fetched TopViewImage Zip File from server. Unzipping...")
                val rawImages = getUnzippedServerResponse(fileResponse.body())
                MBLoggerKit.d("Successfully unzipped ${rawImages.size} TopViewImages. Rescaling...")
                val scaledImages = getScaledImages(rawImages, request.width, request.height)
                MBLoggerKit.d("Successfully rescaled ${scaledImages.size} TopViewImages.")
                RequestResult.Success(scaledImages)
            }
        } catch (t: Throwable) {
            MBLoggerKit.e("Failed TopViewImage loading: $t")
            RequestResult.Error(errorMapStrategy.get(t))
        }

    private fun getUnzippedServerResponse(body: ResponseBody?): Map<String, ByteArray> {
        return body?.bytes()?.let {
            val imageList = HashMap<String, ByteArray>()
            var entry: ZipEntry?

            val stream = ZipInputStream(ByteArrayInputStream(it))
            while (true) {
                entry = stream.nextEntry
                entry ?: break

                val name =
                    entry.name.substringAfter("/").substringAfter("_").substringBeforeLast(".")
                imageList[name] = stream.readBytes()

                stream.closeEntry()
            }
            stream.close()
            imageList
        } ?: HashMap()
    }

    private fun getScaledImagesSerially(
        rawImages: Map<String, ByteArray>,
        width: Int,
        height: Int
    ): Map<String, ByteArray> {
        MBLoggerKit.d(
            "Scaling in serial with ${Runtime.getRuntime().maxMemory()} max heap memory " +
                "for image width: $width and height: $height"
        )
        return rawImages
            .mapNotNull { entry ->
                scaleByteArray(entry.value, width, height)?.let {
                    entry.key to it
                }
            }.toMap()
    }

    private suspend fun getScaledImages(
        rawImages: Map<String, ByteArray>,
        width: Int,
        height: Int
    ): Map<String, ByteArray> =
        if (shouldScaleInParallel) {
            getScaledImagesParallel(rawImages, width, height)
        } else {
            getScaledImagesSerially(rawImages, width, height)
        }

    private suspend fun getScaledImagesParallel(
        rawImages: Map<String, ByteArray>,
        width: Int,
        height: Int
    ): Map<String, ByteArray> {
        MBLoggerKit.d(
            "Scaling in parallel with ${Runtime.getRuntime().maxMemory()} max heap memory " +
                "for image width: $width and height: $height"
        )

        val scaled = rawImages.map {
            scope.async(Dispatchers.Default) {
                it.key to scaleByteArray(it.value, width, height)
            }
        }.awaitAll()
        return scaled.mapNotNull { pair ->
            pair.second?.let {
                pair.first to it
            }
        }.toMap()
    }

    private fun scaleByteArray(array: ByteArray, width: Int, height: Int): ByteArray? {
        return BitmapFactory.decodeByteArray(array, 0, array.size)?.let {
            val byteArray =
                Bitmap.createScaledBitmap(it, width, height, false).toByteArray()
            it.recycle()
            byteArray
        }
    }

    data class TopViewImageRequest(
        val vin: String,
        val width: Int,
        val height: Int
    )

    companion object {

        fun create(
            jwtToken: String,
            vehicleApi: VehicleApi,
            shouldScaleInParallel: Boolean,
            scope: CoroutineScope
        ) = TopViewImageTask(
            jwtToken,
            vehicleApi,
            shouldScaleInParallel,
            scope
        )
    }
}
