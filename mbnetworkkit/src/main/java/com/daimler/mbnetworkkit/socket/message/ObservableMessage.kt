package com.daimler.mbnetworkkit.socket.message

open class ObservableMessage<T>(
    val data: T
) {
    val type: String = this::class.java.canonicalName ?: ""
}
