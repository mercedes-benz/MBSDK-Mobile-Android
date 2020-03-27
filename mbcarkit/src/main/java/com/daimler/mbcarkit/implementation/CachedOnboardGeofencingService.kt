package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.OnboardGeofencingService

internal class CachedOnboardGeofencingService(
    private val onboardGeofencingService: OnboardGeofencingService
) : OnboardGeofencingService by onboardGeofencingService
