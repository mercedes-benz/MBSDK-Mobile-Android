package com.daimler.mbmobilesdk.utils.extensions

import android.content.Context
import android.content.Intent
import com.google.gson.Gson

fun <T : Any> Intent.putAsJson(key: String, t: T) {
    val json = Gson().toJson(t)
    putExtra(key, json)
}

fun <T : Any> Intent.getFromJson(key: String, type: Class<T>): T? {
    val json = getStringExtra(key)
    return json?.let { Gson().fromJson(json, type) }
}

inline fun <reified T : Any> Intent.getFromJson(key: String): T? {
    return getFromJson(key, T::class.java)
}

fun Intent.isResolvable(context: Context) =
    resolveActivity(context.packageManager) != null