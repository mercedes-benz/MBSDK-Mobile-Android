package com.daimler.mbmobilesdk.utils.bindings

import android.graphics.Bitmap
import android.net.Uri
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("uri")
fun setImageUri(imageView: ImageView, uri: Uri?) {
    uri?.let { imageView.setImageURI(it) }
}

@BindingAdapter("bitmap")
fun setImageBitmap(imageView: ImageView, bitmap: Bitmap?) {
    bitmap?.let { imageView.setImageBitmap(bitmap) }
}

@BindingAdapter("imageUrl")
fun loadImageFromUrl(view: ImageView, url: String?) {
    Glide.with(view.context).load(url).into(view)
}

@BindingAdapter("rotate")
fun ImageView.rotateImage(shouldRotate: Boolean) {
    doRotateImage(if (shouldRotate) 90f else 0f)
}

@BindingAdapter("rotateOpposite")
fun ImageView.rotateImageOpposite(opposite: Boolean) {
    doRotateImage(if (opposite) 180f else 0f)
}

private fun ImageView.doRotateImage(angle: Float) {
    animate().rotation(angle).apply {
        interpolator = AccelerateDecelerateInterpolator()
    }
}

@BindingAdapter("android:src")
fun setImageFromRes(view: ImageView, @DrawableRes drawableRes: Int) {
    view.setImageResource(drawableRes)
}

@BindingAdapter("colorFilter")
fun setColorFilter(view: ImageView, @ColorInt color: Int) {
    view.setColorFilter(color)
}