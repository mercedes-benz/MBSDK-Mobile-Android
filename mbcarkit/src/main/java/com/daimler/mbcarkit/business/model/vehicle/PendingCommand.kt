package com.daimler.mbcarkit.business.model.vehicle

data class PendingCommand(val finOrVin: String, val pID: String, val requestId: String, val type: Int)
