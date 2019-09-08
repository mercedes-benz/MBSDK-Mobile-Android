package com.daimler.mbmobilesdk.serviceactivation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.daimler.mbmobilesdk.R
import kotlinx.android.synthetic.main.view_edit_text_missing_field.view.*

internal class EditTextMissingFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseMissingFieldView(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_edit_text_missing_field, this, true)
        btn_confirm.setOnClickListener {
            callback?.onValueChanged(edit_value_content.text?.toString())
        }
    }

    fun setTitle(title: String?) {
        tv_title.text = title
    }

    fun setHint(hint: String?) {
        edit_value.hint = hint
    }

    fun setValue(value: String?) {
        edit_value_content.setText(value)
    }

    fun setInputType(inputType: Int) {
        edit_value_content.inputType = inputType
    }
}