package com.daimler.mbmobilesdk.example.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

open class MutableLiveEvent<T> : MutableLiveData<LiveEvent<T>>() {

    fun sendEvent(value: T) {
        this.value = LiveEvent(value)
    }
}

class MutableLiveUnitEvent : MutableLiveEvent<Unit>() {

    fun sendEvent() = sendEvent(Unit)
}

fun <T> MutableLiveEvent<T>.observe(lifecycleOwner: LifecycleOwner, action: (T) -> Unit) {
    observe(
        lifecycleOwner,
        Observer { event ->
            event?.getContentIfNotHandled()?.let(action)
        }
    )
}
