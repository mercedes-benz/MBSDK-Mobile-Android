package com.daimler.mbmobilesdk.vehiclestage

import android.content.Context
import com.daimler.mbmobilesdk.R

internal sealed class RegistrationStage(
    selectedStage: Int,
    text: String
) : BaseWorkflowStage(selectedStage, MAX_STAGES, text, null) {

    override val stagesVisible: Boolean = true

    class MeId(
        context: Context,
        selectedStage: Int = STAGE_ME_ID,
        text: String = context.getString(R.string.registration_your_id)
    ) : RegistrationStage(selectedStage, text)

    class VehicleAssignment(
        context: Context,
        selectedStage: Int = STAGE_VEHICLE_ASSIGNMENT,
        text: String = context.getString(R.string.assign_registered_successfully)
    ) : RegistrationStage(selectedStage, text)

    private companion object {

        private const val MAX_STAGES = 4
        private const val STAGE_ME_ID = 1
        private const val STAGE_VEHICLE_ASSIGNMENT = 2
        private const val STAGE_SERVICE_ACTIVATION = 3
    }
}