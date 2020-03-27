package com.daimler.mbmobilesdk.example.dialogs.error

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class ErrorDialogViewModelFactory(
    private val title: String?,
    private val msg: String?
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ErrorDialogViewModel(title, msg) as T
}
