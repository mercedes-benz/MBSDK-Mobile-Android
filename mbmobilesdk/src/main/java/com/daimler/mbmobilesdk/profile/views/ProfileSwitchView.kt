package com.daimler.mbmobilesdk.profile.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.daimler.mbmobilesdk.R
import kotlinx.android.synthetic.main.item_profile_field_check_box.view.tv_title
import kotlinx.android.synthetic.main.view_profile_field_switch.view.*

internal class ProfileSwitchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseProfileView(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_profile_field_switch, this, true)

        profile_switch.setOnCheckedChangeListener { _, isChecked ->
            setChecked(isChecked)
            callback?.onValueChanged(if (isChecked) "" else null)
        }
    }

    override fun applyValue(value: String?) {
        setChecked(value != null)
    }

    override fun currentValue(): String? {
        return if (profile_switch.isChecked) "" else null
    }

    private fun setTitle(title: String?) {
        tv_title.text = title
    }

    private fun setChecked(checked: Boolean) {
        profile_switch.isChecked = checked
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

        fun build(context: Context): ProfileSwitchView {
            return ProfileSwitchView(context).apply {
                setTitle(title)
                setChecked(isChecked)
            }
        }
    }
}