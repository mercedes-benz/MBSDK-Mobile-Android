package com.daimler.mbmobilesdk.profile.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.daimler.mbmobilesdk.R
import kotlinx.android.synthetic.main.item_profile_field_check_box.view.*

internal class ProfileCheckBoxView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseProfileView(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_profile_field_check_box, this, true)

        checkbox.setOnCheckedChangeListener { _, isChecked ->
            setChecked(isChecked)
            callback?.onValueChanged(if (isChecked) "" else null)
        }
    }

    override fun applyValue(value: String?) {
        setChecked(value != null)
    }

    override fun currentValue(): String? {
        return if (checkbox.isChecked) "" else null
    }

    private fun setTitle(title: String?) {
        tv_title.text = title
    }

    private fun setChecked(checked: Boolean) {
        checkbox.isChecked = checked
    }

    class Builder {
        private var isChecked = false
        private var title: String? = null

        fun withChecked(checked: Boolean): Builder {
            this.isChecked = checked
            return this
        }

        fun withTitle(title: String?): Builder {
            this.title = title
            return this
        }

        fun build(context: Context): ProfileCheckBoxView {
            return ProfileCheckBoxView(context).apply {
                setTitle(title)
                setChecked(isChecked)
            }
        }
    }
}