package com.daimler.mbmobilesdk.notificationcenter.presentation.messageoverview

import androidx.recyclerview.widget.DiffUtil
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerAdapter

class InboxRecyclerAdapter : MBBaseRecyclerAdapter<InboxMessageItem>() {

    private val items: MutableList<InboxMessageItem> = mutableListOf()

    fun updateItems(updatedItems: List<InboxMessageItem>) {
        val diffResult = DiffUtil.calculateDiff(InboxDiffCallback(items, updatedItems))
        items.clear()
        items.addAll(updatedItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItem(position: Int): InboxMessageItem {
        return items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class InboxDiffCallback(private val oldMessages: List<InboxMessageItem>, private val newMessages: List<InboxMessageItem>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMessages[oldItemPosition].message.key == newMessages[newItemPosition].message.key
        }

        override fun getOldListSize(): Int {
            return oldMessages.size
        }

        override fun getNewListSize(): Int {
            return newMessages.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMessages[oldItemPosition] == newMessages[newItemPosition]
        }
    }
}