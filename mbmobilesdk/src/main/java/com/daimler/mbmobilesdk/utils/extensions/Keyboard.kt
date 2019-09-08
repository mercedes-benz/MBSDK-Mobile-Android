package com.daimler.mbmobilesdk.utils.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Fragment.showKeyboard() {
    this.view?.requestFocus()
    val inputMethodManager = context?.let { it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager } ?: return
    inputMethodManager.showSoftInput(this.view, InputMethodManager.SHOW_IMPLICIT)
}

fun Fragment.hideKeyboard() {
    val inputMethodManager = context?.let { it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager } ?: return
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}