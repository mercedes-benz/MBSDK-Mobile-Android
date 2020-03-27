package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.sendtocar.SendToCarWaypoint
import com.google.gson.annotations.SerializedName

internal data class ApiSendToCarWaypoint(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("title") val title: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("state") val state: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("district") val district: String?,
    @SerializedName("postalCode") val postalCode: String?,
    @SerializedName("street") val street: String?,
    @SerializedName("houseNumber") val houseNumber: String?,
    @SerializedName("subdivision") val subdivision: String?,
    @SerializedName("phoneNumber") val phoneNumber: String?
) {
    companion object {
        fun fromSendToCarWaypoint(sendToCarWaypoint: SendToCarWaypoint) = ApiSendToCarWaypoint(
            sendToCarWaypoint.latitude,
            sendToCarWaypoint.longitude,
            sendToCarWaypoint.title,
            sendToCarWaypoint.country,
            sendToCarWaypoint.state,
            sendToCarWaypoint.city,
            sendToCarWaypoint.district,
            sendToCarWaypoint.postalCode,
            sendToCarWaypoint.street,
            sendToCarWaypoint.houseNumber,
            sendToCarWaypoint.subdivision,
            sendToCarWaypoint.phoneNumber
        )
    }
}
