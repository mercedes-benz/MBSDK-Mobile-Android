package com.daimler.mbmobilesdk.languageselection

import androidx.databinding.ObservableBoolean
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem
import com.daimler.mbuikit.eventbus.EventBus
import com.daimler.mbuikit.utils.extensions.invert

class LanguageSelectionItem(
    val index: Int,
    val language: String,
    val languageCode: String
) : MBBaseRecyclerItem() {

    val selected = ObservableBoolean(false)

    override fun getLayoutRes(): Int = R.layout.item_language_selection

    override fun getModelId(): Int = BR.item

    fun onItemSelected() {
        selected.invert()
        sendEvent()
    }

    fun selectAndNotify() {
        selected.set(true)
        sendEvent()
    }

    fun deselect() = selected.invert()

    private fun sendEvent() =
        EventBus.fuel(LanguageSelectionEvent(index, languageCode, selected.get()))
}