package com.daimler.mbnetworkkit.socket.message

interface MessageObserver<T> {
    fun onUpdate(observableMessage: ObservableMessage<T>)
}
