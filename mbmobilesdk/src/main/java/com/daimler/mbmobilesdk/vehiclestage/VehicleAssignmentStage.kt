package com.daimler.mbmobilesdk.vehiclestage

import android.content.Context
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.featuretoggling.FLAG_SHOW_STAGE_INDICATORS

internal sealed class VehicleAssignmentStage(
    selectedStage: Int,
    text: String,
    subText: String? = null
) : BaseWorkflowStage(selectedStage, MAX_STAGES, text, subText) {

    override val stagesVisible: Boolean = MBMobileSDK.featureToggleService().isToggleEnabled(FLAG_SHOW_STAGE_INDICATORS)

    class AssignmentSelection(
        context: Context,
        selectedStage: Int = STAGE_VEHICLE_ASSIGNMENT_SELECTION,
        text: String = context.getString(R.string.assign_toolbar),
        subText: String? = context.getString(R.string.assign_choose_preferred_way)
    ) : VehicleAssignmentStage(selectedStage, text, subText)

    private companion object {

        private const val MAX_STAGES = 3
        private const val STAGE_VEHICLE_ASSIGNMENT_SELECTION = 1
    }
}