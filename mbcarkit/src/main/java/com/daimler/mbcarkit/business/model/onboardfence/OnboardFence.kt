package com.daimler.mbcarkit.business.model.onboardfence

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates

data class OnboardFence(
    val geoFenceId: Int?,
    /**
     * User defined name of the fence (max. 128 characters)
     */
    val name: String?,
    val isActive: Boolean?,
    /**
     * Center object for fence circle
     */
    val center: GeoCoordinates,
    val fenceType: FenceType?,
    /**
     * Radius of circle in meter
     */
    val radiusInMeters: Int?,
    /**
     * Number of vertices (only if type=polygon)
     */
    val verticesCount: Int?,
    /**
     * List of vertices
     */
    val verticesPositions: List<GeoCoordinates>?,
    val syncStatus: SyncStatus?
)
