package com.daimler.mbmobilesdk.assets

import android.content.Context
import com.google.gson.Gson

internal open class MBMobileSDKAssetsService(
    private val context: Context,
    private val relativeAssetsPath: String
) : AssetsService {

    override fun read(fileName: String): String {
        val path = "$relativeAssetsPath$fileName"
        val stream = context.assets.open(path)
        return stream.bufferedReader().use { it.readText() }
    }

    override fun <T> parseFromJson(fileName: String, cls: Class<T>): T {
        val json = read(fileName)
        return Gson().fromJson(json, cls)
    }
}