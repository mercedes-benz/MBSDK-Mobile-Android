package com.daimler.mbcarkit.business.model.services

import com.daimler.mbcarkit.business.model.accountlinkage.AccountType

sealed class ServiceResolvedPrecondition {

    class AccountLinkage(val type: AccountType) : ServiceResolvedPrecondition()
}
