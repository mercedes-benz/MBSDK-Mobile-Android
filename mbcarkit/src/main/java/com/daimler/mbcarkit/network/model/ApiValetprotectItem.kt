package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectItem
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectRadius
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiValetprotectItem(
    @SerializedName("name") val name: String?,
    @SerializedName("violationtypes") val violationtypes: List<ApiValetprotectViolationType>,
    @SerializedName("center") val center: ApiGeoCoordinates?,
    @SerializedName("radius") val radius: ApiValetprotectRadius?
) : Mappable<ValetprotectItem> {

    override fun map(): ValetprotectItem = toValetprotectItem()

    companion object {
        fun fromValetprotectItem(valetprotectItem: ValetprotectItem) = ApiValetprotectItem(
            valetprotectItem.name,
            valetprotectItem.violationtypes.map { ApiValetprotectViolationType.fromValetprotectViolationType(it) },
            ApiGeoCoordinates(
                valetprotectItem.center.latitude,
                valetprotectItem.center.longitude
            ),
            ApiValetprotectRadius(
                valetprotectItem.radius.value,
                ApiDistanceUnit.fromDistanceUnit(valetprotectItem.radius.unit)
            )
        )
    }
}

internal fun ApiValetprotectItem.toValetprotectItem() = ValetprotectItem(
    name ?: "",
    violationtypes.map { it.toValetprotectViolationType() },
    GeoCoordinates(center?.latitude, center?.longitude),
    ValetprotectRadius(
        radius?.value ?: 0.0,
        radius?.unit.toDistanceUnit()
    )
)
