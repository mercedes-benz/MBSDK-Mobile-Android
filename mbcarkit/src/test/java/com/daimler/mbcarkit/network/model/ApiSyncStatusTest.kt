package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.onboardfence.SyncStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiSyncStatusTest {

    @ParameterizedTest
    @EnumSource(ApiSyncStatus::class)
    fun `Mapping from ApiSyncStatus to SyncStatus enum`(apiSyncStatus: ApiSyncStatus) {
        Assertions.assertEquals(apiSyncStatus.name, apiSyncStatus.toSyncStatus()?.name)
    }

    @ParameterizedTest
    @EnumSource(SyncStatus::class)
    fun `Mapping ApiSyncStatus from SyncStatus enum`(syncStatus: SyncStatus) {
        Assertions.assertEquals(syncStatus.name, ApiSyncStatus.fromSyncStatus(syncStatus)?.name)
    }
}
