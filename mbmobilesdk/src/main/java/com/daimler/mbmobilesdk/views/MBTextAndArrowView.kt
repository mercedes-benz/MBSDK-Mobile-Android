package com.daimler.mbmobilesdk.views

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.utils.extensions.getBoolean
import com.daimler.mbuikit.utils.extensions.obtainTypedArrayOrNull
import kotlinx.android.synthetic.main.view_text_and_arrow.view.*

internal class MBTextAndArrowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_text_and_arrow, this, true)

        val array: TypedArray? = obtainTypedArrayOrNull(attrs, R.styleable.MBTextAndArrowView)
        val text = array?.getText(R.styleable.MBTextAndArrowView_text)
        val showDivider = array.getBoolean(R.styleable.MBTextAndArrowView_showDivider, true)
        setText(text)
        setShowDivider(showDivider)
        array?.recycle()
    }

    fun setText(text: CharSequence?) {
        tv_text.text = text
    }

    fun setShowDivider(showDivider: Boolean) {
        divider.visibility = if (showDivider) View.VISIBLE else View.GONE
    }
}