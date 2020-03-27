package com.daimler.mbloggerkit.adapter

import android.content.Context
import com.daimler.mbloggerkit.Priority
import com.daimler.mbloggerkit.export.ExportableLog
import com.daimler.mbloggerkit.export.Log
import com.daimler.mbloggerkit.format.LogFormatter
import com.daimler.mbloggerkit.format.SimpleFileLogFormat
import com.daimler.mbloggerkit.strategy.DiskLogging
import com.daimler.mbloggerkit.strategy.ExternalDiskLogging
import com.daimler.mbloggerkit.strategy.LogFileProvider
import com.daimler.mbloggerkit.strategy.MemoryLogging
import com.daimler.mbloggerkit.strategy.SessionsBasedLogFileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PersistingLogAdapter private constructor(
    logFormatter: LogFormatter,
    loggingEnabled: Boolean = false,
    logStrategy: ExportableLog
) : FormattedLogAdapter(logFormatter, logStrategy), ExportableLog {

    override fun log(priority: Priority, tag: String, message: String) {
        logStrategy.log(priority, tag, message)
    }

    override fun loadLog(): Log {
        return (logStrategy as ExportableLog).loadLog()
    }

    override val isLoggable: Boolean = loggingEnabled

    class Builder(private val context: Context) {

        private val defaultFilePath = File(context.filesDir, DEFAULT_LOG_DIR).absolutePath
        private val defaultFileName = "${SimpleDateFormat(DATE_TIME_PATTERN, Locale.ENGLISH).format(
            Date(
                System
                    .currentTimeMillis()
            )
        )}$FILE_EXTENSION_LOG"
        private var loggingEnabled = false
        private var logFormatter: LogFormatter = SimpleFileLogFormat()
        private var logStrategy: ExportableLog = DiskLogging(DiskLogging.LogFileConfig(defaultFilePath, defaultFileName, SessionsBasedLogFileProvider()))

        companion object {
            private const val DEFAULT_MEMORY_CAPACITY = 200
            private const val FILE_EXTENSION_LOG = ".log"
            private const val DEFAULT_LOG_DIR = "logs"
            private const val DATE_TIME_PATTERN = "yyyy_MM_dd-HH_mm_ss_SSS"
        }

        fun setLoggingEnabled(enabled: Boolean) = apply {
            loggingEnabled = enabled
        }

        fun logFormatter(logFormatter: LogFormatter) = apply {
            this.logFormatter = logFormatter
        }

        fun useMemoryLogging(capacity: Int = DEFAULT_MEMORY_CAPACITY) = apply {
            this.logStrategy = MemoryLogging(capacity)
        }

        fun useExternalDiskLogging(
            path: String,
            fileName: String,
            logFileProvider: LogFileProvider = SessionsBasedLogFileProvider()
        ) = apply {
            this.logStrategy = ExternalDiskLogging(context, DiskLogging.LogFileConfig(path, fileName, logFileProvider))
        }

        fun build(): PersistingLogAdapter = PersistingLogAdapter(
            logFormatter = logFormatter,
            loggingEnabled = loggingEnabled,
            logStrategy = logStrategy
        )
    }
}
