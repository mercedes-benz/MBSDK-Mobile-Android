package com.daimler.mbcommonkit.utils

import com.daimler.mbloggerkit.MBLoggerKit

/**
 * Measures the time needed to execute the [action].
 *
 * @return the execution time for [action] in ms
 */
fun measureExecutionTime(action: () -> Unit): Long {
    val start = System.currentTimeMillis()
    action()
    return System.currentTimeMillis() - start
}

/**
 * Measures and logs the time needed to execute [action].
 *
 * @param tag a tag that is used to log a message
 * @return the execution time for [action]
 */
fun logExecutionTime(tag: String? = null, action: () -> Unit): Long {
    val time = measureExecutionTime(action)
    val s = if (tag != null) " for $tag" else ""
    MBLoggerKit.d("Execution$s needed $time ms")
    return time
}
