package com.daimler.mbmobilesdk.profile.items

import androidx.databinding.ObservableField
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R

internal class ProfileValueTextViewItem(
    val title: String?,
    initialContent: String
) : BaseProfileRecyclerItem() {

    val content = ObservableField(initialContent)

    override fun getLayoutRes(): Int = R.layout.item_profile_value_text

    override fun getModelId(): Int = BR.model

    override fun applyValue(value: String?) {
        content.set(value)
    }

    override fun currentValue(): String? {
        return content.get()
    }

    fun onItemClicked() {
        callback?.onValueChanged(null)
    }
}