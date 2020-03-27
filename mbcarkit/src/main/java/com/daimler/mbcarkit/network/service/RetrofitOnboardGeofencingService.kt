package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.OnboardGeofencingService
import com.daimler.mbcarkit.business.model.customerfence.CustomerFence
import com.daimler.mbcarkit.business.model.customerfence.CustomerFenceViolation
import com.daimler.mbcarkit.business.model.onboardfence.OnboardFence
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.network.model.ApiCustomerFence
import com.daimler.mbcarkit.network.model.ApiCustomerFenceViolations
import com.daimler.mbcarkit.network.model.ApiCustomerFences
import com.daimler.mbcarkit.network.model.ApiDeleteCustomerFenceViolationsRequest
import com.daimler.mbcarkit.network.model.ApiDeleteCustomerFencesRequest
import com.daimler.mbcarkit.network.model.ApiDeleteOnboardFencesRequest
import com.daimler.mbcarkit.network.model.ApiOnboardFence
import com.daimler.mbcarkit.network.model.ApiOnboardFences
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.NoContentRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.RequestExecutor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class RetrofitOnboardGeofencingService(
    api: VehicleApi,
    scope: CoroutineScope = NetworkCoroutineScope()
) : BaseRetrofitService<VehicleApi>(api, scope), OnboardGeofencingService {

    override fun fetchOnboardFences(
        token: String,
        finOrVin: String
    ): FutureTask<List<OnboardFence>, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<List<OnboardFence>>()
        scope.launch {
            RequestExecutor<ApiOnboardFences, List<OnboardFence>>().executeWithTask(task) {
                api.fetchOnboardFences(
                    token,
                    finOrVin
                )
            }
        }
        return task.futureTask()
    }

    override fun createOnboardFences(
        token: String,
        finOrVin: String,
        onboardFences: List<OnboardFence>
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.createOnboardFences(
                    token,
                    finOrVin,
                    ApiOnboardFences(onboardFences.map { ApiOnboardFence.fromOnboardFence(it) })
                )
            }
        }
        return task.futureTask()
    }

    override fun updateOnboardFences(
        token: String,
        finOrVin: String,
        onboardFences: List<OnboardFence>
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.updateOnboardFences(
                    token,
                    finOrVin,
                    ApiOnboardFences(onboardFences.map { ApiOnboardFence.fromOnboardFence(it) })
                )
            }
        }
        return task.futureTask()
    }

    override fun deleteOnboardFences(
        token: String,
        finOrVin: String,
        ids: List<Int>
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteOnboardFences(
                    token,
                    finOrVin,
                    ApiDeleteOnboardFencesRequest(ids)
                )
            }
        }
        return task.futureTask()
    }

    override fun fetchCustomerFences(
        token: String,
        finOrVin: String
    ): FutureTask<List<CustomerFence>, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<List<CustomerFence>>()
        scope.launch {
            RequestExecutor<ApiCustomerFences, List<CustomerFence>>().executeWithTask(task) {
                api.fetchCustomerFences(
                    token,
                    finOrVin
                )
            }
        }
        return task.futureTask()
    }

    override fun createCustomerFences(
        token: String,
        finOrVin: String,
        customerFences: List<CustomerFence>
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.createCustomerFences(
                    token,
                    finOrVin,
                    ApiCustomerFences(customerFences.map { ApiCustomerFence.fromCustomerFence(it) })
                )
            }
        }
        return task.futureTask()
    }

    override fun updateCustomerFences(
        token: String,
        finOrVin: String,
        customerFences: List<CustomerFence>
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.updateCustomerFences(
                    token,
                    finOrVin,
                    ApiCustomerFences(customerFences.map { ApiCustomerFence.fromCustomerFence(it) })
                )
            }
        }
        return task.futureTask()
    }

    override fun deleteCustomerFences(
        token: String,
        finOrVin: String,
        ids: List<Int>
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteCustomerFences(
                    token,
                    finOrVin,
                    ApiDeleteCustomerFencesRequest(ids)
                )
            }
        }
        return task.futureTask()
    }

    override fun fetchCustomerFenceViolations(
        token: String,
        finOrVin: String
    ): FutureTask<List<CustomerFenceViolation>, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<List<CustomerFenceViolation>>()
        scope.launch {
            RequestExecutor<ApiCustomerFenceViolations, List<CustomerFenceViolation>>()
                .executeWithTask(task) {
                    api.fetchCustomerFenceViolations(
                        token,
                        finOrVin
                    )
                }
        }
        return task.futureTask()
    }

    override fun deleteCustomerFenceViolations(
        token: String,
        finOrVin: String,
        ids: List<Int>
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteCustomerFenceViolations(
                    token,
                    finOrVin,
                    ApiDeleteCustomerFenceViolationsRequest(ids)
                )
            }
        }
        return task.futureTask()
    }
}
