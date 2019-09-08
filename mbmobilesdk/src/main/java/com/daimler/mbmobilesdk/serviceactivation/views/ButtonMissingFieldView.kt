package com.daimler.mbmobilesdk.serviceactivation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.daimler.mbmobilesdk.R
import kotlinx.android.synthetic.main.view_button_missing_field.view.*

internal class ButtonMissingFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseMissingFieldView(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_button_missing_field, this, true)
        btn_action.setOnClickListener {
            callback?.onValueChanged(null)
        }
    }

    fun setTitle(title: String?) {
        tv_title.text = title
    }

    fun setButtonText(text: String?) {
        btn_action.text = text
    }
}