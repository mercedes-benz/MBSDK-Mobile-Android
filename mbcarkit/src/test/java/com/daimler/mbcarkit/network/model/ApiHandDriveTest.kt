package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiHandDriveTest {

    @Test
    fun `Mapping 'null' ApiHandDrive should default to 'null'`() {
        Assertions.assertNull(null.toHandDrive())
    }

    @ParameterizedTest
    @EnumSource(ApiHandDrive::class)
    fun `Mapping from ApiHandDrive to HandDrive enum`(apiHandDrive: ApiHandDrive) {
        Assertions.assertEquals(apiHandDrive.name, apiHandDrive.toHandDrive()?.name)
    }
}
