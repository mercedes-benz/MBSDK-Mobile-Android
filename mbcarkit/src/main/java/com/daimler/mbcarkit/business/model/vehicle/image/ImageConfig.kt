package com.daimler.mbcarkit.business.model.vehicle.image

/**
 * The String values are expected as booleans: true/false
 */
data class ImageConfig internal constructor(
    val background: ImageBackground,
    val roofOpen: String? = null,
    val centered: String? = null,
    val night: String? = null,
    val allowFallbackImage: String? = null
) {

    class Builder(private val background: ImageBackground) {

        private var roofOpen: String? = null

        private var centered: String? = null

        private var night: String? = null

        private var allowFallbackImage: String? = null

        /**
         * If enabled, the roof will be shown in an open state. Available only for convertibles.
         */
        fun withRoofOpen(): Builder {
            roofOpen = true.toString()
            return this
        }

        /**
         * If enabled, the vehicle will be positioned in the middle of the image. Currently only static
         * images are affected.
         */
        fun centered(): Builder {
            centered = true.toString()
            return this
        }

        /**
         * If enabled, the vehicle's headlights are turned on in combination with a slightly darker background.
         */
        fun night(): Builder {
            night = true.toString()
            return this
        }

        /**
         * If enabled, the API call will return a fallback image if there is no image available for the specified config.
         */
        fun allowFallbackImage(): Builder {
            allowFallbackImage = true.toString()
            return this
        }

        fun build(): ImageConfig {
            return ImageConfig(background, roofOpen, centered, night, allowFallbackImage)
        }
    }
}
