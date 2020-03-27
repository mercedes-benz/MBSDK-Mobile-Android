package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.SpeedfenceService

internal class CachedSpeedfenceService(
    private val speedfenceService: SpeedfenceService
) : SpeedfenceService by speedfenceService
