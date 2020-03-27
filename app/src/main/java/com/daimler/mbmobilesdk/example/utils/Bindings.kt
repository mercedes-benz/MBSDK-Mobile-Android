package com.daimler.mbmobilesdk.example.utils

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingConversion
fun convertBooleanToVisibility(visible: Boolean) = if (visible) View.VISIBLE else View.GONE

@Suppress("UNCHECKED_CAST")
@BindingAdapter("listAdapterItems")
fun <T : MultipleRecyclerItem> RecyclerView.bindItemsToListAdapter(
    items: MutableList<T>?
) {
    items?.let {
        if (layoutManager == null) {
            layoutManager = LinearLayoutManager(context)
        }

        if (adapter is MultipleRecyclerItemListAdapter<*>) {
            (adapter as MultipleRecyclerItemListAdapter<T>).submitList(ArrayList(items))
        } else {
            val newAdapter = MultipleRecyclerItemListAdapter<T>()
            newAdapter.submitList(ArrayList(items))
            adapter = newAdapter
        }
    } ?: run {
        adapter = null
    }
}

@BindingAdapter("imageBitmap")
fun ImageView.loadImage(bitmap: Bitmap?) {
    bitmap?.let {
        setImageBitmap(bitmap)
    }
}
