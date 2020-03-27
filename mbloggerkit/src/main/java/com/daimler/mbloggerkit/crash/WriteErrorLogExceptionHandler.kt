package com.daimler.mbloggerkit.crash

import com.daimler.mbloggerkit.MBLoggerKit

internal class WriteErrorLogExceptionHandler(
    private val errorLogFileWriter: ErrorLogFileWriter
) : ExceptionHandler, Thread.UncaughtExceptionHandler {

    private var defaultExceptionHandler: Thread.UncaughtExceptionHandler? = null
    private var registered = false

    override fun register() {
        if (registered) return
        registered = true
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun unregister() {
        if (!registered) return
        Thread.setDefaultUncaughtExceptionHandler(defaultExceptionHandler)
        defaultExceptionHandler = null
        registered = false
    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        errorLogFileWriter.writeLog(MBLoggerKit.loadCurrentLog())
        t?.let { thread ->
            e?.let { throwable ->
                defaultExceptionHandler?.uncaughtException(thread, throwable)
            }
        }
    }
}
