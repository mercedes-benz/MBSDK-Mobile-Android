package com.daimler.mbcarkit.util

import com.daimler.mbcarkit.business.model.vehicle.VehicleAttribute

internal fun List<VehicleAttribute<*, *>>.isUpdatedBy(updated: List<VehicleAttribute<*, *>>): Boolean {
    if (this.size != updated.size) {
        return true
    }

    return zip(updated).any { (old, new) ->
        old.status != new.status || old.value != new.value
    }
}
