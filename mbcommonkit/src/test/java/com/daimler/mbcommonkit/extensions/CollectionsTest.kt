package com.daimler.mbcommonkit.extensions

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.jupiter.api.Test
import kotlin.collections.ArrayList

class CollectionsTest {

    private val list = ArrayList(listOf("MyString", "mystring"))

    @Test
    fun testDuplicatesDefault() {
        assertEquals(
            "Wrong detection of duplicates.",
            true,
            list.duplicates().isEmpty()
        )
    }

    @Test
    fun testDuplicatesSelector() {
        assertEquals(
            "Duplicates not detected.",
            true,
            list.duplicates { it.toLowerCase() }.size == 1
        )
        val tmp = ArrayList(list)
        tmp.add("mYString")
        assertEquals(
            "Duplicates not detected.",
            true,
            tmp.duplicates { it.toLowerCase() }.size == 2
        )
    }

    @Test
    fun testTakeFrom() {
        val first = listOf(1, 2, 3)
        val second = listOf(4, 5, 6)
        val all = first.takeFrom(second)
        assertEquals(
            "Concatenated list does not contain all elements.",
            true,
            all.containsAll(first) && all.containsAll(second)
        )
        var correctOrder = true
        all.forEachIndexed { index, e -> correctOrder = correctOrder && (index == e - 1) }
        assertEquals("The order of the concatenated list is wrong.", true, correctOrder)
    }

    @Test
    fun testReplaceAt() {
        val list = mutableListOf(1, 2, 3, 4, 5)
        list.replaceAt(2, 4)
        assertTrue(list.size == 5)
        assertTrue(list[1] == 2)
        assertTrue(list[2] == 4)
        assertTrue(list[3] == 4)
        assertTrue(!list.contains(3))
    }

    @Test
    fun testReplace() {
        val list = mutableListOf("a", "b", "c", "d", "e")
        list.replace("c", "x")
        assertTrue(list.size == 5)
        assertTrue(list[1] == "b")
        assertTrue(list[2] == "x")
        assertTrue(list[3] == "d")
        assertTrue(!list.contains("c"))
    }

    @Test
    fun testReplaceSelector() {
        val list: MutableList<DummyModel> = mutableListOf(DummyModel(1), DummyModel(2), DummyModel(3))
        list.replace<DummyModel>(DummyModel(1)) { it.value == 2 }
        assertTrue(list.size == 3)
        assertTrue(list[0].value == 1)
        assertTrue(list[1].value == 1)
        assertTrue(list[2].value == 3)
    }

    private data class DummyModel(val value: Int)
}
