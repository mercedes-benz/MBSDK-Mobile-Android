package com.daimler.mbingresskit.socket.observable

import com.daimler.mbnetworkkit.socket.message.MessageObserver
import com.daimler.mbnetworkkit.socket.message.ObservableMessage

sealed class UserObserver<T> : MessageObserver<T> {

    open class BaseUserObserver<T>(private val action: (T) -> Unit) : UserObserver<T>() {

        override fun onUpdate(observableMessage: ObservableMessage<T>) {
            action(observableMessage.data)
        }
    }

    class UserData(action: (ProfileDataUpdate) -> Unit) : BaseUserObserver<ProfileDataUpdate>(action)

    class ProfilePicture(action: (ProfilePictureUpdate) -> Unit) : BaseUserObserver<ProfilePictureUpdate>(action)

    class UserPin(action: (ProfilePinUpdate) -> Unit) : BaseUserObserver<ProfilePinUpdate>(action)
}
