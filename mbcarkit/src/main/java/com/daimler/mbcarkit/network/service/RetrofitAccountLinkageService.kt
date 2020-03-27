package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.AccountLinkageService
import com.daimler.mbcarkit.business.model.accountlinkage.AccountLinkageGroup
import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.network.model.ApiAccountLinkages
import com.daimler.mbcarkit.network.model.toApiAccountType
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.NoContentRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.RequestExecutor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class RetrofitAccountLinkageService(
    api: VehicleApi,
    scope: CoroutineScope = NetworkCoroutineScope()
) : BaseRetrofitService<VehicleApi>(api, scope), AccountLinkageService {

    override fun fetchAccounts(
        jwtToken: String,
        finOrVin: String,
        serviceIds: List<String>?,
        connectRedirectUrl: String?
    ): FutureTask<List<AccountLinkageGroup>, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<List<AccountLinkageGroup>>()
        scope.launch {
            RequestExecutor<ApiAccountLinkages, List<AccountLinkageGroup>>()
                .executeWithTask(task) {
                    api.fetchAccounts(
                        jwtToken,
                        finOrVin,
                        serviceIds?.joinToString(","),
                        connectRedirectUrl
                    )
                }
        }

        return task.futureTask()
    }

    override fun deleteAccount(
        jwtToken: String,
        finOrVin: String,
        vendorId: String,
        accountType: AccountType
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteAccount(
                    jwtToken,
                    finOrVin,
                    vendorId,
                    accountType.toApiAccountType()
                )
            }
        }
        return task.futureTask()
    }
}
