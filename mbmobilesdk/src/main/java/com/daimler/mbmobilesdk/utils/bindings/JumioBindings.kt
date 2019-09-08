package com.daimler.mbmobilesdk.utils.bindings

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter

@BindingAdapter("items")
fun bindItemsToListAdapter(list: Spinner, items: MutableList<String>) {
    val arrayAdapter = ArrayAdapter(list.context, android.R.layout.simple_spinner_item, items)
    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    list.adapter = arrayAdapter
}

@BindingAdapter("selection")
fun setAdapter(
    view: AdapterView<*>,
    newSelection: Int
) {
    if (view.selectedItem != newSelection) {
        view.post { view.setSelection(newSelection) }
    }
}