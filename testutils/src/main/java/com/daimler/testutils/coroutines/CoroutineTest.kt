package com.daimler.testutils.coroutines

import kotlinx.coroutines.CoroutineScope

/**
 * Used by [TestCoroutineExtension] in case you need directly access to the used
 * [CoroutineScope].
 */
interface CoroutineTest {

    /**
     * Called once after the test class was instantiated.
     *
     * @param scope the [CoroutineScope] used for tests
     */
    fun attachScope(scope: CoroutineScope)
}
