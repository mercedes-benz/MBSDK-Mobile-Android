package com.daimler.mbcarkit.business.model.vehicle.image

class VehicleImageRequest private constructor(
    val finOrVin: String,
    val keys: List<ImageKey>,
    val config: ImageConfig
) {

    private companion object {
        const val KEY_SEPARATOR = ","
    }

    fun background() = config.background.key

    fun isRoofOpen() = config.roofOpen

    fun isCentered() = config.centered

    fun isNight() = config.night

    fun allowFallbackImage() = config.allowFallbackImage

    fun keysString() = keys.joinToString(separator = KEY_SEPARATOR) { it.key }

    class Builder(
        private val finOrVin: String,
        private val config: ImageConfig
    ) {

        private val keys = mutableListOf<ImageKey>()

        fun addImage(imageKey: ImageKey): Builder {
            keys.add(imageKey)
            return this
        }

        fun build(): VehicleImageRequest {
            return VehicleImageRequest(finOrVin, keys, config.copy())
        }
    }
}
