package com.daimler.mbcommonkit.utils

import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.Test

class TimeMeasurementUtilsTest {

    @Test
    fun testExecution() {
        var executed = false
        measureExecutionTime {
            executed = true
        }
        assertEquals("Action was not executed.", true, executed)
    }

    @Test
    fun testResult() {
        assertEquals("Return value is invalid.", true, measureExecutionTime {} >= 0)
    }
}
