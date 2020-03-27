package com.daimler.mbloggerkit.strategy

import com.daimler.mbloggerkit.Priority
import com.daimler.mbloggerkit.export.ExportableLog
import com.daimler.mbloggerkit.export.Log
import java.io.File
import java.util.concurrent.Executors

internal open class DiskLogging(fileConfig: LogFileConfig) : ExportableLog {

    private val logFilePath = fileConfig.path

    private val logFileName = fileConfig.name

    private val logFileHandler = fileConfig.logFileProvider

    private val singleQueueExecutor = Executors.newSingleThreadExecutor()

    override fun log(priority: Priority, tag: String, message: String) {
        val logFile = logFileHandler.provideLogFile(logFilePath, logFileName)
        logFile?.let {
            if (it.exists().and(it.isDirectory.not())) {
                writeToLogFileInBackground(it, message)
            }
        }
    }

    override fun loadLog(): Log {
        val exportableLog = Log()
        val logFile = logFileHandler.provideLogFile(logFilePath, logFileName)
        logFile?.let { file ->
            file.forEachLine {
                exportableLog.addToLog(it)
            }
        }
        return exportableLog
    }

    private fun writeToLogFileInBackground(logFile: File, log: String) {
        singleQueueExecutor.execute {
            writeToFile(logFile, log)
        }
    }

    @Synchronized
    private fun writeToFile(logFile: File, log: String) {
        val newLineOrEmpty = if (logFile.length() == 0L) "" else "\n"
        logFile.appendText("$newLineOrEmpty$log")
    }

    data class LogFileConfig(val path: String, val name: String, val logFileProvider: LogFileProvider)
}
