package com.daimler.mbmobilesdk.profile.items

import androidx.databinding.ObservableField
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R

internal class ProfileSelectableValuesItem(
    val items: Map<String, String>,
    val hint: String?,
    val emptyItem: String?,
    initialKeySelection: String?,
    val notifyKeys: Boolean
) : BaseProfileRecyclerItem() {

    val keySelection = ObservableField(initialKeySelection)
    private var currentValue: String? = initialKeySelection

    override fun getLayoutRes(): Int = R.layout.item_profile_selectable_values

    override fun getModelId(): Int = BR.model

    override fun applyValue(value: String?) {
        currentValue = value
        keySelection.set(value)
    }

    override fun currentValue(): String? {
        return currentValue
    }

    fun onValueChanged(value: String?) {
        currentValue = value
        callback?.onValueChanged(value)
    }
}