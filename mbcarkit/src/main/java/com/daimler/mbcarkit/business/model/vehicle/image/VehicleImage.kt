package com.daimler.mbcarkit.business.model.vehicle.image

data class VehicleImage(val finOrVin: String, val imageKey: String, val imageBytes: ByteArray?) {

    override fun equals(other: Any?): Boolean {
        return other != null && other is VehicleImage && other.imageKey == imageKey && other.imageBytes?.size == imageBytes?.size
    }

    override fun hashCode(): Int {
        return imageKey.hashCode()
    }
}

fun VehicleImage.primaryKey(): String {
    return "${this.finOrVin}${this.imageKey}"
}
