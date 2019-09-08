package com.daimler.mbmobilesdk.profile.items

import android.text.Editable
import androidx.databinding.ObservableField
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.profile.fields.ProfileValueCallback

internal class ProfileEditTextItem(
    initialContent: String?,
    var hint: String?,
    var hasNext: Boolean,
    mode: Mode = Mode.TEXT,
    val inputType: Int,
    val isMandatory: Boolean
) : BaseProfileRecyclerItem() {

    private val delegate: Delegate = Delegate.create(mode)

    val editable = delegate.editable
    val content = ObservableField(initialContent)

    init {
        if (isMandatory) hint = hint?.plus(MANDATORY_CHAR)
    }

    override fun getLayoutRes(): Int = R.layout.item_profile_edit_text

    override fun getModelId(): Int = BR.model

    override fun applyValue(value: String?) {
        content.set(value)
    }

    override fun currentValue(): String? {
        return content.get()
    }

    fun onTextChanged(editable: Editable) {
        delegate.textChanged(callback, editable)
    }

    fun onItemClicked() {
        delegate.itemClicked(callback)
    }

    fun onFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            delegate.focusReceived(callback)
        }
    }

    enum class Mode { TEXT, CLICK }

    private sealed class Delegate {

        abstract val editable: Boolean

        open fun textChanged(callback: ProfileValueCallback?, editable: Editable) = Unit
        open fun itemClicked(callback: ProfileValueCallback?) = Unit
        open fun focusReceived(callback: ProfileValueCallback?) = Unit

        private class TextDelegate : Delegate() {

            override val editable: Boolean = true

            override fun textChanged(callback: ProfileValueCallback?, editable: Editable) {
                callback?.onValueChanged(editable.toString())
            }
        }

        private class ClickDelegate : Delegate() {

            override val editable: Boolean = false

            override fun itemClicked(callback: ProfileValueCallback?) {
                callback?.onValueChanged(null)
            }

            override fun focusReceived(callback: ProfileValueCallback?) {
                callback?.onValueChanged(null)
            }
        }

        companion object {
            fun create(mode: Mode): Delegate =
                when (mode) {
                    Mode.TEXT -> TextDelegate()
                    Mode.CLICK -> ClickDelegate()
                }
        }
    }
}