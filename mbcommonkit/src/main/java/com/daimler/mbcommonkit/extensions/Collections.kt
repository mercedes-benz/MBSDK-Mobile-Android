package com.daimler.mbcommonkit.extensions

/**
 * Returns a list that contains duplications of [this]. Duplicates are selected
 * by the [selector]. The result list will contain all duplicates if [this] contains multiple
 * duplicates of the same element.
 *
 * ```
 *  data class MyClass(val id: Int)
 *
 *  val list = Arrays.asList(MyClass(2), MyClass(1), MyClass(2))
 *  val duplicates = list.duplicates { it.id }
 *  // duplicates: [MyClass(2)]
 * ```
 */
fun <T, K> Iterable<T>.duplicates(selector: (T) -> K): List<T> {
    val set = HashSet<K>()
    val result = ArrayList<T>()
    for (e in this) {
        val k = selector(e)
        if (set.add(k).not()) {
            result.add(e)
        }
    }
    return result
}

/**
 * Same as [duplicates] but uses the element itself for comparison.
 *
 * ```
 *  val list = Arrays.asList(1, 5, 5, 2)
 *  val duplicates = list.duplicates()
 *  // duplicates: [5]
 * ```
 */
fun <T> Iterable<T>.duplicates(): List<T> = duplicates { it }

/**
 * Adds all elements from [from] to [this].
 */
fun <T> List<T>.takeFrom(from: Collection<T>): List<T> {
    return ArrayList(this).takeFrom(from)
}

/**
 * See [takeFrom].
 */
fun <T> ArrayList<T>.takeFrom(from: Collection<T>): MutableList<T> {
    addAll(from)
    return this
}

/**
 * Replace the element at index [index] with [newItem].
 */
fun <T> MutableList<T>.replaceAt(index: Int, newItem: T) {
    if (index !in (0..size)) throw IllegalArgumentException("Index must be between 0 and $size!")
    removeAt(index)
    add(index, newItem)
}

/**
 * Replaces [element] with [newElement].
 *
 * @return true if [element] existed in the list and could be replaced
 */
fun <T> MutableList<T>.replace(element: T, newElement: T): Boolean {
    val index = indexOf(element)
    return replaceAtChecked(index, newElement)
}

/**
 * Replaces the first element that matches the [selector] condition with [newElement].
 *
 * @return true if there was an element that matched the given condition and could be replaced
 */
fun <T> MutableList<T>.replace(newElement: T, selector: (T) -> Boolean): Boolean {
    val index = indexOfFirst(selector)
    return replaceAtChecked(index, newElement)
}

private fun <T> MutableList<T>.replaceAtChecked(index: Int, newElement: T): Boolean {
    if (index in (0..size)) {
        replaceAt(index, newElement)
        return true
    }
    return false
}
