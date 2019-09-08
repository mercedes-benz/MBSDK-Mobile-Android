package com.daimler.mbmobilesdk.utils

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

/**
 * [RecyclerView.ItemDecoration] that centers the first and last item horizontally.
 */
class CenteringItemDecoration(private val maxItemWidthPx: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val size = parent.adapter?.itemCount ?: return
        if (position == 0) {
            outRect.left = getItemOffset(parent, view)
        } else if (position == size - 1) {
            outRect.right = getItemOffset(parent, view)
        }
    }

    private fun getItemOffset(parent: RecyclerView, view: View): Int {
        val viewWidth = view.widthOrDefault(maxItemWidthPx)
        val sidePadding = view.sidePadding()
        val sideMargin = view.sideMargin()
        return (parent.width / 2f - (viewWidth / 2f + sidePadding / 2f + sideMargin / 2f)).roundToInt()
    }

    private fun View.widthOrDefault(default: Int) = if (width != 0) width else default

    private fun View.sidePadding() = paddingLeft + paddingRight

    private fun View.sideMargin() =
        (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            it.leftMargin + it.rightMargin
        } ?: 0
}