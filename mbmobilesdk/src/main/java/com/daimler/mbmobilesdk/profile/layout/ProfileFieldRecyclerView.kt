package com.daimler.mbmobilesdk.profile.layout

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.items.BaseProfileRecyclerItem
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerAdapter

@SuppressLint("ViewConstructor")
internal class ProfileFieldRecyclerView(
    context: Context,
    adapter: ProfileFieldAdapter
) : RecyclerView(context) {

    init {
        super.setAdapter(adapter)
    }

    override fun getAdapter(): ProfileFieldAdapter {
        return super.getAdapter() as ProfileFieldAdapter
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        throw UnsupportedOperationException("Custom adapters are not allowed!")
    }

    /**
     * Basic adapter with a fixed item list.
     */
    class ProfileFieldAdapter(
        val items: List<BaseProfileRecyclerItem>
    ) : MBBaseRecyclerAdapter<BaseProfileRecyclerItem>() {

        override fun getItem(position: Int): BaseProfileRecyclerItem = items[position]

        override fun getItemCount(): Int = items.size

        inline fun <reified T : ProfileField> adjustItem(value: String?) {
            items.firstOrNull { it.associatedField() is T }?.applyValue(value)
        }
    }
}