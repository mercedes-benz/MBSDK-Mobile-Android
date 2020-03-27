package com.daimler.mbnetworkkit.socket.message

interface Notifyable {

    fun <T> notifyChange(clazz: Class<T>, data: T)
}
