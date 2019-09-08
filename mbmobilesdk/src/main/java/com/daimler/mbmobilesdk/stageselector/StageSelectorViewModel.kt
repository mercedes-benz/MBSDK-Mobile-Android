package com.daimler.mbmobilesdk.stageselector

import android.app.Application
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.app.Endpoint
import com.daimler.mbmobilesdk.app.Region
import com.daimler.mbmobilesdk.app.Stage
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.components.viewmodels.MBBaseToolbarViewModel
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent

internal class StageSelectorViewModel(app: Application) : MBBaseToolbarViewModel(app), StageItem.Events {

    val items = MutableLiveArrayList<MBBaseRecyclerItem>()

    val endpointSelectedEvent = MutableLiveEvent<Endpoint>()

    init {
        createSelectableStageItems()
        selectEndpointItem(MBMobileSDK.mobileSdkSettings().endPoint.get())
    }

    override fun onStageSelected(endpoint: Endpoint) {
        selectEndpointItem(endpoint)
        MBMobileSDK.mobileSdkSettings().endPoint.set(endpoint)
        endpointSelectedEvent.sendEvent(endpoint)
    }

    private fun createSelectableStageItems() {
        val regions = Region.values()
        val stages = Stage.values()
        val list = mutableListOf<BaseStageItem>()
        regions.forEach { region ->
            list.add(StageHeaderItem(region))
            stages.forEach { stage ->
                val endpoint = Endpoint(region, stage)
                if (endpoint.isAvailable()) list.add(StageItem(endpoint, stage.displayName, this))
            }
        }
        items.addAllAndDispatch(list)
    }

    private fun selectEndpointItem(endpoint: Endpoint) {
        items.value
            .filterIsInstance(StageItem::class.java)
            .forEach {
                if (it.endpoint == endpoint) {
                    it.select()
                } else {
                    it.deselect()
                }
            }
    }
}