package com.daimler.mbmobilesdk.example.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

interface BindableAdapter<T : MultipleRecyclerItem> {
    val viewHolderList: ArrayList<BindableViewHolder>

    fun bindableViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        val viewHolder = BindableViewHolder(binding)
        viewHolderList.add(viewHolder)
        return viewHolder
    }

    fun bindViewHolder(position: Int, holder: BindableViewHolder) {
        val item = getItem(position)
        if (!holder.binding.setVariable(item.getModelId(), item)) {
            throw RuntimeException("the variable name of your xml layout is not 'item' or you pass the wrong bindingRes-attribute")
        }

        if (holder.binding.lifecycleOwner == null) {
            holder.binding.lifecycleOwner = holder
        }

        holder.binding.executePendingBindings()
    }

    fun getItem(position: Int): T

    fun markAllViewHolderDestroyed() {
        viewHolderList.forEach { vh -> vh.markDestroyed() }
    }
}
