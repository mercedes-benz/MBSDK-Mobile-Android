package com.daimler.mbmobilesdk.stageselector

import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.Endpoint

internal class StageItem(
    val endpoint: Endpoint,
    val title: String,
    private val events: Events?
) : BaseStageItem(true) {

    override fun getLayoutRes(): Int = R.layout.item_stage_selector

    override fun getModelId(): Int = BR.model

    override fun select() = selected.set(true)

    override fun deselect() = selected.set(false)

    fun onItemClicked() {
        events?.onStageSelected(endpoint)
    }

    interface Events {
        fun onStageSelected(endpoint: Endpoint)
    }
}