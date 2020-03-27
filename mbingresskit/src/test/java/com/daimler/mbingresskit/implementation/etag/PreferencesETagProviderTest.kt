package com.daimler.mbingresskit.implementation.etag

import android.content.Context
import android.content.SharedPreferences
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class PreferencesETagProviderTest {

    private val context = mockk<Context>()
    private val prefs = mockk<SharedPreferences>()
    private val editor = mockk<SharedPreferences.Editor>(relaxed = true)

    private val valueSlot = slot<String>()

    private lateinit var subject: PreferencesETagProvider

    @BeforeEach
    fun setup() {
        every { context.getSharedPreferences(any(), any()) } returns prefs
        every { prefs.edit() } returns editor
        every { prefs.getString(any(), any()) } answers {
            if (valueSlot.isCaptured) {
                valueSlot.captured
            } else {
                secondArg()
            }
        }
        every { editor.putString(any(), capture(valueSlot)) } returns editor

        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        valueSlot.clear()
        clearAllMocks()
    }

    @Test
    fun `get() should return value from preferences`(softly: SoftAssertions) {
        every { prefs.getString(KEY, any()) } returns VALUE
        softly.assertThat(subject.get(KEY)).isEqualTo(VALUE)
    }

    @Test
    fun `set() should write to preferences`(softly: SoftAssertions) {
        subject.set(KEY, VALUE)
        softly.assertThat(prefs.getString(KEY, null)).isEqualTo(VALUE)
    }

    @Test
    fun `clear() should remove key`() {
        subject.clear(KEY)
        verify { editor.remove(KEY) }
    }

    @Test
    fun `clearAll() should clear all keys`() {
        subject.clearAll()
        verify { editor.clear() }
    }

    private fun createSubject() = PreferencesETagProvider(context)

    private companion object {

        private const val KEY = "key"
        private const val VALUE = "value"
    }
}
