package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.accountlinkage.AccountLinkageGroup
import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface AccountLinkageService {

    /**
     * Fetches a list of available accounts and providers.
     *
     * @param jwtToken authentication token
     * @param finOrVin the related vehicle
     * @param serviceIds services for which to fetch accounts, can be null to fetch all possible accounts
     * @param connectRedirectUrl the url to be called after a connection, can be null
     */
    fun fetchAccounts(
        jwtToken: String,
        finOrVin: String,
        serviceIds: List<String>?,
        connectRedirectUrl: String?
    ): FutureTask<List<AccountLinkageGroup>, ResponseError<out RequestError>?>

    /**
     * Deletes a linked account.
     *
     * @param jwtToken authentication token
     * @param finOrVin the related vehicle
     * @param vendorId the id of the account provider
     * @param accountType the type of the account
     */
    fun deleteAccount(
        jwtToken: String,
        finOrVin: String,
        vendorId: String,
        accountType: AccountType
    ): FutureTask<Unit, ResponseError<out RequestError>?>
}
