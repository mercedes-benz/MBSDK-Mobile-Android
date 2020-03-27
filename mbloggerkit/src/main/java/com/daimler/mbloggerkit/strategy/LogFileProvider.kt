package com.daimler.mbloggerkit.strategy

import java.io.File

interface LogFileProvider {
    fun provideLogFile(path: String, name: String): File?
}

internal fun createFileIfNotExists(logFile: File): Boolean {
    return if (logFile.exists()) {
        true
    } else {
        val parentDir = logFile.parentFile
        if (parentDir?.exists() == true) {
            logFile.createNewFile()
        } else {
            val createdParentDirs = parentDir?.mkdirs()
            if (createdParentDirs == true) {
                logFile.createNewFile()
            } else {
                false
            }
        }
    }
}
