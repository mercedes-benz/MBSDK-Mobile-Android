package com.daimler.mbmobilesdk.tou.soe

import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import androidx.databinding.ObservableBoolean
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.ClickAction
import com.daimler.mbmobilesdk.utils.extensions.applyClickActions
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbuikit.eventbus.EventBus

internal class SoeAgreementItem(
    val ids: List<String>,
    initialChecked: Boolean,
    description: String,
    links: List<SoeAgreementTextLink>
) : SoeRecyclerItem(TYPE_AGREEMENT) {

    val enabled = ObservableBoolean(true)

    val linkMovementMethod: MovementMethod = LinkMovementMethod.getInstance()
    val text = createClickableText(description, links)

    val checked = ObservableBoolean(initialChecked)

    override fun getLayoutRes(): Int = R.layout.item_soe_agreement

    override fun getModelId(): Int = BR.item

    fun onCheckedChanged(checked: Boolean) {
        if (this.checked.get() != checked) {
            this.checked.set(checked)
            EventBus.fuel(SoeAgreementAcceptanceStateEvent(ids, checked))
        }
    }

    fun updateEnabledState(enabled: Boolean) {
        MBLoggerKit.d("Update enabled state to $enabled.")
        this.enabled.set(enabled)
    }

    fun updateCheckedState(checked: Boolean) {
        MBLoggerKit.d("Update checked state to $checked.")
        this.checked.set(checked)
    }

    private fun createClickableText(description: String, links: List<SoeAgreementTextLink>): CharSequence {
        val spannable = SpannableString(description)
        val clickActions = links.map {
            ClickAction(it.clickableText) { EventBus.fuel(SoeAgreementReadEvent(it.documentId)) }
        }
        spannable.applyClickActions(clickActions)
        return spannable
    }
}