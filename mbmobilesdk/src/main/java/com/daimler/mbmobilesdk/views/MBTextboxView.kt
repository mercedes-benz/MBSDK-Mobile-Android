package com.daimler.mbmobilesdk.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.daimler.mbmobilesdk.R
import kotlinx.android.synthetic.main.view_mb_textbox.view.*

class MBTextboxView : FrameLayout {

    constructor(context: Context) : super(context) {
        initialize(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr) {
        initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.view_mb_textbox, this, true)
        background = ContextCompat.getDrawable(context, R.drawable.bg_overlay_textbox)
    }

    fun setText(text: String?) {
        tv_main.text = text
    }
}