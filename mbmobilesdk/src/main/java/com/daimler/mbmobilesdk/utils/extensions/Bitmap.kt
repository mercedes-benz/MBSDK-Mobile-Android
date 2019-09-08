package com.daimler.mbmobilesdk.utils.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import java.io.ByteArrayOutputStream

fun Bitmap.toBitmapDrawable(context: Context) =
    BitmapDrawable(context.resources, this)

fun Bitmap.toBitmapDrawable(resources: Resources) =
    BitmapDrawable(resources, this)

fun Bitmap.toByteArray(quality: Int): ByteArray {
    return ByteArrayOutputStream().also {
        this.compress(Bitmap.CompressFormat.JPEG, quality, it)
    }.toByteArray()
}