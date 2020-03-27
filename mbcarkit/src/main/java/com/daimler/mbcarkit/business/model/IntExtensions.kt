package com.daimler.mbcarkit.business.model

inline fun <reified E : Enum<E>> Int?.toEnum(): E? {
    return this?.let { enumValues<E>().getOrNull(it) }
}
