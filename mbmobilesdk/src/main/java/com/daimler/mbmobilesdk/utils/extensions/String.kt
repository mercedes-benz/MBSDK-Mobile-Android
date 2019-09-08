package com.daimler.mbmobilesdk.utils.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

internal fun String?.orNullIfBlank() = if (isNullOrBlank()) null else this

internal fun String.toDate(pattern: String, locale: Locale = Locale.getDefault()): Date? =
    try {
        SimpleDateFormat(pattern, locale).parse(this)
    } catch (e: ParseException) {
        null
    }