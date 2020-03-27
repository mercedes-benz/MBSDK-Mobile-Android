package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicleusers.ProfileSyncStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiProfileSyncStatusTest {

    @ParameterizedTest
    @EnumSource(value = ApiProfileSyncStatus::class)
    fun `toProfileSyncStatus() should map correctly`(input: ApiProfileSyncStatus) {
        val expected = when (input) {
            ApiProfileSyncStatus.UNKNOWN -> ProfileSyncStatus.UNKNOWN
            ApiProfileSyncStatus.ON -> ProfileSyncStatus.ON
            ApiProfileSyncStatus.OFF -> ProfileSyncStatus.OFF
            ApiProfileSyncStatus.MANAGE_IN_HEAD_UNIT -> ProfileSyncStatus.MANAGE_IN_HEAD_UNIT
            ApiProfileSyncStatus.UNSUPPORTED -> ProfileSyncStatus.UNSUPPORTED
            ApiProfileSyncStatus.SERVICE_NOT_ACTIVE -> ProfileSyncStatus.SERVICE_NOT_ACTIVE
        }
        Assertions.assertEquals(expected, input.toProfileSyncStatus())
    }
}
