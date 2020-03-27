package com.daimler.mbnetworkkit.socket.message

interface Observables {

    fun <T> observe(clazz: Class<T>, observer: MessageObserver<T>): Observables

    fun <T> dispose(clazz: Class<T>, observer: MessageObserver<T>): Observables
}
