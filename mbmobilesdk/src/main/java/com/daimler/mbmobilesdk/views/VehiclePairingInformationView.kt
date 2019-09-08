package com.daimler.mbmobilesdk.views

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.daimler.mbmobilesdk.R
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbuikit.utils.extensions.getInt
import com.daimler.mbuikit.utils.extensions.obtainTypedArrayOrNull

internal class VehiclePairingInformationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        applyAttrsAndLayout(attrs)
    }

    private fun applyAttrsAndLayout(attrs: AttributeSet?) {
        val array: TypedArray? = obtainTypedArrayOrNull(attrs, R.styleable.VehiclePairingInformationView)
        val style: Int = array.getInt(R.styleable.VehiclePairingInformationView_infoStyle, STYLE_BODY)
        array?.recycle()
        inflateLayoutForStyle(style)
    }

    private fun inflateLayoutForStyle(style: Int) {
        @LayoutRes val res = when (style) {
            STYLE_BODY -> R.layout.view_vehicle_pairing_information_body
            STYLE_SCREEN -> R.layout.view_vehicle_pairing_information_screen
            else -> {
                MBLoggerKit.e("Unknown style: $style.")
                R.layout.view_vehicle_pairing_information_body
            }
        }
        LayoutInflater.from(context).inflate(res, this, true)
    }

    private companion object {
        private const val STYLE_BODY = 0
        private const val STYLE_SCREEN = 1
    }
}