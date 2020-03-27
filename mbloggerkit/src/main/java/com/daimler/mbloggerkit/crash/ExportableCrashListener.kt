package com.daimler.mbloggerkit.crash

import com.microsoft.appcenter.crashes.AbstractCrashesListener
import com.microsoft.appcenter.crashes.ingestion.models.ErrorAttachmentLog
import com.microsoft.appcenter.crashes.model.ErrorReport

internal class ExportableCrashListener(
    private val entries: Int,
    private val errorLogFileReader: ErrorLogFileReader
) : AbstractCrashesListener() {

    override fun getErrorAttachments(report: ErrorReport): Iterable<ErrorAttachmentLog> {
        return errorLogFileReader.readLatestErrorLog()
            ?.entries()
            ?.takeLast(entries)
            ?.map { it.message }
            ?.let {
                listOf(
                    ErrorAttachmentLog.attachmentWithText(it.joinToString("\n"), FILE_NAME)
                )
            } ?: super.getErrorAttachments(report)
    }

    private companion object {

        private const val FILE_NAME = "error_log.txt"
    }
}
