package com.daimler.testutils.reflection

inline fun <reified T> Any.findFields() =
    javaClass.declaredFields
        .filter { it.type == T::class.java }
        .apply {
            forEach { it.isAccessible = true }
        }

inline fun <reified T> Any.mockFields(value: T) {
    findFields<T>()
        .forEach {
            it.set(this, value)
        }
}
