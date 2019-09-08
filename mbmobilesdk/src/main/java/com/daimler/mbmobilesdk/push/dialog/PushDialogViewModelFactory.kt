package com.daimler.mbmobilesdk.push.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbmobilesdk.push.PushData

internal class PushDialogViewModelFactory(
    private val pushData: PushData
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PushDialogViewModel(pushData) as T
    }
}