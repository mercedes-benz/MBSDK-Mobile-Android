package com.daimler.mbmobilesdk.serviceactivation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.daimler.mbmobilesdk.R
import kotlinx.android.synthetic.main.view_text_missing_field.view.*

internal class TextMissingFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseMissingFieldView(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_text_missing_field, this, true)
    }

    fun setText(text: String?) {
        tv_title.text = text
    }
}