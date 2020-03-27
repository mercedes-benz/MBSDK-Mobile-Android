package com.daimler.mbcarkit.business.model.services

data class ServiceActivationStatusUpdateWrapper(
    val isSelectedVehicle: Boolean,
    val statusUpdate: ServiceActivationStatusUpdate
)
