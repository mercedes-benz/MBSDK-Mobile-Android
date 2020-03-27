package com.daimler.mbcommonkit.utils

import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class NullUtilsTest {
    @Test
    fun `2 params execute only if all not null`() {
        val calledFn = mockk<(a: String, b: String) -> Unit>(relaxed = true)

        ifNotNull(null, null, calledFn)
        verify {
            calledFn.invoke(any(), any()) wasNot Called
        }

        ifNotNull(null, "b", calledFn)
        verify {
            calledFn.invoke(any(), any()) wasNot Called
        }

        ifNotNull("a", null, calledFn)
        verify {
            calledFn.invoke(any(), any()) wasNot Called
        }

        ifNotNull("a", "b", calledFn)
        verify(exactly = 1) {
            calledFn.invoke("a", "b")
        }
    }

    @Test
    fun `3 params execute only if all not null`() {
        val calledFn = mockk<(a: String, b: String, c: String) -> Unit>(relaxed = true)

        ifNotNull(null, null, null, calledFn)
        verify {
            calledFn.invoke(any(), any(), any()) wasNot Called
        }

        ifNotNull(null, "b", "c", calledFn)
        verify {
            calledFn.invoke(any(), any(), any()) wasNot Called
        }

        ifNotNull("a", null, "c", calledFn)
        verify {
            calledFn.invoke(any(), any(), any()) wasNot Called
        }

        ifNotNull(null, null, "c", calledFn)
        verify {
            calledFn.invoke(any(), any(), any()) wasNot Called
        }

        ifNotNull("a", "b", "c", calledFn)
        verify(exactly = 1) {
            calledFn.invoke("a", "b", "c")
        }
    }
}
