package com.daimler.mbmobilesdk.utils.extensions

import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> T.post(action: T.() -> Unit) {
    executePendingBindings()
    action()
}