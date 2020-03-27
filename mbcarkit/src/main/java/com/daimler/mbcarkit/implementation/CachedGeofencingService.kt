package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.GeofencingService

internal class CachedGeofencingService(
    private val geofencingService: GeofencingService
) : GeofencingService by geofencingService
