package com.daimler.mbmobilesdk.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.utils.dpToPx

class LineDividerDecorator(
    @ColorInt private val color: Int,
    private val heightDp: Int = 1,
    private val leftOffsetDp: Int = 0,
    private val rightOffsetDp: Int = 0,
    private val viewTypes: IntArray? = null
) : RecyclerView.ItemDecoration() {

    private var paint: Paint? = null
    private var left: Int? = null
    private var right: Int? = null
    private var height: Int? = null

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (isDecorationView(parent, view)) {
            outRect.set(0, 0, 0, dividerHeight(parent.context))
        } else {
            outRect.setEmpty()
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val count = parent.childCount
        repeat(count) {
            val view = parent.getChildAt(it)
            if (isDecorationView(parent, view)) {
                c.drawLine(
                    view.left + dividerLeft(parent.context).toFloat(),
                    view.bottom.toFloat(),
                    view.right - dividerRight(parent.context).toFloat(),
                    view.bottom.toFloat(),
                    paint(parent.context)
                )
            }
        }
    }

    private fun isDecorationView(parent: RecyclerView, view: View): Boolean {
        val pos = parent.getChildAdapterPosition(view)
        if (pos != RecyclerView.NO_POSITION) {
            if (viewTypes == null) return true
            val viewType = parent.adapter?.getItemViewType(pos)
            return isValidViewType(viewType)
        }
        return false
    }

    private fun isValidViewType(viewType: Int?): Boolean =
        viewTypes == null || viewType == null || viewTypes.isEmpty() || viewTypes.contains(viewType)

    private fun paint(context: Context): Paint {
        return paint ?: run {
            Paint().apply {
                style = Paint.Style.FILL_AND_STROKE
                color = this@LineDividerDecorator.color
                strokeWidth = dividerHeight(context).toFloat()
            }
        }
    }

    private fun dividerHeight(context: Context): Int {
        return getPxOrCalculate(context, height, heightDp) {
            height = it
        }
    }

    private fun dividerLeft(context: Context): Int {
        return getPxOrCalculate(context, left, leftOffsetDp) {
            left = it
        }
    }

    private fun dividerRight(context: Context): Int {
        return getPxOrCalculate(context, right, rightOffsetDp) {
            right = it
        }
    }

    private fun getPxOrCalculate(context: Context, value: Int?, dp: Int, action: (Int) -> Unit): Int {
        return value ?: run {
            val px = dpToPx(context.resources, dp)
            action(px)
            px
        }
    }

    companion object {

        fun createDefault(context: Context) =
            LineDividerDecorator(
                ContextCompat.getColor(context, R.color.mb_divider_color)
            )
    }
}