@file:Suppress("unused")

package com.daimler.mbmobilesdk.example.utils

import androidx.annotation.LayoutRes
import androidx.databinding.BaseObservable

abstract class MultipleRecyclerItem : BaseObservable() {
    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun getModelId(): Int

    open fun getIdentifier(): Int {
        return hashCode()
    }

    open fun onBind() {}
}
