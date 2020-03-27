package com.daimler.mbmobilesdk.utils.extensions

import java.util.Locale

fun Locale.format() = formatWithSeparator("-")

fun Locale.formatWithSeparator(separator: String) = "$language$separator$country"
