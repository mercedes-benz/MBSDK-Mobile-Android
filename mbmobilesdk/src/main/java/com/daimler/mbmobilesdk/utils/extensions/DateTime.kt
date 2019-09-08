package com.daimler.mbmobilesdk.utils.extensions

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateTime {
    val dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
    @SuppressLint("SimpleDateFormat")
    val simpleDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm Z")
    val simpleTimeFormat = SimpleDateFormat("HH:mm Z")
    val simpleTimeFormat24 = SimpleDateFormat("HH:mm")
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
}

fun Date.toLocalDateTimeString(): String {
    return DateTime.dateTimeFormat.format(this)
}

fun Date.toCacDateTimeString(): String {
    return DateTime.simpleDateTimeFormat.format(this)
}

fun Date.toLocalTime12String(): String {
    return DateTime.simpleTimeFormat.format(this)
}

fun Date.toLocalTime24String(): String {
    return DateTime.simpleTimeFormat24.format(this)
}

fun Date.toOnlyDate(): String {
    return DateTime.simpleDateFormat.format(this)
}
