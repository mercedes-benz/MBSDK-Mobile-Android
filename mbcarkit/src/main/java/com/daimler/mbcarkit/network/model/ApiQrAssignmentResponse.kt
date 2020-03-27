package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.assignment.QRAssignment
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiQrAssignmentResponse(
    @SerializedName("vin") val vin: String?,
    @SerializedName("assignmentType") val assignmentType: ApiAssignmentType?,
    @SerializedName("model") val model: String?
) : Mappable<QRAssignment> {

    override fun map(): QRAssignment = toQRAssignment()
}

internal fun ApiQrAssignmentResponse.toQRAssignment() = QRAssignment(
    vin.orEmpty(),
    assignmentType.toAssignmentType(),
    model
)
