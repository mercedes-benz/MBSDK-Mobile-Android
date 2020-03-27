package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

internal class ApiNormalizedProfileControlTest {

    @ParameterizedTest
    @ArgumentsSource(value = ApiModelArgumentsProvider::class)
    fun `mapping to NormalizedProfileControl`(input: ApiNormalizedProfileControl) {
        val model = input.toNormalizedProfileControl()
        Assertions.assertEquals(input.enabled, model.enabled)
    }

    private class ApiModelArgumentsProvider : ArgumentsProvider {

        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> =
            listOf(true, false).map {
                Arguments { arrayOf(ApiNormalizedProfileControl(it)) }
            }.stream()
    }
}
