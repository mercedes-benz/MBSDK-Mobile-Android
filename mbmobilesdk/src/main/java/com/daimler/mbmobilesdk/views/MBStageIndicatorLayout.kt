package com.daimler.mbmobilesdk.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.vehiclestage.StageConfig
import com.daimler.mbuikit.widgets.layouts.MBElevatedConstraintLayout
import kotlinx.android.synthetic.main.view_stage_indicator.view.*

internal class MBStageIndicatorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MBElevatedConstraintLayout(context, attrs, defStyleAttr) {

    var stageConfig: StageConfig? = null
        set(value) {
            field = value
            applyConfig(value)
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_stage_indicator, this, true)
    }

    private fun applyConfig(stageConfig: StageConfig?) {
        stageConfig?.let {
            visibility = View.VISIBLE
            view_stages.apply {
                visibility = if (it.stagesVisible) View.VISIBLE else View.GONE
                setSelectedStage(it.selectedStage)
                setStages(it.stages)
            }
            tv_headline.apply {
                visibility = if (!it.stageText.isNullOrBlank()) View.VISIBLE else View.GONE
                text = it.stageText
            }
            tv_subtitle.apply {
                visibility = if (!it.stageSubText.isNullOrBlank()) View.VISIBLE else View.GONE
                text = it.stageSubText
            }
        } ?: setGone()
    }

    private fun setGone() {
        visibility = View.GONE
    }
}