package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class ApiDriveTypeTest {

    @ParameterizedTest
    @EnumSource(ApiDriveType::class)
    fun `Mapping from ApiDriveType to DriveType enum`(apiDriveType: ApiDriveType) {
        Assertions.assertEquals(apiDriveType.name, apiDriveType.toDriveType()?.name)
    }
}
