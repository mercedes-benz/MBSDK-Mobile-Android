package com.daimler.mbmobilesdk.example.utils

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MultipleRecyclerItemListAdapter<T : MultipleRecyclerItem> :
    ListAdapter<T, BindableViewHolder>(DiffCallback()),
    BindableAdapter<T> {

    override val viewHolderList = ArrayList<BindableViewHolder>()

    class DiffCallback<T : MultipleRecyclerItem> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.getIdentifier() == newItem.getIdentifier()
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).getLayoutRes()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = bindableViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) = bindViewHolder(position, holder)

    override fun getItem(position: Int): T = super.getItem(position)

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        markAllViewHolderDestroyed()
    }
}
