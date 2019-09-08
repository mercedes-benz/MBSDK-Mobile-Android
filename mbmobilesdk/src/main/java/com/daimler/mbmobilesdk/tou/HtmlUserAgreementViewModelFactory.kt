package com.daimler.mbmobilesdk.tou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class HtmlUserAgreementViewModelFactory(
    private val title: String,
    private val htmlAgreement: String
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HtmlUserAgreementViewModel(title, htmlAgreement) as T
    }
}