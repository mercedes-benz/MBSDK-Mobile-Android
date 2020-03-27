package com.daimler.mbcarkit.business.model.services

data class ServiceActivationStatusUpdate(
    val sequenceNumber: Int,
    val finOrVin: String,
    val updates: List<ServiceUpdate>
)
