package com.daimler.mbnetworkkit.task

import org.junit.Assert
import org.junit.Test

class BaseTaskTest {
    /**
     * Tests that a onComplete-Hook gets executed, if it is attached to to an already completed task.
     */
    @Test
    fun testOnCompletedOnCompletedTask() {
        val to = TaskObject<Int, String>()
        var hookExecuted = false
        to.complete(1)
        // when
        to.onComplete { hookExecuted = true }
        // then
        Assert.assertTrue(hookExecuted)
    }

    /**
     * Tests that a onFailure-Hook gets not executed, if it is attached to to an already completed task.
     */
    @Test
    fun testOnFailureOnCompletedTask() {
        val to = TaskObject<Int, String>()
        var hookExecuted = false
        to.complete(1)
        // when
        to.onFailure { hookExecuted = true }
        // then
        Assert.assertFalse(hookExecuted)
    }

    /**
     * Tests that a onFailure-Hook gets executed, if it is attached to to an already failed task.
     */
    @Test
    fun testOnFailureOnFailedTask() {
        val to = TaskObject<Int, String>()
        var hookExecuted = false
        to.fail("abc")
        // when
        to.onFailure { hookExecuted = true }
        // then
        Assert.assertTrue(hookExecuted)
    }
}
