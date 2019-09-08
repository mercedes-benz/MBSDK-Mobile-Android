package com.daimler.mbmobilesdk.profile.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.widgets.layouts.MBElevatedLinearLayout
import kotlinx.android.synthetic.main.view_profile_section.view.*

internal class MBProfileSectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MBElevatedLinearLayout(context, attrs, defStyleAttr) {

    var title: String? = null
        set(value) {
            field = value
            tv_title.text = value
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_profile_section, this, true)
        orientation = VERTICAL
    }
}