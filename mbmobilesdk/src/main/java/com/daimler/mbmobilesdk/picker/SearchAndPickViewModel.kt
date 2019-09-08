package com.daimler.mbmobilesdk.picker

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.LineDividerDecorator
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.eventbus.EventBus
import com.daimler.mbuikit.eventbus.Observes
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.getColor
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

internal class SearchAndPickViewModel(
    app: Application,
    val title: String,
    values: Map<String, String>,
    initialSelection: String?
) : AndroidViewModel(app) {

    val progressVisible = mutableLiveDataOf(false)
    val applyVisible = mutableLiveDataOf(false)
    val items = MutableLiveArrayList<SearchAndPickItem>()
    val scrollPosition = mutableLiveDataOf(0)

    val valueSelectedEvent = MutableLiveEvent<Pair<String, String>>()
    val cancelEvent = MutableLiveUnitEvent()
    val clearSearchEvent = MutableLiveUnitEvent()

    private lateinit var rawItems: List<SearchAndPickItem>
    private var selectedPair: Pair<String, String>? = null

    init {
        createItemList(values, initialSelection)

        EventBus.createStation(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.dismount(this)
    }

    fun getDecorator() = LineDividerDecorator(getColor(R.color.mb_grey2))

    fun onApplyClicked() {
        selectedPair?.let { valueSelectedEvent.sendEvent(it) }
    }

    fun onCancelClicked() {
        cancelEvent.sendEvent()
    }

    fun onTextChanged(editable: Editable) {
        val searchText = editable.toString()

        // Set the scroll position to zero if we the search is empty
        if (searchText.isEmpty()) {
            scrollPosition.postValue(0)
        }

        resetSelection()
        items.value.clear()
        items.addAllAndDispatch(rawItems.filter { it.value.toLowerCase().contains(searchText.toLowerCase()) })
    }

    fun onClearSearch() {
        resetSelection()
        items.clearAndDispatch()
        items.addAllAndDispatch(rawItems)
        scrollPosition.postValue(0)
        clearSearchEvent.sendEvent()
    }

    private fun resetSelection() {
        applySelection("", "", false)
    }

    private fun createItemList(values: Map<String, String>, initialSelection: String?) {
        rawItems = values.map {
            val item = SearchAndPickItem(it.key, it.value)
            if (initialSelection == it.key || initialSelection == it.value) {
                item.selected.set(true)
                applySelection(it.key, it.value, true)
            }
            item
        }.sortedBy { it.value }

        items.value.clear()
        items.addAllAndDispatch(rawItems)

        adjustScrolling()
    }

    private fun applySelection(key: String, value: String, selected: Boolean) {
        items.value.forEach { it.selected.set(it.value == value) }
        selectedPair = if (selected) Pair(key, value) else null
        applyVisible.postValue(selected)
    }

    private fun adjustScrolling() {
        selectedPair?.second?.let { value ->
            val index = items.value.indexOfFirst { it.value == value }
            if (index != -1) scrollPosition.postValue(index)
        }
    }

    @Observes
    @Suppress("UNUSED")
    private fun onItemSelected(event: SearchItemPickedEvent) {
        applySelection(event.key, event.value, event.selected)
    }
}