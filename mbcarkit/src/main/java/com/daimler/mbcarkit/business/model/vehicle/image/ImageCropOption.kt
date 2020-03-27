package com.daimler.mbcarkit.business.model.vehicle.image

/**
 * Crop options for vehicle images.
 */
enum class ImageCropOption(internal val keySuffix: String) {

    /**
     * Crop the image to a square. A non-squared image might still be returned
     * if the base image cannot be cut into a square.
     */
    SQUARE("croppedSquare"),

    /**
     * Crop the image to a square. Transparent pixels are added to the base image
     * to ensure a squared image.
     */
    SAFE_SQUARE("croppedSafeSquare"),

    /**
     * Crops the image without specific constraints. This results in a maximum cut away area.
     */
    BEST_EFFORT("croppedBestEffort")
}
