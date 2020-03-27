package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit

data class Location(
    var heading: VehicleAttribute<Double, NoUnit>,
    var latitude: VehicleAttribute<Double, NoUnit>,
    var longitude: VehicleAttribute<Double, NoUnit>,
    var positionErrorState: VehicleAttribute<VehicleLocationErrorState, NoUnit>,
    var proximityCalculationRequired: VehicleAttribute<RequiredState, NoUnit>
)
