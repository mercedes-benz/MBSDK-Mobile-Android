package com.daimler.mbmobilesdk.utils.bindings

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("emptyError")
fun TextView.setEmptyError(showError: Boolean) {
    // This only works when this view is not focusable.
    error = if (showError) "" else null
}