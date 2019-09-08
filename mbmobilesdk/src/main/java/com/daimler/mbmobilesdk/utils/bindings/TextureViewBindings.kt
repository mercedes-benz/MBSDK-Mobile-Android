package com.daimler.mbmobilesdk.utils.bindings

import android.view.TextureView
import androidx.databinding.BindingAdapter

@BindingAdapter("surfaceListener")
fun TextureView.setSurfaceListener(listener: TextureView.SurfaceTextureListener) {
    surfaceTextureListener = listener
}