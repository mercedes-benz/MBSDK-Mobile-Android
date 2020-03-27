package com.daimler.mbnetworkkit.socket.message

class ObserverManager : Observables, Notifyable {

    internal val typedObservables: MutableList<TypedSocketObservable<*>> = mutableListOf()

    override fun <T> observe(clazz: Class<T>, observer: MessageObserver<T>): Observables {
        if (containsTypedObservable(clazz).not()) {
            val newTypedObservable = TypedSocketObservable(clazz)
            newTypedObservable.registerObserver(observer)
            synchronized(this) {
                typedObservables.add(newTypedObservable)
            }
        } else {
            @Suppress("UNCHECKED_CAST")
            synchronized(this) {
                typedObservables
                    .filter { it.clazz == clazz }
                    .forEach { (it as TypedSocketObservable<T>).registerObserver(observer) }
            }
        }
        return this
    }

    override fun <T> dispose(clazz: Class<T>, observer: MessageObserver<T>): Observables {
        synchronized(this) {
            @Suppress("UNCHECKED_CAST")
            typedObservables
                .filter { it.clazz == clazz }
                .forEach { (it as TypedSocketObservable<T>).unregisterObserver(observer) }
            typedObservables.removeAll { it.countRegisteredObservers() == 0 }
        }
        return this
    }

    override fun <T> notifyChange(clazz: Class<T>, data: T) {
        synchronized(this) {
            @Suppress("UNCHECKED_CAST")
            typedObservables
                .filter { it.clazz == clazz }
                .forEach { (it as TypedSocketObservable<T>).notifyObservers(ObservableMessage(data)) }
        }
    }

    private fun <T> containsTypedObservable(type: Class<T>): Boolean {
        return typedObservables.firstOrNull { type == it.clazz }?.let { true } ?: false
    }
}

inline fun <reified T> Observables.observe(observer: MessageObserver<T>): Observables {
    this.observe(T::class.java, observer)
    return this
}

inline fun <reified T> Observables.dispose(observer: MessageObserver<T>): Observables {
    this.dispose(T::class.java, observer)
    return this
}

inline fun <reified T> Notifyable.notifyChange(data: T) {
    this.notifyChange(T::class.java, data)
}
