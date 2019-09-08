package com.daimler.mbmobilesdk.profile.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.daimler.mbmobilesdk.R
import kotlinx.android.synthetic.main.view_profile_value_text_single_line.view.*

internal class ProfileValueSingleLineTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseProfileView(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_profile_value_text_single_line, this, true)
        setOnClickListener { callback?.onValueChanged(null) }
    }

    override fun applyValue(value: String?) {
        tv_content.text = value
    }

    override fun currentValue(): String? {
        return tv_content.text?.toString()
    }

    private fun setTitle(title: String?) {
        tv_title.text = title
    }

    private fun setContent(content: String?) {
        tv_content.text = content
    }

    private fun setActive(active: Boolean) {
        if (!active) {
            tv_content.setTextColor(ContextCompat.getColor(context, R.color.mb_grey2))
        }
    }

    class Builder {
        private var title: String? = null
        private var content: String? = null
        private var active: Boolean = true

        fun withTitle(title: String?): Builder {
            this.title = title
            return this
        }

        fun withContent(content: String?): Builder {
            this.content = content
            return this
        }

        fun withActive(active: Boolean): Builder {
            this.active = active
            return this
        }

        fun build(context: Context): ProfileValueSingleLineTextView {
            return ProfileValueSingleLineTextView(context).apply {
                setTitle(title)
                setContent(content)
                setActive(active)
            }
        }
    }
}