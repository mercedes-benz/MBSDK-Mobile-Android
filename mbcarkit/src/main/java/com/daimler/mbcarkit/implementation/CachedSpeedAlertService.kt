package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.SpeedAlertService

internal class CachedSpeedAlertService(
    private val speedAlertService: SpeedAlertService
) : SpeedAlertService by speedAlertService
