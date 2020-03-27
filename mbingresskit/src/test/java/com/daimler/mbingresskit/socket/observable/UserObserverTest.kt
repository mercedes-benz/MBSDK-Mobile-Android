package com.daimler.mbingresskit.socket.observable

import com.daimler.mbnetworkkit.socket.message.ObservableMessage
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class UserObserverTest {

    @Test
    fun `UserObserver_UserData should invoke given action onUpdate`(softly: SoftAssertions) {
        val data = ProfileDataUpdate(1)
        verifyObserverUpdate(data, softly) {
            UserObserver.UserData(it)
        }
    }

    @Test
    fun `UserObserver_ProfilePicture should invoke given action onUpdate`(softly: SoftAssertions) {
        val data = ProfilePictureUpdate(1)
        verifyObserverUpdate(data, softly) {
            UserObserver.ProfilePicture(it)
        }
    }

    @Test
    fun `UserObserver_UserPin should invoke given action onUpdate`(softly: SoftAssertions) {
        val data = ProfilePinUpdate(1)
        verifyObserverUpdate(data, softly) {
            UserObserver.UserPin(it)
        }
    }

    private fun <T> verifyObserverUpdate(
        data: T,
        softly: SoftAssertions,
        observerCreator: (((T) -> Unit) -> UserObserver<T>)
    ) {
        var result: T? = null
        val observer = observerCreator { result = it }
        observer.onUpdate(ObservableMessage(data))
        softly.assertThat(result).isEqualTo(data)
    }
}
