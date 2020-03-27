package com.daimler.mbmobilesdk.example.utils

class LiveEvent<out T>(private val content: T) {

    private var isHandled = false

    /**
     * Returns the content of this event if called for the first time, returns null
     * on every following call.
     */
    fun getContentIfNotHandled(): T? =
        if (isHandled) {
            null
        } else {
            isHandled = true
            content
        }

    /**
     * Always returns the content.
     */
    fun peekContent(): T = content
}
