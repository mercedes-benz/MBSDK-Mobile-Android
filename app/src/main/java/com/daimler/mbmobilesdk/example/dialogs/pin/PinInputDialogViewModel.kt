package com.daimler.mbmobilesdk.example.dialogs.pin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal class PinInputDialogViewModel : ViewModel() {

    val pin = MutableLiveData<String>()
    val confirmEnabled: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(pin) {
            value = it?.length == PIN_LENGTH
        }
    }

    val confirmEvent = MutableLiveData<String>()
    val cancelEvent = MutableLiveData<Unit>()

    fun onConfirmClicked() {
        confirmEvent.value = pin.value
    }

    fun onCancelClicked() {
        cancelEvent.value = Unit
    }

    companion object {

        const val PIN_LENGTH = 4
    }
}
