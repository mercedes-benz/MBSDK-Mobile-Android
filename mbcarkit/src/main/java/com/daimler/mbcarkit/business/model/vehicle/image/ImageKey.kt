package com.daimler.mbcarkit.business.model.vehicle.image

sealed class ImageKey(imagePerspective: ImagePerspective, imageType: VehicleImageType, manipulation: ImageManipulation, specificName: String? = null) {

    companion object {
        internal const val PROVIDER_PREFIX = "B"
        internal const val SEPARATOR = "-"
    }

    internal val key = createImageKey(imagePerspective, imageType, manipulation, specificName)

    class Static(
        perspective: ImagePerspective.PerspectiveStatic,
        imageType: VehicleImageType,
        crop: ImageManipulation = ImageManipulation.None
    ) : ImageKey(perspective, imageType, crop)

    class DynamicExterior(
        perspective: ImagePerspective.PerspectiveExterior,
        imageType: VehicleImageType,
        crop: ImageManipulation = ImageManipulation.None
    ) : ImageKey(perspective, imageType, crop)

    class DynamicInterior(
        perspective: ImagePerspective.PerspectiveInterior,
        imageType: VehicleImageType,
        crop: ImageManipulation = ImageManipulation.None
    ) : ImageKey(perspective, imageType, crop)

    class TopView(
        vehiclePart: String,
        width: Int,
        height: Int
    ) : ImageKey(ImagePerspective.PerspectiveTop(), VehicleImageType.DynamicPng("_${width}_$height"), ImageManipulation.None, vehiclePart)

    override fun toString(): String {
        return "ImageKey(key=$key)"
    }

    private fun createImageKey(imagePerspective: ImagePerspective, imageType: VehicleImageType, manipulation: ImageManipulation, specificName: String?) =
        StringBuilder().apply {
            append(PROVIDER_PREFIX)
            append(imagePerspective.value)

            append(SEPARATOR)
            append(imageType.type)
            append(imageType.size)

            manipulation.keySuffix?.let {
                append(SEPARATOR)
                append(it)
            }

            specificName?.let {
                append(SEPARATOR)
                append(it)
            }
        }.toString()
}
