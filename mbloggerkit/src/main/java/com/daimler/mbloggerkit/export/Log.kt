package com.daimler.mbloggerkit.export

import android.content.Context
import android.content.Intent
import java.io.File

class Log : Iterable<LogEntry> {

    private val logEntries = mutableListOf<LogEntry>()

    internal fun addToLog(entry: String) {
        logEntries.add(LogEntry(entry))
    }

    override fun iterator(): Iterator<LogEntry> {
        return logEntries.iterator()
    }

    internal fun entries() = logEntries

    override fun toString(): String {
        val sharedLog = StringBuilder()
        logEntries.forEach {
            sharedLog.appendLine(it.message)
        }
        return sharedLog.toString()
    }

    companion object {
        const val INTENT_FILE_TYPE = "plain/*"
        const val INTENT_TEXT_TYPE = "text/plain"
    }
}

fun Log.shareAsFile(context: Context, fileName: String) {
    val sharedLogFile = File(context.cacheDir, fileName)
    if (sharedLogFile.exists().not()) {
        if (sharedLogFile.createNewFile()) {
            shareLogFile(context, sharedLogFile, this)
        }
    } else {
        if (sharedLogFile.isDirectory.not()) {
            shareLogFile(context, sharedLogFile, this)
        }
    }
}

fun Log.shareAsText(context: Context) {
    val message = this.toString()
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        type = Log.INTENT_TEXT_TYPE
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share Log"))
}

private fun shareLogFile(context: Context, logFile: File, log: Log) {
    logFile.writeText(log.toString())
    val shareUri = MBLoggerKitFileProvider.getUriForFile(context, logFile)
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_STREAM, shareUri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        type = Log.INTENT_FILE_TYPE
    }
    context.startActivity(shareIntent)
}
