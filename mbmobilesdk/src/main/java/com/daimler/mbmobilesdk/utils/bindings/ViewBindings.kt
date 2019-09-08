package com.daimler.mbmobilesdk.utils.bindings

import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.BindingAdapter
import com.daimler.mbmobilesdk.R

@BindingAdapter("animateRotation")
fun animateRotation(view: View, rotate: Boolean) {
    if (rotate) {
        view.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.rotate_indefinitely))
    } else {
        view.clearAnimation()
    }
}

@BindingAdapter("dynamicHeightChange")
fun setHeightParams(view: View, visible: Boolean) {
    val params = view.layoutParams
    if (visible) {
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
    } else {
        params.height = 0
    }
    view.requestLayout()
}