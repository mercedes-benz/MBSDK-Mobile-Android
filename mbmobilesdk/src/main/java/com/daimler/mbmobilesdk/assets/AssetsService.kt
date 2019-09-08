package com.daimler.mbmobilesdk.assets

interface AssetsService {

    fun read(fileName: String): String

    fun <T> parseFromJson(fileName: String, cls: Class<T>): T
}