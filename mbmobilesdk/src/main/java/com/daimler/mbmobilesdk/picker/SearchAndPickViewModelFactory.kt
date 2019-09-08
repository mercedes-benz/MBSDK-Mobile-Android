package com.daimler.mbmobilesdk.picker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class SearchAndPickViewModelFactory(
    private val app: Application,
    private val title: String,
    private val values: Map<String, String>,
    private val initialSelection: String?
) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchAndPickViewModel(app, title, values, initialSelection) as T
    }
}