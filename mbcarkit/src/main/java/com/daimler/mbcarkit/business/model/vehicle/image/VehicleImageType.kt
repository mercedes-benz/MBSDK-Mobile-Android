package com.daimler.mbcarkit.business.model.vehicle.image

sealed class VehicleImageType(internal val type: String, internal val size: String) {

    internal companion object {
        const val TYPE_JPEG = "A"
        const val TYPE_PNG = "P"
    }

    class Jpeg(jpgSize: VehicleImageJpegSize) : VehicleImageType(TYPE_JPEG, jpgSize.value)
    class Png(pngSize: VehicleImagePngSize) : VehicleImageType(TYPE_PNG, pngSize.value)
    class DynamicPng(pngSize: String) : VehicleImageType(TYPE_PNG, pngSize)
}
