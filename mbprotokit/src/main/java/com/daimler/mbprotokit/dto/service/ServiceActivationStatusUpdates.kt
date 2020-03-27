package com.daimler.mbprotokit.dto.service

data class ServiceActivationStatusUpdates(
    val updatesByVin: Map<String, ServiceActivationStatusUpdate>,
    val sequenceNumber: Int
)
