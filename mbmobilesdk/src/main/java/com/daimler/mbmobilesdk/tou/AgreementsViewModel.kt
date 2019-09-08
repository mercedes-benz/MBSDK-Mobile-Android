package com.daimler.mbmobilesdk.tou

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class AgreementsViewModel(app: Application) : AndroidViewModel(app) {

    val toolbarTitle = MutableLiveData<String>()

    fun updateToolbarTitle(toolbarTitle: String) {
        this.toolbarTitle.postValue(toolbarTitle)
    }
}