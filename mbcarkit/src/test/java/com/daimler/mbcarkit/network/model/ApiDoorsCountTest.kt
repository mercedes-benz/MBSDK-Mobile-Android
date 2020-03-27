package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiDoorsCountTest {

    @Test
    fun `Mapping 'null' ApiDoorsCount should default to 'null'`() {
        Assertions.assertNull(null.toDoorsCount())
    }

    @ParameterizedTest
    @EnumSource(ApiDoorsCount::class)
    fun `Mapping from ApiDoorsCount to DoorsCount enum`(apiDoorsCount: ApiDoorsCount) {
        Assertions.assertEquals(apiDoorsCount.name, apiDoorsCount.toDoorsCount()?.name)
    }
}
