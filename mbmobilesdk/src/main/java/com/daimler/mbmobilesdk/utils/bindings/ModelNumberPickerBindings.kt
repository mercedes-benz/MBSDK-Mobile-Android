package com.daimler.mbmobilesdk.utils.bindings

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.views.ModelNumberPicker
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList

@BindingAdapter("liveItems")
fun <T : ModelNumberPicker.Pickable> ModelNumberPicker.setItems(items: MutableLiveArrayList<T>?) {
    items?.let { setDisplayItems(it.value) }
}

@BindingAdapter("initialSelection")
fun ModelNumberPicker.setInitialSelection(initialSelection: MutableLiveData<String>) {
    initialSelection.value?.let { setDisplayedValue(it) }
}