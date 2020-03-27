package com.daimler.mbcarkit.business.model.services

data class ServiceActivationStatusUpdates(
    val updatesByVin: Map<String, ServiceActivationStatusUpdate>,
    val sequenceNumber: Int
)
