package com.daimler.mbprotokit.dto.service

data class ServiceActivationStatusUpdate(
    val sequenceNumber: Int,
    val finOrVin: String,
    val updates: List<ServiceUpdate>
)
