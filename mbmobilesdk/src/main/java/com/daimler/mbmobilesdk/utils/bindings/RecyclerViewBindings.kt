package com.daimler.mbmobilesdk.utils.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.daimler.mbmobilesdk.utils.OnSnapChangedListener
import com.daimler.mbmobilesdk.utils.SnapOnScrollListener

@BindingAdapter("decorator")
fun addDecorator(view: RecyclerView, decorator: RecyclerView.ItemDecoration) {
    view.addItemDecoration(decorator)
}

@BindingAdapter("scrollPosition")
fun scrollRecyclerViewTo(view: RecyclerView, position: Int) {
    val manager = view.layoutManager
    if (manager != null && position in (0 until manager.itemCount)) {
        view.post {
            if (manager is LinearLayoutManager) {
                manager.scrollToPositionWithOffset(position, 0)
            } else {
                manager.scrollToPosition(position)
            }
        }
    }
}

@BindingAdapter("smoothScrollPosition")
fun RecyclerView.setSmoothScrollPosition(position: Int) {
    smoothScrollToPosition(position)
}

@BindingAdapter("snapHelper")
fun RecyclerView.setSnapHelper(snapHelper: SnapHelper) {
    snapHelper.attachToRecyclerView(this)
}

@BindingAdapter("snapHelper", "snapListener", requireAll = false)
fun RecyclerView.setSnapHelper(snapHelper: SnapHelper?, snapChangedListener: OnSnapChangedListener?) {
    snapHelper?.let { helper ->
        helper.attachToRecyclerView(this)
        val snapListener = SnapOnScrollListener(helper).also {
            it.setOnSnapListener(snapChangedListener)
        }
        addOnScrollListener(snapListener)
    }
}