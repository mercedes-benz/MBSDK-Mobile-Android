package com.daimler.mbmobilesdk.vehicleassignment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchMerchantViewModelFactory(private val app: Application) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchMerchantViewModel(app) as T
    }
}