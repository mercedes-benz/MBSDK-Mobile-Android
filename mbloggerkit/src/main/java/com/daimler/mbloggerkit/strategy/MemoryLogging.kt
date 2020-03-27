package com.daimler.mbloggerkit.strategy

import com.daimler.mbloggerkit.Priority
import com.daimler.mbloggerkit.export.ExportableLog
import com.daimler.mbloggerkit.export.Log

internal class MemoryLogging(capacity: Int) : ExportableLog {

    private val capacity = if (capacity < 1) throw IllegalArgumentException("Capacity must be at least 1") else capacity

    private val logs = mutableListOf<CachedLog>()

    override fun log(priority: Priority, tag: String, message: String) {
        synchronized(this) {
            if (logs.size >= capacity) {
                logs.removeAt(0)
            }
            logs.add(CachedLog(priority, tag, message))
        }
    }

    override fun loadLog(): Log {
        val exportableLog = Log()
        logs.forEach {
            exportableLog.addToLog(it.message)
        }
        return exportableLog
    }

    private data class CachedLog(val priority: Priority, val tag: String, val message: String)
}
