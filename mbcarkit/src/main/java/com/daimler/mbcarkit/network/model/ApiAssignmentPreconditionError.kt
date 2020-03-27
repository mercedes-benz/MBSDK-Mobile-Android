package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.assignment.AssignmentPreconditionError
import com.daimler.mbnetworkkit.networking.RequestError
import com.google.gson.annotations.SerializedName

internal data class ApiAssignmentPreconditionError(
    @SerializedName("vin") val finOrVin: String?,
    @SerializedName("assignmentType") val assignmentType: ApiAssignmentType?,
    @SerializedName("termsOfUseRequired") val termsOfUseRequired: Boolean,
    @SerializedName("mercedesMePinRequired") val mePinRequired: Boolean,
    @SerializedName("salesDesignation") val salesDesignation: String?,
    @SerializedName("baumuster") val baumuster: String?,
    @SerializedName("baumusterDescription") val baumusterDescription: String?
) : RequestError

internal fun ApiAssignmentPreconditionError.toAssignmentPreconditionError() =
    AssignmentPreconditionError(
        finOrVin,
        assignmentType.toAssignmentType(),
        termsOfUseRequired,
        mePinRequired,
        salesDesignation,
        baumuster,
        baumusterDescription
    )
