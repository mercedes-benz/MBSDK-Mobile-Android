package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class ApiDriveStatusTest {

    @ParameterizedTest
    @EnumSource(ApiDriveStatus::class)
    fun `Mapping from ApiDriveStatus to DriveStatus enum`(apiDriveStatus: ApiDriveStatus) {
        Assertions.assertEquals(apiDriveStatus.name, apiDriveStatus.toDriveSatus()?.name)
    }
}
