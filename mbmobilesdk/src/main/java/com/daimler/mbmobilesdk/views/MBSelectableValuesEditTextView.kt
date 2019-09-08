package com.daimler.mbmobilesdk.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.daimler.mbmobilesdk.R
import kotlinx.android.synthetic.main.view_profile_selectable_values.view.*

internal class MBSelectableValuesEditTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var notifyKeys = true
    private var callback: SelectableValuesCallback? = null

    private val items = LinkedHashMap<String, String>()

    init {
        LayoutInflater.from(context).inflate(R.layout.view_mb_selectable_values_edit_text, this, true)

        spinner.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                items.remove(KEY_HINT_ITEM)
                fillAdapter(items.values)
            }
            false
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {
                callback?.onValueChanged(null)
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val item = parent.getItemAtPosition(position).toString()
                when {
                    position == 0 && items.containsKey(KEY_EMPTY_ITEM) ->
                        callback?.onValueChanged(null)
                    notifyKeys ->
                        callback?.onValueChanged(findKeyForValue(item))
                    else ->
                        callback?.onValueChanged(item)
                }
            }
        }
        spinner.adapter = createAdapter()
    }

    @Suppress("UNCHECKED_CAST")
    fun setItems(newItems: Map<String, String>) {
        val emptyItem = items[KEY_EMPTY_ITEM]
        val hintItem = items[KEY_HINT_ITEM]
        items.clear()
        hintItem?.let { items[KEY_HINT_ITEM] = it }
        emptyItem?.let { items[KEY_EMPTY_ITEM] = it }
        items.putAll(newItems)
        fillAdapter(items.values)
    }

    /**
     * Sets the selected item. If [notifyKeys] is true provide the key of the selected item,
     * provide the value otherwise.
     */
    fun setKeySelection(key: String?) {
        key?.let { k ->
            val value = if (notifyKeys) items[k] else k
            value?.let {
                val pos = adapter().getPosition(it)
                if (pos != -1) spinner.setSelection(pos)
            }
        }
    }

    fun setEmptyItem(item: String?) {
        item?.let {
            if (items.isNotEmpty()) {
                val copy = LinkedHashMap(items)
                items.clear()
                items[KEY_EMPTY_ITEM] = it
                items.putAll(copy)
                fillAdapter(items.values)
            } else {
                items[KEY_EMPTY_ITEM] = it
            }
        }
    }

    fun setHint(hint: String?) {
        hint?.let {
            if (items.isNotEmpty()) {
                val copy = LinkedHashMap(items)
                items.clear()
                items[KEY_HINT_ITEM] = it
                items.putAll(copy)
                fillAdapter(items.values)
            } else {
                items[KEY_HINT_ITEM] = it
            }
        }
    }

    fun setCallback(callback: SelectableValuesCallback?) {
        this.callback = callback
    }

    fun setNotifyKeys(notifyKeys: Boolean) {
        this.notifyKeys = notifyKeys
    }

    private fun createAdapter(): SpinnerAdapter = SelectableValuesAdapter(context)

    private fun fillAdapter(items: Collection<String>) {
        adapter().apply {
            clear()
            addAll(items)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun adapter(): SelectableValuesAdapter =
        spinner.adapter as SelectableValuesAdapter

    private fun findKeyForValue(value: String): String? {
        val keyMap = items.filterKeys { items[it] == value }
        return if (keyMap.isNotEmpty()) keyMap.keys.first() else null
    }

    interface SelectableValuesCallback {
        fun onValueChanged(value: String?)
    }

    private class SelectableValuesAdapter(
        context: Context
    ) : ArrayAdapter<String>(context, R.layout.view_profile_dropdown_item) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val item = getItem(position)
            val holder: TitleViewHolder
            val rawView: View

            if (convertView == null) {
                rawView = LayoutInflater.from(context).inflate(R.layout.view_profile_dropdown_title_edit_text, parent, false)
                holder = TitleViewHolder(rawView)
                rawView.tag = holder
            } else {
                rawView = convertView
                holder = rawView.tag as TitleViewHolder
            }

            holder.titleView.setText(item)

            return rawView
        }

        private class TitleViewHolder(view: View) {
            val titleView: TextView = view.findViewById(R.id.tv_title)
        }
    }

    private companion object {
        private const val KEY_EMPTY_ITEM = "key.spinner.selectable.values.empty"
        private const val KEY_HINT_ITEM = "key.spinner.selectable.values.hint"
    }
}