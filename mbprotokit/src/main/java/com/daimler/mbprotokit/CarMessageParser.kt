package com.daimler.mbprotokit

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage

interface CarMessageParser {
    /**
     * Should return true if the passed message is a vehicle related message. Only if true is returned,
     * the related [ReceivedCarMessageCallback] should be triggered. If false is returned, none of the
     * callback methods have to be triggered.
     */
    fun parseReceivedMessage(socketMessage: DataSocketMessage, messageCallback: ReceivedCarMessageCallback): Boolean
}
