package com.daimler.mbmobilesdk.profile.items

import androidx.databinding.ObservableBoolean
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R

internal class ProfileFieldCheckBoxItem(
    val title: String,
    initialChecked: Boolean
) : BaseProfileRecyclerItem() {

    val checked = ObservableBoolean(initialChecked)

    override fun getLayoutRes(): Int = R.layout.item_profile_field_check_box

    override fun getModelId(): Int = BR.model

    override fun applyValue(value: String?) {
        checked.set(value != null)
    }

    override fun currentValue(): String? {
        return if (checked.get()) "" else null
    }

    fun onCheckedChanged(checked: Boolean) {
        // TODO generic callback?
        this.checked.set(checked)
        callback?.onValueChanged(if (checked) "" else null)
    }
}