package com.daimler.mbmobilesdk.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

class SnapOnScrollListener(
    private val snapHelper: SnapHelper
) : RecyclerView.OnScrollListener() {

    private var listener: OnSnapChangedListener? = null
    private var currentSnapPosition = 0

    fun setOnSnapListener(listener: OnSnapChangedListener?) {
        this.listener = listener
        listener?.onSnapPositionChanged(currentSnapPosition)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            notifySnapPosition(recyclerView)
        }
    }

    private fun notifySnapPosition(recyclerView: RecyclerView) {
        val position = snapHelper.getSnapPosition(recyclerView)
        if (position != currentSnapPosition) {
            listener?.onSnapPositionChanged(position)
            currentSnapPosition = position
        }
    }

    private fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager ?: return 0
        val view = findSnapView(layoutManager) ?: return 0
        return layoutManager.getPosition(view)
    }
}

interface OnSnapChangedListener {

    fun onSnapPositionChanged(newPosition: Int)
}