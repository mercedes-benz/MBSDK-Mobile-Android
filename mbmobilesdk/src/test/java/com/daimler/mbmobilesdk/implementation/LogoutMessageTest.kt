package com.daimler.mbmobilesdk.implementation

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import io.mockk.every
import io.mockk.mockkStatic
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Calendar

@ExtendWith(SoftAssertionsExtension::class)
class LogoutMessageTest {

    private val millis = 123L

    @BeforeEach
    fun setup() {
        mockkStatic(Calendar::class)
        every { Calendar.getInstance().timeInMillis } returns millis
    }

    @Test
    fun `parse should return correct result`(softly: SoftAssertions) {
        LogoutMessage().parse().also {
            softly.assertThat(it::class).isEqualTo(DataSocketMessage.ByteSocketMessage::class)
            softly.assertThat(it.timestamp).isEqualTo(millis)
            softly.assertThat((it as DataSocketMessage.ByteSocketMessage).bytes).isEmpty()
        }
    }
}
