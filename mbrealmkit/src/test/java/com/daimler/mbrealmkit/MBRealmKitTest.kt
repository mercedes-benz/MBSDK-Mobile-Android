package com.daimler.mbrealmkit

import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.realm.Realm
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.UUID

class MBRealmKitTest {
    private val realmServiceConfig = mockk<RealmServiceConfig>(relaxed = true)

    @AfterEach
    fun tearDown() {
        clearMocks(realmServiceConfig)
    }

    @Test
    fun `realm can be created and successfully retrieved afterwards`() {
        val myRealmId = randomUUID()
        MBRealmKit.createRealmInstance(myRealmId, realmServiceConfig)
        assertDoesNotThrow { MBRealmKit.realm(myRealmId) }
    }

    @Test
    fun `cached realm is not changed when using existing id but different service config`() {
        val myRealmId = randomUUID()
        MBRealmKit.createRealmInstance(myRealmId, realmServiceConfig)
        val realmAfterFirstCreate = MBRealmKit.realm(myRealmId)

        val differentRealmServiceConfig = mockk<RealmServiceConfig>(relaxed = true)
        MBRealmKit.createRealmInstance(myRealmId, differentRealmServiceConfig)
        val realmAfterSecondCreate = MBRealmKit.realm(myRealmId)

        assertThat(realmAfterSecondCreate).isSameAs(realmAfterFirstCreate)
    }

    @Test
    fun getRealmWithWrongId() {
        assertThrows(UnableToCreateRealmInstanceException::class.java) {
            MBRealmKit.realm(randomUUID())
        }
    }

    @Test
    fun `get realm should return existing realm`() {
        val myRealmId = randomUUID()
        val realmInstance = mockk<Realm>()
        every { realmServiceConfig.realmInstance() } returns realmInstance
        MBRealmKit.createRealmInstance(myRealmId, realmServiceConfig)
        val realmInstanceFromCache = MBRealmKit.realm(myRealmId)

        assertThat(realmInstanceFromCache).isSameAs(realmInstance)
    }

    private fun randomUUID() = UUID.randomUUID().toString()
}
