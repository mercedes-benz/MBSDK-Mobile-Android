package com.daimler.mbmobilesdk.jumio.dialogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class JumioTermsDialogViewModelFactory(
    private val title: String?,
    private val msg: String?,
    private val positiveButton: String?,
    private val negativeButton: String?
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        JumioTermsDialogViewModel(title, msg, positiveButton, negativeButton, false) as T
}