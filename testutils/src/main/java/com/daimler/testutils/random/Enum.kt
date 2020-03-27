package com.daimler.testutils.random

inline fun <reified T : Enum<T>> randomEnumValue() = enumValues<T>().random()
