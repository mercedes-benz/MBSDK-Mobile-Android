package com.daimler.mbmobilesdk.vehicleselection

import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.vehicle.image.*
import com.daimler.mbnetworkkit.common.HttpErrorDescription
import com.daimler.mbnetworkkit.networking.HttpError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal class VehicleImageLoader(
    private val vin: String
) : TaskObject<List<VehicleImage>, ResponseError<out RequestError>?>() {

    fun load(imageConfig: ImageConfig, imageKeys: List<ImageKey>): FutureTask<List<VehicleImage>, ResponseError<out RequestError>?> {
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                val imageRequest =
                    VehicleImageRequest.Builder(vin, imageConfig)
                        .apply { imageKeys.forEach { addImage(it) } }
                        .build()
                MBCarKit.vehicleImageService().fetchVehicleImages(jwt, imageRequest)
                    .onComplete { complete(it) }
                    .onFailure { fail(it) }
            }
            .onFailure {
                fail(ResponseError.httpError(
                    HttpError.Unauthorized(HttpErrorDescription(null, null)).code,
                    "Could not refresh token."))
            }
        return futureTask()
    }

    fun loadDefault(
        degrees: Degrees = Degrees.DEGREES_0,
        manipulation: ImageManipulation = ImageManipulation.None
    ): FutureTask<List<VehicleImage>, ResponseError<out RequestError>?> {
        val imageConfig = ImageConfig.Builder(ImageBackground.CUTOUT).build()
        val imageKey = ImageKey.DynamicExterior(
            ImagePerspective.PerspectiveExterior(degrees),
            VehicleImageType.Png(VehicleImagePngSize.SIZE_1920x1080),
            manipulation
        )
        return load(imageConfig, listOf(imageKey))
    }
}