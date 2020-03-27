package com.daimler.mbcarkit.business.model.vehicle.image

sealed class ImagePerspective constructor(internal val value: String) {

    internal companion object {
        const val PERSPECTIVE_PREFIX_INTERIOR = "I"
        const val PERSPECTIVE_PREFIX_EXTERIOR = "E"
        const val PERSPECTIVE_PREFIX_TOPVIEW = "T"
    }
    class PerspectiveTop : ImagePerspective(PERSPECTIVE_PREFIX_TOPVIEW)
    class PerspectiveStatic(staticView: StaticView) : ImagePerspective(staticView.value)
    class PerspectiveExterior(degrees: Degrees) : ImagePerspective("$PERSPECTIVE_PREFIX_EXTERIOR${degrees.value}")
    class PerspectiveInterior(interiorView: InteriorView) : ImagePerspective("$PERSPECTIVE_PREFIX_INTERIOR${interiorView.value}")
}
