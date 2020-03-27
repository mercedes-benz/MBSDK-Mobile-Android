package com.daimler.mbnetworkkit.socket.message

open class TypedSocketObservable<T>(
    internal val clazz: Class<T>
) {

    val type = clazz::class.java.canonicalName!!

    private val observers: MutableList<MessageObserver<T>> = mutableListOf()

    fun registerObserver(observer: MessageObserver<T>) {
        synchronized(this) {
            if (observers.contains(observer).not()) {
                observers.add(observer)
            }
        }
    }

    fun unregisterObserver(observer: MessageObserver<T>) {
        synchronized(this) {
            observers.remove(observer)
        }
    }

    fun clearObservers() {
        synchronized(this) {
            observers.clear()
        }
    }

    fun notifyObservers(observableMessage: ObservableMessage<T>) {
        synchronized(this) {
            observers.forEach {
                it.onUpdate(observableMessage)
            }
        }
    }

    fun countRegisteredObservers() = observers.size
}
