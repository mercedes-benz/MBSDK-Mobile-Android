package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.OutletsService

internal class CachedMerchantsService(
    private val outletsService: OutletsService
) : OutletsService by outletsService
