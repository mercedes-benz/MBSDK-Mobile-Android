package com.daimler.mbcarkit.business.model.assignment

import com.daimler.mbnetworkkit.networking.RequestError

data class AssignmentPreconditionError(
    val finOrVin: String?,
    val assignmentType: AssignmentType,
    val termsOfUseRequired: Boolean,
    val mePinRequired: Boolean,
    val salesDesignation: String?,
    val baumuster: String?,
    val baumusterDescription: String?
) : RequestError
