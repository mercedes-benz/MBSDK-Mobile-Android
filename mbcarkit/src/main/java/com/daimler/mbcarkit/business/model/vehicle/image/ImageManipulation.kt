package com.daimler.mbcarkit.business.model.vehicle.image

/**
 * Manipulation options for the original vehicle image before it is returned to the client.
 */
sealed class ImageManipulation(internal val keySuffix: String?) {

    /**
     * Crop the image, remove transparent pixels.
     * @see ImageCropOption
     */
    class Crop(option: ImageCropOption) : ImageManipulation(option.keySuffix)

    /**
     * Do nothing with the original image. Get it as-is.
     */
    object None : ImageManipulation(null)
}
