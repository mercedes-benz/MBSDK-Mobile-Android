package com.daimler.mbmobilesdk.implementation

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbprotokit.generated.Client
import java.util.Calendar

internal class LogoutMessage : SendableMessage {

    override fun parse(): DataSocketMessage {
        return DataSocketMessage.ByteSocketMessage(
            Calendar.getInstance().timeInMillis,
            Client.Logout.newBuilder().build().toByteArray()
        )
    }
}
