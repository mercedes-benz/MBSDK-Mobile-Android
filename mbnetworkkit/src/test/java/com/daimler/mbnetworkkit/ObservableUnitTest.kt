package com.daimler.mbnetworkkit

import com.daimler.mbnetworkkit.socket.message.MessageObserver
import com.daimler.mbnetworkkit.socket.message.ObservableMessage
import com.daimler.mbnetworkkit.socket.message.TypedSocketObservable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.ArrayList

class ObservableUnitTest {

    private var updateString: String? = null

    @Before
    fun setup() {
        updateString = ""
    }

    @Test
    @Throws(Exception::class)
    fun registerObserver() {
        val stringObservable = TypedSocketObservable(String::class.java)
        stringObservable.registerObserver(
            object : MessageObserver<String> {
                override fun onUpdate(observableMessage: ObservableMessage<String>) {
                }
            }
        )
        assertEquals(1, stringObservable.countRegisteredObservers().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun registerObserverTwiceNotPossible() {
        val stringObservable = TypedSocketObservable(String::class.java)
        val messageObserver = object : MessageObserver<String> {
            override fun onUpdate(observableMessage: ObservableMessage<String>) {
            }
        }
        stringObservable.registerObserver(messageObserver)
        stringObservable.registerObserver(messageObserver)
        assertEquals(1, stringObservable.countRegisteredObservers().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun unregisterObserver() {
        val stringObservable = TypedSocketObservable(String::class.java)
        val messageObserver = object : MessageObserver<String> {
            override fun onUpdate(observableMessage: ObservableMessage<String>) {
            }
        }
        stringObservable.registerObserver(messageObserver)
        stringObservable.unregisterObserver(messageObserver)
        assertEquals(0, stringObservable.countRegisteredObservers().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun registerMultipleObserver() {
        val stringObservable = TypedSocketObservable(String::class.java)
        val numberOfObservers = 20
        for (i in 0 until numberOfObservers) {
            stringObservable.registerObserver(
                object : MessageObserver<String> {
                    override fun onUpdate(observableMessage: ObservableMessage<String>) {
                    }
                }
            )
        }
        assertEquals(numberOfObservers.toLong(), stringObservable.countRegisteredObservers().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun unregisterMultipleObserver() {
        val stringObservable = TypedSocketObservable(String::class.java)
        val numberOfObservers = 20
        val observers = ArrayList<MessageObserver<String>>(numberOfObservers)
        for (i in 0 until numberOfObservers) {
            observers.add(
                object : MessageObserver<String> {
                    override fun onUpdate(observableMessage: ObservableMessage<String>) {
                    }
                }
            )
        }
        for (observer in observers) {
            stringObservable.registerObserver(observer)
        }
        for (observer in observers) {
            stringObservable.unregisterObserver(observer)
        }
        assertEquals(0, stringObservable.countRegisteredObservers().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun notifObservers() {
        val stringObservable = TypedSocketObservable(String::class.java)
        val updateMessage = "UPDATE"
        stringObservable.registerObserver(
            object : MessageObserver<String> {
                override fun onUpdate(observableMessage: ObservableMessage<String>) {
                    updateString += observableMessage.data
                }
            }
        )
        stringObservable.notifyObservers(ObservableMessage(updateMessage))
        assertEquals(updateMessage, updateString)
    }

    @Test
    @Throws(Exception::class)
    fun notifyMultipleObservers() {
        val stringObservable = TypedSocketObservable(String::class.java)
        val numberOfObservers = 10
        val observers = ArrayList<MessageObserver<String>>(numberOfObservers)
        for (i in 0 until numberOfObservers) {
            observers.add(
                object : MessageObserver<String> {
                    override fun onUpdate(observableMessage: ObservableMessage<String>) {
                        updateString += observableMessage.data
                    }
                }
            )
        }
        for (observer in observers) {
            stringObservable.registerObserver(observer)
        }
        stringObservable.notifyObservers(ObservableMessage("1"))
        assertEquals("1111111111", updateString)
    }
}
