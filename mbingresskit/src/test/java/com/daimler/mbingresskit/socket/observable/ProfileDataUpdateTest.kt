package com.daimler.mbingresskit.socket.observable

import com.daimler.mbprotokit.dto.user.UserDataUpdate
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class ProfileDataUpdateTest {

    @Test
    fun `verify mapping from UserDataUpdate`(softly: SoftAssertions) {
        val update = UserDataUpdate(1)
        val model = update.toProfileDataUpdate()
        softly.assertThat(model.sequenceNumber).isEqualTo(update.sequenceNumber)
    }
}
