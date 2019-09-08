package com.daimler.mbmobilesdk.views

import android.content.Context
import android.util.AttributeSet
import com.daimler.mbuikit.widgets.layouts.MBElevatedConstraintLayout

internal class GarageItemLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MBElevatedConstraintLayout(context, attrs, defStyleAttr) {

    var userVisible: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                changeUserVisible(value)
            }
        }

    init {
        scaleX = SCALING_FACTOR
        scaleY = SCALING_FACTOR
    }

    private fun changeUserVisible(userVisible: Boolean) {
        doScaling(if (userVisible) 1f else SCALING_FACTOR)
        isSelected = userVisible
    }

    private fun doScaling(factor: Float) {
        animate()
            .scaleX(factor)
            .scaleY(factor)
            .start()
    }

    private companion object {

        private const val SCALING_FACTOR = 0.8f
    }
}