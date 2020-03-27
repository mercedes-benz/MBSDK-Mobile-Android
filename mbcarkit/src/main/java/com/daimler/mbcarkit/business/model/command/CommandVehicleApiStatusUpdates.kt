package com.daimler.mbcarkit.business.model.command

data class CommandVehicleApiStatusUpdates(val commandsByVin: Map<String, CommandVehicleApiStatusUpdate>)
