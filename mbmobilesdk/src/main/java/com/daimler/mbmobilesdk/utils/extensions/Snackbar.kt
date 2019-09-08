package com.daimler.mbmobilesdk.utils.extensions

import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.daimler.mbmobilesdk.R
import com.google.android.material.snackbar.Snackbar

fun Snackbar.showCentered() {
    val textView: TextView = view.findViewById(R.id.snackbar_text)
    textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    textView.gravity = Gravity.CENTER_HORIZONTAL
    show()
}