package com.daimler.mbmobilesdk.utils.extensions

fun StringBuilder.blank(): StringBuilder = append(" ")

fun StringBuilder.newLine(): StringBuilder = append('\n')

fun <T : Any> createStringOf(
    iterable: Iterable<T>,
    formatter: (T) -> String = { it.toString() }
): String {
    val builder = StringBuilder()
    iterable.forEach { builder.append(formatter(it)).newLine() }
    return builder.toString()
}