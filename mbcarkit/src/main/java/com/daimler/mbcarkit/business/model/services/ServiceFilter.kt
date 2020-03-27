package com.daimler.mbcarkit.business.model.services

sealed class ServiceFilter {

    /**
     * Filters for services that contain [ServiceRight.READ].
     */
    object ReadOnly : ServiceFilter()

    /**
     * Filters for services that contain ALL given rights.
     */
    class Rights(val rights: List<ServiceRight>) : ServiceFilter()

    /**
     * No filter.
     */
    object None : ServiceFilter()
}
