package com.daimler.mbmobilesdk.familyapps

import androidx.databinding.ObservableBoolean
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbdeeplinkkit.common.FamilyApp
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem
import com.daimler.mbuikit.utils.extensions.invert

class AppChooserItem(
    private val app: FamilyApp,
    private val events: Events
) : MBBaseRecyclerItem() {

    val name: String? = app.name
    val description: String? = app.description
    val imageUrl: String? = app.iconUrl

    val isExpanded = ObservableBoolean(false)

    override fun getLayoutRes(): Int = R.layout.item_choose_app

    override fun getModelId(): Int = BR.item

    fun onItemClicked() {
        events.onAppClicked(app)
    }

    fun onDescriptionClicked() {
        isExpanded.invert()
    }

    interface Events {
        fun onAppClicked(app: FamilyApp)
    }
}