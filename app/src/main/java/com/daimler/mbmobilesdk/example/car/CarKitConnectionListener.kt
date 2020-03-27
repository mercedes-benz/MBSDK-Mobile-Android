package com.daimler.mbmobilesdk.example.car

import com.daimler.mbnetworkkit.socket.ConnectionState

interface CarKitConnectionListener {
    fun onConnectionStateChanged(connectionState: ConnectionState)
}
