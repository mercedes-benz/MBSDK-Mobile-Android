package com.daimler.mbmobilesdk.utils.bindings

import android.content.Context
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("closeKeyboardOnDone")
fun EditText.closeKeyboardOnDone(listener: OnKeyboardClosedListener?) {
    listener?.let {
        setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                it.onKeyboardClosed()
                true
            } else {
                false
            }
        }
    } ?: setOnEditorActionListener(null)
}

@BindingAdapter("nextOrDone")
fun EditText.nextOrDone(hasNext: Boolean) {
    imeOptions = if (hasNext) EditorInfo.IME_ACTION_NEXT else EditorInfo.IME_ACTION_DONE
}

@BindingAdapter("allowInput")
fun EditText.setAllowInput(allowInput: Boolean) {
    inputType = if (allowInput) InputType.TYPE_CLASS_TEXT else InputType.TYPE_NULL
}

interface OnKeyboardClosedListener {
    fun onKeyboardClosed()
}

interface OnFocusChangedListener {
    fun onFocusChanged(hasFocus: Boolean)
}