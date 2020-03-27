package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.ValetProtectService

internal class CachedValetProtectService(
    private val valetProtectService: ValetProtectService
) : ValetProtectService by valetProtectService
