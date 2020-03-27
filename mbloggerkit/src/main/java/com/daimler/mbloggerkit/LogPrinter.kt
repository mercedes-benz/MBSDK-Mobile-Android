package com.daimler.mbloggerkit

import com.daimler.mbloggerkit.export.ExportableLog
import com.daimler.mbloggerkit.export.Log

internal class LogPrinter internal constructor(printerConfig: PrinterConfig) {

    private val adapters = printerConfig.adapters

    private val tagStrategy = printerConfig.tagStrategy

    internal fun log(priority: Priority, tag: String? = null, message: String, throwable: Throwable? = null) {
        val logTimestamp = System.currentTimeMillis()
        val logTag = tagStrategy.createTag(tag)
        adapters.filter {
            it.isLoggable
        }.forEach {
            it.log(logTag, LogMessage(logTimestamp, priority, message, throwable))
        }
    }

    internal fun loadCurrentLog(): Log {
        val exportableLogAdapter = adapters.firstOrNull { it is ExportableLog }
        return exportableLogAdapter?.let {
            (it as ExportableLog).loadLog()
        } ?: Log()
    }
}
