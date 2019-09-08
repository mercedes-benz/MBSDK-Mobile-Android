package com.daimler.mbmobilesdk.utils

fun checkParameterNotNull(paramName: String, value: Any?) {
    if (value == null) throw IllegalArgumentException("You need to provide a value for $paramName.")
}

fun checkParameterNotBlank(paramName: String, value: String?) {
    if (value.isNullOrBlank()) {
        throw IllegalArgumentException("You need to provide a non-blank value for $paramName.")
    }
}

fun checkParameterNot(paramName: String, value: Any?, other: Any?) {
    if (value == other) {
        throw IllegalArgumentException("You need to provide a valid value for $paramName.")
    }
}

inline fun <reified T> checkParameterType(paramName: String, value: Any?) {
    if (value !is T) {
        throw IllegalArgumentException("$paramName must be of type ${T::class.java}")
    }
}