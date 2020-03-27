package com.daimler.mbnetworkkit.header

import com.daimler.mbnetworkkit.DummyChain
import com.daimler.mbnetworkkit.networking.DynamicHeaderInterceptor
import com.daimler.mbnetworkkit.networking.DynamicHeadersInterceptor
import com.daimler.mbnetworkkit.networking.SingleHeaderInterceptor
import com.daimler.mbnetworkkit.networking.StaticHeadersInterceptor
import okhttp3.Interceptor
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HeadersInterceptorTest {

    private lateinit var chain: Interceptor.Chain

    @Before
    fun setup() {
        chain = DummyChain()
    }

    @Test
    fun testReverser() {
        var value = DUMMY_VALUE_1
        val reverser = ReverseStringWrapper(value)

        assertEquals(0, reverser.amountOfOperations)
        assertEquals(false, reverser.currentlyReversed)

        value = reverser.reversed()
        assertEquals(DUMMY_VALUE_1.reversed(), value)
        assertEquals(1, reverser.amountOfOperations)
        assertEquals(true, reverser.currentlyReversed)

        value = reverser.reversed()
        assertEquals(DUMMY_VALUE_1, value)
        assertEquals(2, reverser.amountOfOperations)
        assertEquals(false, reverser.currentlyReversed)
    }

    @Test
    fun testStaticHeader() {
        val interceptor = SingleHeaderInterceptor(HEADER_KEY_TEST_1, DUMMY_VALUE_1)
        interceptor.intercept(chain)
        assertEquals(DUMMY_VALUE_1, chain.request().header(HEADER_KEY_TEST_1))
    }

    @Test
    fun testStaticHeaders() {
        val interceptor = StaticHeadersInterceptor(
            mapOf(
                HEADER_KEY_TEST_1 to DUMMY_VALUE_1,
                HEADER_KEY_TEST_2 to DUMMY_VALUE_2,
                HEADER_KEY_TEST_3 to DUMMY_VALUE_3
            )
        )
        interceptor.intercept(chain)
        assertEquals(DUMMY_VALUE_1, chain.request().header(HEADER_KEY_TEST_1))
        assertEquals(DUMMY_VALUE_2, chain.request().header(HEADER_KEY_TEST_2))
        assertEquals(DUMMY_VALUE_3, chain.request().header(HEADER_KEY_TEST_3))
    }

    @Test
    fun testDynamicHeader() {
        val reversed = ReverseStringWrapper(DUMMY_VALUE_2)
        val interceptor = DynamicHeaderInterceptor(HEADER_KEY_TEST_1) { reversed.reversed() }
        interceptor.intercept(chain)
        assertEquals(DUMMY_VALUE_2.reversed(), chain.request().header(HEADER_KEY_TEST_1))
        interceptor.intercept(chain)
        assertEquals(DUMMY_VALUE_2, chain.request().header(HEADER_KEY_TEST_1))
        assertEquals(2, reversed.amountOfOperations)
    }

    @Test
    fun testDynamicHeaders() {
        val (reverser1, reverser2, reverser3) =
            Triple(ReverseStringWrapper(DUMMY_VALUE_1), ReverseStringWrapper(DUMMY_VALUE_2), ReverseStringWrapper(DUMMY_VALUE_3))
        val interceptor = DynamicHeadersInterceptor(
            mapOf(
                HEADER_KEY_TEST_1 to { reverser1.reversed() },
                HEADER_KEY_TEST_2 to { reverser2.reversed() },
                HEADER_KEY_TEST_3 to { reverser3.reversed() }
            )
        )

        interceptor.intercept(chain)
        assertEquals(DUMMY_VALUE_1.reversed(), chain.request().header(HEADER_KEY_TEST_1))
        assertEquals(DUMMY_VALUE_2.reversed(), chain.request().header(HEADER_KEY_TEST_2))
        assertEquals(DUMMY_VALUE_3.reversed(), chain.request().header(HEADER_KEY_TEST_3))

        interceptor.intercept(chain)
        assertEquals(DUMMY_VALUE_1, chain.request().header(HEADER_KEY_TEST_1))
        assertEquals(DUMMY_VALUE_2, chain.request().header(HEADER_KEY_TEST_2))
        assertEquals(DUMMY_VALUE_3, chain.request().header(HEADER_KEY_TEST_3))

        assertEquals(2, reverser1.amountOfOperations)
        assertEquals(2, reverser2.amountOfOperations)
        assertEquals(2, reverser3.amountOfOperations)
    }

    private class ReverseStringWrapper(initialValue: String) {

        private var latest: String = initialValue
        private var current = initialValue

        var amountOfOperations = 0
            private set

        val currentlyReversed: Boolean
            get() = amountOfOperations % 2 == 1

        fun reversed(): String {
            amountOfOperations++
            latest = current
            current = current.reversed()
            return current
        }
    }

    private companion object {
        private const val HEADER_KEY_TEST_1 = "test1"
        private const val HEADER_KEY_TEST_2 = "test2"
        private const val HEADER_KEY_TEST_3 = "test3"

        private const val DUMMY_VALUE_1 = "dummy.value.1"
        private const val DUMMY_VALUE_2 = "dummy.value.2"
        private const val DUMMY_VALUE_3 = "dummy.value.3"
    }
}
