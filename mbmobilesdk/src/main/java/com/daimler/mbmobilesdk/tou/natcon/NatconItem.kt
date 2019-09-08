package com.daimler.mbmobilesdk.tou.natcon

import android.text.SpannableString
import android.text.method.LinkMovementMethod
import androidx.databinding.ObservableBoolean
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.ClickAction
import com.daimler.mbmobilesdk.utils.extensions.applyClickAction
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem
import com.daimler.mbuikit.eventbus.EventBus

internal class NatconItem(
    val id: String,
    text: String
) : MBBaseRecyclerItem() {

    val text = makeClickable(text)
    val errorVisible = ObservableBoolean(false)
    val checked = ObservableBoolean(false)
    val movementMethod = LinkMovementMethod.getInstance()

    var errorState = false
        set(value) {
            field = value
            errorVisible.set(value)
        }

    override fun getLayoutRes(): Int = R.layout.item_natcon

    override fun getModelId(): Int = BR.item

    fun onCheckedChanged(checked: Boolean) {
        if (checked) errorState = false
        EventBus.fuel(NatconAcceptedEvent(id, checked))
    }

    fun updateCheckedState(checked: Boolean) {
        this.checked.set(checked)
    }

    private fun makeClickable(text: String): CharSequence {
        val spannable = SpannableString(text)
        val action = ClickAction(text) { EventBus.fuel(NatconReadEvent(id)) }
        return spannable.applyClickAction(action)
    }
}