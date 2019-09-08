package com.daimler.mbmobilesdk.views

import android.content.Context
import android.util.AttributeSet
import android.widget.NumberPicker

class ModelNumberPicker : NumberPicker {

    private val items = mutableListOf<Pickable>()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr)

    init {
        descendantFocusability = FOCUS_BLOCK_DESCENDANTS
    }

    fun setDisplayItems(items: List<Pickable>) {
        if (!(this.items.toTypedArray() contentEquals items.toTypedArray())) {

            if (displayedValues != null) reset()
            this.items.clear()
            if (items.isNotEmpty()) {
                this.items.addAll(items)
                minValue = 0
                super.setDisplayedValues(this.items.map { it.displayValue() }.toTypedArray())
                maxValue = items.size - 1
            }
        }
    }

    override fun setDisplayedValues(displayedValues: Array<out String>?) {
        throw UnsupportedOperationException("Do not call setDisplayedValues directly!")
    }

    fun setOnChangeListener(listener: OnChangeListener?) {
        listener?.let {
            setOnValueChangedListener { _, _, newVal ->
                it.onChange(items[newVal])
            }
        } ?: setOnValueChangedListener(null)
    }

    fun setDisplayedValue(value: String) {
        val index = items.indexOfFirst { it.displayValue() == value }
        if (index >= 0) setValue(index)
    }

    private fun reset() {
        minValue = 0
        maxValue = 0
        super.setDisplayedValues(null)
    }

    interface Pickable {
        fun displayValue(): String
    }

    interface OnChangeListener {
        fun onChange(item: Pickable)
    }
}