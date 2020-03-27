package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectViolation
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiValetprotectViolation(
    @SerializedName("id") val id: Int,
    @SerializedName("violationtype") val violationtype: ApiValetprotectViolationType?,
    @SerializedName("time") val time: Long,
    @SerializedName("coordinate") val coordinate: ApiGeoCoordinates?,
    @SerializedName("snapshot") val snapshot: ApiValetprotectItem
) : Mappable<ValetprotectViolation> {
    override fun map(): ValetprotectViolation = toValetprotectViolation()
}

internal fun ApiValetprotectViolation.toValetprotectViolation() = ValetprotectViolation(
    id,
    violationtype.toValetprotectViolationType(),
    time,
    GeoCoordinates(coordinate?.latitude, coordinate?.longitude),
    snapshot.toValetprotectItem()
)
