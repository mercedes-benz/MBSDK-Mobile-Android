package com.daimler.testutils.arguments

import io.mockk.clearAllMocks
import io.mockk.mockk
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.stream.Collectors

@ExtendWith(SoftAssertionsExtension::class)
internal class EnumWithNullArgumentsProviderTest {

    private lateinit var subject: EnumWithNullArgumentsProvider<TestEnum>

    @BeforeEach
    fun setup() {
        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @Test
    fun `should provide correct arguments`(softly: SoftAssertions) {
        val stream = subject.provideArguments(mockk())
        val values = stream.map { it.get().first() }.collect(Collectors.toList())
        softly.assertThat(values[0]).isEqualTo(TestEnum.ONE)
        softly.assertThat(values[1]).isEqualTo(TestEnum.TWO)
        softly.assertThat(values[2]).isEqualTo(TestEnum.THREE)
        softly.assertThat(values[3]).isNull()
    }

    private fun createSubject() =
        EnumWithNullArgumentsProvider(TestEnum::class)

    private enum class TestEnum { ONE, TWO, THREE }
}
