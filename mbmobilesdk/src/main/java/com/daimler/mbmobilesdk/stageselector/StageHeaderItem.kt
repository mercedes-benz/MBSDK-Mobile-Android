package com.daimler.mbmobilesdk.stageselector

import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.Region

internal class StageHeaderItem(region: Region) : BaseStageItem(false) {

    val title = region.name

    override fun getLayoutRes(): Int = R.layout.item_stage_header

    override fun getModelId(): Int = BR.model

    override fun select() = Unit

    override fun deselect() = Unit
}