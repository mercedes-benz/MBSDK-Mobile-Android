package com.daimler.mbmobilesdk.socket

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.socket.ConnectionState
import com.daimler.mbnetworkkit.socket.SocketConnectionListener
import com.daimler.mbnetworkkit.socket.message.Observables

/**
 * Base AndroidViewModel that holds [Observables] for a socket connection and redirects
 * different states to appropriate callbacks.
 */
abstract class SocketAndroidViewModel(app: Application) : AndroidViewModel(app),
    SocketConnectionListener {

    private var observables: Observables? = null

    /**
     * Called when observers should be registered.
     */
    protected abstract fun onRegisterObservers(observables: Observables)

    /**
     * Called when observers should be disposed.
     */
    protected abstract fun onDisposeObservers(observables: Observables)

    /**
     * Connect to the socket when this method is called.
     */
    protected abstract fun connectToSocket()

    /**
     * Dispose from the socket when this method is called.
     */
    protected abstract fun disposeFromSocket()

    override fun onCleared() {
        super.onCleared()
        dispose()
    }

    /**
     * Connects to the socket.
     */
    protected fun connect() {
        connectToSocket()
    }

    /**
     * Disposes the socket.
     * NOTE: No disconnection happens here.
     */
    protected fun dispose() {
        observables?.let { onDisposeObservers(it) }
        observables = null
        disposeFromSocket()
    }

    override fun connectionStateChanged(connectionState: ConnectionState) {
        MBLoggerKit.d("connectionStateChanged: $connectionState")
        when (connectionState) {
            is ConnectionState.Connected -> {
                observables = connectionState.observables
                observables?.let { onRegisterObservers(it) }
            }
            is ConnectionState.Disconnected -> {
                observables?.let { onDisposeObservers(it) }
                observables = null
            }
        }
    }
}