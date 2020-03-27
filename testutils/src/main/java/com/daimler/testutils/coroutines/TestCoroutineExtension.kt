package com.daimler.testutils.coroutines

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.junit.jupiter.api.extension.TestInstancePostProcessor

/**
 * Creates and holds both a [TestCoroutineScope] and a [TestCoroutineDispatcher].
 * The dispatcher will be set as the main dispatcher using [Dispatchers.setMain] before each test
 * and [Dispatchers.resetMain] after each test.
 * You can implement [CoroutineTest.attachScope] if you need direct access to the scope, e.g.
 * if your subject class requires a scope parameter in its constructor.
 * This extension will also inject the scope into each of your test methods.
 * ```
 * @Test
 * fun `myTest`(scope: TestCoroutineScope) {
 *     scope.runBlockingTest {
 *         // test coroutine
 *     }
 * }
 * ```
 */
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoroutinesApi
class TestCoroutineExtension :
    TestInstancePostProcessor,
    BeforeEachCallback,
    AfterEachCallback,
    ParameterResolver {

    private val dispatcher = TestCoroutineDispatcher()
    private val scope = TestCoroutineScope(dispatcher)

    override fun postProcessTestInstance(testInstance: Any, context: ExtensionContext) {
        (testInstance as? CoroutineTest)?.attachScope(scope)
    }

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(dispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        dispatcher.cleanupTestCoroutines()
        scope.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

    override fun supportsParameter(
        parameterContext: ParameterContext,
        extensionContext: ExtensionContext
    ): Boolean = parameterContext.parameter.type == TestCoroutineScope::class.java

    override fun resolveParameter(
        parameterContext: ParameterContext,
        extensionContext: ExtensionContext
    ): Any = scope
}
