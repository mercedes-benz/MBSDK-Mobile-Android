package com.daimler.mbmobilesdk.vehiclestage

internal abstract class BaseWorkflowStage(
    protected val selectedStage: Int,
    protected val maxStages: Int,
    protected val text: String,
    protected val subText: String?
) : WorkflowStage {

    protected abstract val stagesVisible: Boolean

    override fun toStageConfig(): StageConfig {
        return StageConfig(stagesVisible, selectedStage, maxStages, text, subText)
    }
}