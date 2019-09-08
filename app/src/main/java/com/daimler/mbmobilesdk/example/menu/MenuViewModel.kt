package com.daimler.mbmobilesdk.example.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {

    val text = MutableLiveData<String>()

    init {
        text.value = "Menu"
    }
}