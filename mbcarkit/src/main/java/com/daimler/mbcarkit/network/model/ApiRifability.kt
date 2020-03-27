package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.rif.Rifability
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiRifability(
    @SerializedName("rifable") val rifable: Boolean?,
    @SerializedName("isRifSupportedForVAC") val rifSupportedForVac: Boolean?,
    @SerializedName("isPushNotificationSupportedForVAC") val pushSupportedForVac: Boolean?,
    @SerializedName("canCarReceiveVACs") val canCarReceiveVacs: Boolean?,
    @SerializedName("vehicleConnectivity") val vehicleConnectivity: ApiVehicleConnectivity?
) : Mappable<Rifability> {

    override fun map(): Rifability = toRifability()
}

internal fun ApiRifability.toRifability(): Rifability {
    val rifable = when {
        canCarReceiveVacs != null -> canCarReceiveVacs
        rifSupportedForVac != null -> rifSupportedForVac
        rifable != null -> rifable
        else -> false
    }
    val isConnectVehicle = vehicleConnectivity == ApiVehicleConnectivity.BUILT_IN
    return Rifability(rifable, isConnectVehicle)
}
