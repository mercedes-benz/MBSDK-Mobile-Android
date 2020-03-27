package com.daimler.mbprotokit

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.dto.user.UserDataUpdate
import com.daimler.mbprotokit.dto.user.UserPictureUpdate
import com.daimler.mbprotokit.dto.user.UserPinUpdate

interface ReceivedUserMessageCallback {

    fun onUserDataUpdated(update: UserDataUpdate)

    fun onUserPictureUpdated(update: UserPictureUpdate)

    fun onUserPinUpdated(update: UserPinUpdate)

    fun onUserMessageError(socketMessage: DataSocketMessage, cause: String)
}
