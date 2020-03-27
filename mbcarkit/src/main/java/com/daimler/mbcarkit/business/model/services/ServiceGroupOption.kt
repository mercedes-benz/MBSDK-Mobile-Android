package com.daimler.mbcarkit.business.model.services

/**
 * Specifies the grouping of the services.
 */
enum class ServiceGroupOption {
    /**
     * No grouping, every service will be within a single group.
     */
    NONE,

    /**
     * Services are grouped by their category.
     */
    CATEGORY
}
