package com.daimler.mbcommonkit.utils

/**
 * expression for <code>let</code> on multiple variables
 * @param a first variable to be not null
 * @param b second variable to be not null
 */
fun <A, B, R> ifNotNull(a: A?, b: B?, action: (A, B) -> R?): R? =
    if (a != null && b != null) action(a, b) else null

/**
 * expression for <code>let</code> on multiple variables
 * @param a first variable to be not null
 * @param b second variable to be not null
 * @param c second variable to be not null
 */
fun <A, B, C, R> ifNotNull(a: A?, b: B?, c: C?, action: (A, B, C) -> R?): R? =
    if (a != null && b != null && c != null) action(a, b, c) else null
