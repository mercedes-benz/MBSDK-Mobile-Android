package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicleusers.VehicleLocalProfile
import com.daimler.mbcommonkit.utils.ifNotNull
import com.google.gson.annotations.SerializedName

internal data class ApiLocalProfile(
    @SerializedName("id") val id: String?,
    @SerializedName("profileName") val name: String?
)

internal fun ApiLocalProfile.toVehicleLocalProfile(): VehicleLocalProfile? =
    ifNotNull(id, name) { id, name ->
        VehicleLocalProfile(id, name)
    }
