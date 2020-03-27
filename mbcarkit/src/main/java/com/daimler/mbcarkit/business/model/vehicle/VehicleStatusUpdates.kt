package com.daimler.mbcarkit.business.model.vehicle

data class VehicleStatusUpdates(val vehiclesByVin: Map<String, VehicleStatusUpdate>, val sequenceNumber: Int)
