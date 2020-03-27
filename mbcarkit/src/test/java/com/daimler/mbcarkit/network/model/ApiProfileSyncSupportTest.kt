package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiProfileSyncSupportTest {

    @Test
    fun `Mapping 'null' ApiProfileSyncSupport should default to 'null'`() {
        Assertions.assertNull(null.toProfileSyncSupport())
    }

    @ParameterizedTest
    @EnumSource(ApiProfileSyncSupport::class)
    fun `Mapping from ApiProfileSyncSupport to ProfileSyncSupport enum`(apiProfileSyncSupport: ApiProfileSyncSupport) {
        Assertions.assertEquals(apiProfileSyncSupport.name, apiProfileSyncSupport.toProfileSyncSupport()?.name)
    }
}
