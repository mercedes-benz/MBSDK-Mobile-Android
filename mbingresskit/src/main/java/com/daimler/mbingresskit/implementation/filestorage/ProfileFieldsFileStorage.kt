package com.daimler.mbingresskit.implementation.filestorage

import android.content.Context
import com.daimler.mbcommonkit.filestorage.FileStorage
import com.daimler.mbcommonkit.filestorage.FileWriter
import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbloggerkit.MBLoggerKit
import com.google.gson.Gson
import java.io.File

internal class ProfileFieldsFileStorage(
    private val context: Context,
    private val fileWriter: FileWriter<String>
) : FileStorage<ProfileFieldsData, ProfileFieldsData> {

    override fun writeToFile(data: ProfileFieldsData): String? {
        return writeToFile(data, DEFAULT_FILE_IDENTIFIER)
    }

    override fun writeToFile(data: ProfileFieldsData, identifier: String): String? {
        val dir = subDir()
        return if (dir.exists() || dir.mkdirs()) {
            try {
                val outFile = File(dir, fileName(identifier))
                fileWriter.writeToFile(data.toJson(), outFile)
            } catch (e: Exception) {
                MBLoggerKit.e("Failed to write data to file.", throwable = e)
                null
            }
        } else {
            MBLoggerKit.e("Cannot create directory ${dir.absolutePath}.")
            null
        }
    }

    override fun readFromFile(data: ProfileFieldsData): ProfileFieldsData? {
        return readFromFile(DEFAULT_FILE_IDENTIFIER)
    }

    override fun readFromFile(identifier: String): ProfileFieldsData? {
        val dir = subDir()
        val file = File(dir, fileName(identifier))
        return if (file.exists()) {
            try {
                val json = fileWriter.readFile(file)
                dataFromJson(json)
            } catch (e: Exception) {
                MBLoggerKit.e("Failed to read from file.", throwable = e)
                null
            }
        } else {
            MBLoggerKit.e("File for identifier $identifier does not exist.")
            null
        }
    }

    override fun deleteFiles(): Boolean {
        val dir = subDir()
        return try {
            dir.deleteRecursively()
        } catch (e: Exception) {
            MBLoggerKit.e("Failed to delete files.", throwable = e)
            false
        }
    }

    private fun subDir() = File(context.filesDir, PROFILE_FIELDS_DIR)

    private fun fileName(identifier: String) = "$identifier$FILE_EXTENSION"

    private fun ProfileFieldsData.toJson() = Gson().toJson(this)

    private fun dataFromJson(json: String?) =
        json?.let { Gson().fromJson(json, ProfileFieldsData::class.java) }

    private companion object {

        private const val PROFILE_FIELDS_DIR = "fields/"
        private const val DEFAULT_FILE_IDENTIFIER = "default"
        private const val FILE_EXTENSION = ".json"
    }
}
