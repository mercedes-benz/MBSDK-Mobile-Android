package com.daimler.mbmobilesdk.utils.extensions

import android.view.View
import android.view.ViewGroup

fun ViewGroup.forAllChildren(action: (View?) -> Unit) {
    forAllChildrenIndexed { _, view -> action(view) }
}

fun ViewGroup.forAllChildrenIndexed(action: (Int, View?) -> Unit) {
    val childCount = childCount
    repeat(childCount) { action(it, getChildAt(it)) }
}