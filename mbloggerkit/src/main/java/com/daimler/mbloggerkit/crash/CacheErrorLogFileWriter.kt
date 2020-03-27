package com.daimler.mbloggerkit.crash

import android.content.Context
import com.daimler.mbloggerkit.export.Log
import com.daimler.mbloggerkit.strategy.createFileIfNotExists
import java.io.File

internal class CacheErrorLogFileWriter(
    private val context: Context
) : ErrorLogFileWriter {

    override fun writeLog(log: Log) {
        val file = newLogFile() ?: return
        val lines = log.entries().map { it.message }
        val text = lines.joinToString("\n")
        file.writeText(text)
    }

    override fun readLatestErrorLog(): Log? {
        return lastModifiedLogFile()?.readLines()?.let { lines ->
            Log().apply {
                lines.forEach {
                    addToLog(it)
                }
            }
        }
    }

    private fun newLogFile(): File? {
        val subDir = File(context.cacheDir, SUB_DIR)
        val file = File(subDir, createFileName())
        val created = createFileIfNotExists(file)
        return if (created) file else null
    }

    private fun createFileName() = "${System.currentTimeMillis()}_$ERROR_FILE_NAME"

    private fun lastModifiedLogFile(): File? {
        val subDir = File(context.cacheDir, SUB_DIR)
        if (!subDir.exists()) return null

        return subDir.listFiles()
            ?.takeIf {
                it.isNotEmpty()
            }?.maxByOrNull {
                it.lastModified()
            }
    }

    private companion object {

        private const val SUB_DIR = "logger/crashes/"
        private const val ERROR_FILE_NAME = "mbloggerkit_crash_log.log"
    }
}
