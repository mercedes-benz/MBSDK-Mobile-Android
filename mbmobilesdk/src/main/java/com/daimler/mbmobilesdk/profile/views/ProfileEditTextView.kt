package com.daimler.mbmobilesdk.profile.views

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.profile.fields.ProfileValueCallback
import com.daimler.mbmobilesdk.utils.bindings.nextOrDone
import com.daimler.mbmobilesdk.utils.bindings.setAllowInput
import com.daimler.mbuikit.utils.extensions.afterTextChanged
import kotlinx.android.synthetic.main.view_profile_edit_text.view.*

internal class ProfileEditTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseProfileView(context, attrs, defStyleAttr) {

    private var textMode = Mode.CLICK

    private var delegate: Delegate = Delegate.create(textMode)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_profile_edit_text, this, true)

        edit_text.isClickable = delegate.editable
        edit_text.setAllowInput(delegate.editable)

        setInputType(InputType.TYPE_CLASS_TEXT)

        edit_text.afterTextChanged { editable -> delegate.textChanged(callback, editable) }
        edit_text.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                delegate.focusReceived(callback)
            }
        }
        edit_text.setOnClickListener { delegate.itemClicked(callback) }
    }

    private fun setMode(mode: Mode) {
        textMode = mode
        delegate = Delegate.create(textMode)
        edit_text.isClickable = delegate.editable
        edit_text.setAllowInput(delegate.editable)
    }

    private fun setHint(hint: String?) {
        text_input_layout.hint = hint
    }

    private fun setInputType(inputType: Int) {
        edit_text.inputType = inputType
    }

    private fun setNextOrDone(hasNext: Boolean) {
        edit_text.nextOrDone(hasNext)
    }

    override fun applyValue(value: String?) {
        edit_text.setText(value)
    }

    override fun currentValue(): String? {
        return edit_text.text.toString()
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

    class Builder {
        private var hint: String? = null
        private var mode = Mode.CLICK
        private var inputType = InputType.TYPE_CLASS_TEXT
        private var hasNext = true
        private var content: String? = null
        private var mandatory: Boolean = false

        fun withHint(hint: String?): Builder {
            this.hint = hint
            return this
        }

        fun withMode(mode: Mode): Builder {
            this.mode = mode
            return this
        }

        fun withInputType(inputType: Int): Builder {
            this.inputType = inputType
            return this
        }

        fun withHasNext(hasNext: Boolean): Builder {
            this.hasNext = hasNext
            return this
        }

        fun withContent(content: String?): Builder {
            this.content = content
            return this
        }

        fun withMandatory(mandatory: Boolean): Builder {
            this.mandatory = mandatory
            return this
        }

        fun build(context: Context): ProfileEditTextView {
            return ProfileEditTextView(context).apply {
                if (mandatory) hint = hint?.plus(MANDATORY_CHAR)
                setHint(hint)
                setMode(mode)
                setInputType(inputType)
                setNextOrDone(hasNext)
                applyValue(content)
            }
        }
    }
}