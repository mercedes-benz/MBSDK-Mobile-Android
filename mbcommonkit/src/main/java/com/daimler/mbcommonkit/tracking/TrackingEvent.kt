package com.daimler.mbcommonkit.tracking

open class TrackingEvent(
    val value: Map<String, String>,
    tag: String? = null,
    val isAction: Boolean = false
) {
    val tag: String = tag ?: javaClass.simpleName
}
