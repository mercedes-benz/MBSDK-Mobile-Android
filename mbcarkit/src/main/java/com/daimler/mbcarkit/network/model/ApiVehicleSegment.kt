package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.VehicleSegment
import com.daimler.mbcarkit.network.model.ApiVehicleSegment.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiVehicleSegment(val value: String) {

    /** Value if the car is a standard mercedes benz or not one of the other types **/
    @SerializedName("DEFAULT") DEFAULT("DEFAULT"),

    /** Represents the car segment AMG **/
    @SerializedName("AMG") AMG("AMG"),

    /** Represents the car segment of all electric cars from Daimler **/
    @SerializedName("EQ") EQ("EQ"),

    /** Represents the car segment Maybach **/
    @SerializedName("MAYBACH") MAYBACH("MAYBACH"),

    /** Represents the car segment S-Class **/
    @SerializedName("SCLASS") SCLASS("SCLASS");

    companion object {
        val map: Map<String, VehicleSegment> = VehicleSegment.values().associateBy(VehicleSegment::name)
    }
}

internal fun ApiVehicleSegment?.toVehicleSegment(): VehicleSegment? =
    this?.let { map[name] }
