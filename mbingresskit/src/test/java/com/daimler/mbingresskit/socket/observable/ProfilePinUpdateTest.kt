package com.daimler.mbingresskit.socket.observable

import com.daimler.mbprotokit.dto.user.UserPinUpdate
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class ProfilePinUpdateTest {

    @Test
    fun `verify mapping from UserPinUpdate`(softly: SoftAssertions) {
        val update = UserPinUpdate(1)
        val model = update.toProfilePinUpdate()
        softly.assertThat(model.sequenceNumber).isEqualTo(update.sequenceNumber)
    }
}
