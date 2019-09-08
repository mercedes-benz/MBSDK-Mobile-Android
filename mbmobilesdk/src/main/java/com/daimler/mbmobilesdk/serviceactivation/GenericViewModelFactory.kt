package com.daimler.mbmobilesdk.serviceactivation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GenericViewModelFactory<V : ViewModel>(
    private val creator: () -> V
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}