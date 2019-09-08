package com.daimler.mbmobilesdk.utils.extensions

import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition

internal fun <T : View, R> RequestBuilder<R>.onResourceReady(view: T, action: (T, R) -> Unit): CustomViewTarget<T, R> {
    return into(object : CustomViewTarget<T, R>(view) {
        override fun onLoadFailed(errorDrawable: Drawable?) = Unit

        override fun onResourceCleared(placeholder: Drawable?) = Unit

        override fun onResourceReady(resource: R, transition: Transition<in R>?) {
            action(view, resource)
        }
    })
}