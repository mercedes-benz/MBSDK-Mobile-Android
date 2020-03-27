package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.customerfence.CustomerFence
import com.daimler.mbcarkit.business.model.customerfence.CustomerFenceViolation
import com.daimler.mbcarkit.business.model.onboardfence.OnboardFence
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface OnboardGeofencingService {

    /**
     * Fetching list of a car's OnboardFences model
     *
     * @param token authentication token
     * @param finOrVin the vehicle's FIN or VIN
     * @return List of [OnboardFence]s
     */
    fun fetchOnboardFences(token: String, finOrVin: String):
        FutureTask<List<OnboardFence>, ResponseError<out RequestError>?>

    /**
     * Creates a list of OnboardFences for a car
     *
     * @param token authentication token
     * @param finOrVin the vehicle's FIN or VIN
     * @param onboardFences List of [OnboardFence]s which should be created
     */
    fun createOnboardFences(token: String, finOrVin: String, onboardFences: List<OnboardFence>):
        FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Updates the given OnboardFences.
     *
     * @param token authentication token
     * @param finOrVin the vehicle's FIN or VIN
     * @param onboardFences list of [OnboardFence]s to update
     */
    fun updateOnboardFences(token: String, finOrVin: String, onboardFences: List<OnboardFence>):
        FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Deletes the OnboardFences for the given ids.
     *
     * @param token authentication token
     * @param finOrVin the vehicle's FIN or VIN
     * @param ids list of [OnboardFence.onboardFenceId]s to delete. Pass an empty list to delete all OnboardFences
     */
    fun deleteOnboardFences(token: String, finOrVin: String, ids: List<Int> = emptyList()):
        FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Fetching list of a car's CustomerFences model
     *
     * @param token authentication token
     * @param finOrVin the vehicle's FIN or VIN
     * @return List of [CustomerFence]s
     */
    fun fetchCustomerFences(token: String, finOrVin: String):
        FutureTask<List<CustomerFence>, ResponseError<out RequestError>?>

    /**
     * Creates a list of CustomerFences for a car
     *
     * @param token authentication token
     * @param finOrVin the vehicle's FIN or VIN
     * @param customerFences List of [CustomerFence]s which should be created
     */
    fun createCustomerFences(token: String, finOrVin: String, customerFences: List<CustomerFence>):
        FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Updates the given CustomerFences
     *
     * @param token authentication token
     * @param finOrVin the vehicle's FIN or VIN
     * @param customerFences list of [CustomerFence] to update
     */
    fun updateCustomerFences(token: String, finOrVin: String, customerFences: List<CustomerFence>):
        FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Deletes the CustomerFences for the given ids
     *
     * @param token authentication token
     * @param finOrVin the vehicle's FIN or VIN
     * @param ids list of [CustomerFence.customerFenceId]s to delete; empty list to delete all CustomerFences
     */
    fun deleteCustomerFences(token: String, finOrVin: String, ids: List<Int> = emptyList()):
        FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Fetches a car's CustomerFence violations
     *
     * @param token authentication token
     * @param finOrVin the vehicle's FIN or VIN
     * @return List of [CustomerFenceViolation]s
     */
    fun fetchCustomerFenceViolations(token: String, finOrVin: String):
        FutureTask<List<CustomerFenceViolation>, ResponseError<out RequestError>?>

    /**
     * Deletes a car's CustomerFence violations
     *
     * @param token authentication token
     * @param finOrVin the vehicle's FIN or VIN
     * @param ids list of [CustomerFenceViolation.violationId]s to delete; empty list to delete all
     */
    fun deleteCustomerFenceViolations(token: String, finOrVin: String, ids: List<Int> = emptyList()):
        FutureTask<Unit, ResponseError<out RequestError>?>
}
