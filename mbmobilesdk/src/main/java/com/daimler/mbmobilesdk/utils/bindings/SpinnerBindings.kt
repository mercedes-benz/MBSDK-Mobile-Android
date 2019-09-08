package com.daimler.mbmobilesdk.utils.bindings

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.daimler.mbmobilesdk.R

@BindingAdapter("items", "initialSelection")
fun setItems(spinner: Spinner, items: List<String>?, initialSelection: Int = 0) {
    if (items != null && items.isNotEmpty()) {
        spinner.visibility = View.VISIBLE
        val spinnerArrayAdapter = ArrayAdapter<String>(spinner.context,
            R.layout.item_login_spinner_row, items)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerArrayAdapter
        spinner.setSelection(initialSelection)
    } else {
        spinner.visibility = View.GONE
    }
}