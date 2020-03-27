package com.daimler.testutils.reflection

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class ReflectionsTest {

    @Test
    fun `findFields() should return all fields with specified type`(softly: SoftAssertions) {
        val subject = TestClass()
        val fields = subject.findFields<TestInterface>()
        softly.assertThat(fields.size).isEqualTo(3)
        softly.assertThat(fields).allMatch { it.isAccessible }
    }

    @Test
    fun `mockFields() should set all fields of specified type`(softly: SoftAssertions) {
        val subject = TestClass()
        val impl = TestInterfaceImpl3()
        subject.mockFields<TestInterface>(impl)
        softly.assertThat(subject.test1).isInstanceOf(TestInterfaceImpl3::class.java)
        softly.assertThat(subject.test2).isInstanceOf(TestInterfaceImpl3::class.java)
        val field = subject.javaClass.getDeclaredField("test3")
        field.isAccessible = true
        softly.assertThat(field.get(subject)).isInstanceOf(TestInterfaceImpl3::class.java)
    }

    private class TestClass {
        val test1: TestInterface = TestInterfaceImpl1()
        val test2: TestInterface = TestInterfaceImpl2()
        private val test3: TestInterface = TestInterfaceImpl2()
    }

    private interface TestInterface
    private class TestInterfaceImpl1 : TestInterface
    private class TestInterfaceImpl2 : TestInterface
    private class TestInterfaceImpl3 : TestInterface
}
