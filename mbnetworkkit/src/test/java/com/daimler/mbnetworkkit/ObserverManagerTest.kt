package com.daimler.mbnetworkkit

import com.daimler.mbnetworkkit.socket.message.MessageObserver
import com.daimler.mbnetworkkit.socket.message.ObservableMessage
import com.daimler.mbnetworkkit.socket.message.ObserverManager
import com.daimler.mbnetworkkit.socket.message.dispose
import com.daimler.mbnetworkkit.socket.message.notifyChange
import com.daimler.mbnetworkkit.socket.message.observe
import org.junit.Before
import org.junit.Test

class ObserverManagerTest {

    lateinit var observerManager: ObserverManager

    var observedString: String? = null

    @Before
    fun setup() {
        observerManager = ObserverManager()
        observedString = null
    }

    @Test
    fun addObserverIfNotRegistered() {
        observerManager.observe(
            object : MessageObserver<String> {
                override fun onUpdate(observableMessage: ObservableMessage<String>) {
                }
            }
        )
        assert(observerManager.typedObservables.size == 1)
    }

    @Test
    fun removeObserverIfRegistered() {
        val stringObserver = object : MessageObserver<String> {
            override fun onUpdate(observableMessage: ObservableMessage<String>) {
            }
        }
        observerManager.apply {
            observe(stringObserver)
            dispose(stringObserver)
        }
        assert(observerManager.typedObservables.size == 0)
    }

    @Test
    fun notifyOneObserverChange() {
        val stringObserver = object : MessageObserver<String> {
            override fun onUpdate(observableMessage: ObservableMessage<String>) {
                observedString = observableMessage.data
            }
        }
        val updatedString = "UPDATE"
        observerManager.apply {
            observe(stringObserver)
            notifyChange(updatedString)
        }
        assert(observedString == updatedString)
    }

    @Test
    fun notifyMultipleObserverChange() {
        val observer1 = object : MessageObserver<String> {
            override fun onUpdate(observableMessage: ObservableMessage<String>) {
                observedString = observableMessage.data
            }
        }
        val observer2 = object : MessageObserver<String> {
            override fun onUpdate(observableMessage: ObservableMessage<String>) {
                observedString = "$observedString${observableMessage.data}"
            }
        }
        val updatedString = "UPDATE"
        observerManager.apply {
            observe(observer1)
            observe(observer2)
            notifyChange(updatedString)
        }
        assert(observedString == "$updatedString$updatedString")
    }
}
