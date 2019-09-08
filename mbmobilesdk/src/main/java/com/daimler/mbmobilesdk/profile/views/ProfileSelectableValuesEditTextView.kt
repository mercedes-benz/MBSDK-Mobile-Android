package com.daimler.mbmobilesdk.profile.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.views.MBSelectableValuesEditTextView
import kotlinx.android.synthetic.main.view_profile_selectable_values_base_edit_text.view.*

internal class ProfileSelectableValuesEditTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseProfileView(context, attrs, defStyleAttr) {

    private var currentValue: String? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_profile_selectable_values_base_edit_text, this, true)

        selectable_values_edit_text.setCallback(object : MBSelectableValuesEditTextView.SelectableValuesCallback {
            override fun onValueChanged(value: String?) {
                currentValue = value
                callback?.onValueChanged(value)
            }
        })
    }

    override fun applyValue(value: String?) {
        currentValue = value
        selectable_values_edit_text.setKeySelection(currentValue)
    }

    override fun currentValue(): String? {
        return currentValue
    }

    private fun setEmptyItem(item: String?) {
        selectable_values_edit_text.setEmptyItem(item)
    }

    private fun setHint(hint: String?) {
        selectable_values_edit_text.setHint(hint)
    }

    private fun setItems(items: Map<String, String>) {
        selectable_values_edit_text.setItems(items)
    }

    private fun setKeySelection(key: String?) {
        key?.let {
            applyValue(it)
        }
    }

    private fun setNotifyKeys(notifyKeys: Boolean) {
        selectable_values_edit_text.setNotifyKeys(notifyKeys)
    }

    class Builder {
        private var emptyItem: String? = null
        private var hint: String? = null
        private var items = mapOf<String, String>()
        private var key: String? = null
        private var notifyKeys = true

        fun withEmptyItem(emptyItem: String?): Builder {
            this.emptyItem = emptyItem
            return this
        }

        fun withHint(hint: String?): Builder {
            this.hint = hint
            return this
        }

        fun withItems(items: Map<String, String>): Builder {
            this.items = items
            return this
        }

        fun withKeySelection(key: String?): Builder {
            this.key = key
            return this
        }

        fun withNotifyKeys(notifyKeys: Boolean): Builder {
            this.notifyKeys = notifyKeys
            return this
        }

        fun build(context: Context): ProfileSelectableValuesEditTextView {
            return ProfileSelectableValuesEditTextView(context).apply {
                setEmptyItem(emptyItem)
                setHint(hint)
                setItems(items)
                setKeySelection(key)
                setNotifyKeys(notifyKeys)
            }
        }
    }
}