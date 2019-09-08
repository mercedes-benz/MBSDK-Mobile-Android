package com.daimler.mbmobilesdk.utils

fun <A, B, R> ifNotNull(a: A?, b: B?, action: (A, B) -> R?): R? =
    if (a != null && b != null) action(a, b) else null

fun <A, B, C, R> ifNotNull(a: A?, b: B?, c: C?, action: (A, B, C) -> R?): R? =
    if (a != null && b != null && c != null) action(a, b, c) else null