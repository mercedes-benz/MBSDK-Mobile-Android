package com.daimler.mbmobilesdk.example.dialogs.error

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal class ErrorDialogViewModel(
    val title: String?,
    val message: String?
) : ViewModel() {

    val confirmEvent = MutableLiveData<Unit>()

    fun onOkayClicked() {
        confirmEvent.value = Unit
    }
}
