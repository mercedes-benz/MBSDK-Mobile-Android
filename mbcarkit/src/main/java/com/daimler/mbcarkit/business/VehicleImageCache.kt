package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.vehicle.image.ImageKey
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImage

interface VehicleImageCache {
    fun updateImage(image: VehicleImage)
    fun loadImage(finOrVin: String, key: ImageKey): VehicleImage
    fun clear()
}
