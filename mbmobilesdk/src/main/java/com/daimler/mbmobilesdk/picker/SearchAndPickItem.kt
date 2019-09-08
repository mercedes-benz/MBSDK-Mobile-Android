package com.daimler.mbmobilesdk.picker

import androidx.databinding.ObservableBoolean
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem
import com.daimler.mbuikit.eventbus.EventBus
import com.daimler.mbuikit.utils.extensions.invert

internal class SearchAndPickItem(
    private val key: String,
    val value: String
) : MBBaseRecyclerItem() {

    val selected = ObservableBoolean(false)

    override fun getLayoutRes(): Int = R.layout.item_search_and_pick

    override fun getModelId(): Int = BR.model

    fun onItemClicked() {
        val newState = selected.invert()
        EventBus.fuel(SearchItemPickedEvent(key, value, newState))
    }
}