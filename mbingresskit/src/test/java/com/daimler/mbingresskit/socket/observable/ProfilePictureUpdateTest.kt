package com.daimler.mbingresskit.socket.observable

import com.daimler.mbprotokit.dto.user.UserPictureUpdate
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class ProfilePictureUpdateTest {

    @Test
    fun `verify mapping from UserPictureUpdate`(softly: SoftAssertions) {
        val update = UserPictureUpdate(1)
        val model = update.toProfilePictureUpdate()
        softly.assertThat(model.sequenceNumber).isEqualTo(update.sequenceNumber)
    }
}
