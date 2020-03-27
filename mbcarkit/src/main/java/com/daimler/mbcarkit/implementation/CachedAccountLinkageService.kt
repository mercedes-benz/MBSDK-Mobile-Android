package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.AccountLinkageService

internal class CachedAccountLinkageService(
    private val delegate: AccountLinkageService
) : AccountLinkageService by delegate
