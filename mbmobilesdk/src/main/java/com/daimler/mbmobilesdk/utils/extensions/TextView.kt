package com.daimler.mbmobilesdk.utils.extensions

import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt

typealias TextClickHandler = () -> Unit

data class ClickAction(val text: String, val handler: TextClickHandler)

fun TextView.setClickableSpans(
    text: String,
    clickActions: List<ClickAction>,
    @ColorInt linkTextColor: Int,
    movementMethod: MovementMethod = LinkMovementMethod.getInstance()
) {
    val span = SpannableString(text)
    span.applyClickActions(clickActions)
    setLinkTextColor(linkTextColor)
    setMovementMethod(movementMethod)
    setText(span)
}

fun SpannableString.applyClickAction(clickAction: ClickAction): SpannableString {
    return applyClickActions(listOf(clickAction))
}

fun SpannableString.applyClickActions(clickActions: List<ClickAction>): SpannableString {
    val text = toString()
    clickActions.forEach {
        val clickableText = it.text
        val handler = it.handler
        val index = text.indexOf(clickableText)
        if (index >= 0) {
            setSpan(clickableSpan(handler), index, index + clickableText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
    return this
}

private fun clickableSpan(action: TextClickHandler) = object : ClickableSpan() {
    override fun onClick(widget: View?) {
        action()
    }
}