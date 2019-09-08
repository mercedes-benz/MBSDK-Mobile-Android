package com.daimler.mbmobilesdk.utils.extensions

import android.text.Html
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.utils.isNougat

/**
 * Posts the same value again to notify observers.
 */
fun <T> MutableLiveData<T>.dispatch() {
    postValue(value)
}

internal fun MutableLiveData<CharSequence>.postFromHtml(htmlContent: String) {
    val html = if (isNougat()) {
        Html.fromHtml(htmlContent, 0)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(htmlContent)
    }
    postValue(html)
}