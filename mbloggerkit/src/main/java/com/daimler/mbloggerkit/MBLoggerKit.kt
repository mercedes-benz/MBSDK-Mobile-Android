package com.daimler.mbloggerkit

import androidx.annotation.WorkerThread
import com.daimler.mbloggerkit.export.Log

/**
 * This module contains the Logger used by all other Mercedes-Benz Mobile SDK modules.
 * Initialize it the following way in your [Application] subclass:
 *
 * ```
 * MBLoggerKit.usePrinterConfig(
 *     PrinterConfig.Builder()
 *         .addAdapter(AndroidLogAdapter.Builder()
 *         .setLoggingEnabled(loggingEnabled)
 *         .build()
 *     )
 *     .build()
 * )
 * ```
 *
 * Log your statements as you would with standard Android logs but use the class [MBLoggerKit] instead of Log,
 * e.g. ```MBLoggerKit.d("My debug log statement.")```
 */
object MBLoggerKit {

    private var printer: LogPrinter = LogPrinter(PrinterConfig.Builder().build())

    fun usePrinterConfig(printerConfig: PrinterConfig) {
        printer = LogPrinter(printerConfig)
    }

    fun v(message: String, tag: String? = null, throwable: Throwable? = null) {
        printer.log(Priority.VERBOSE, tag, message, throwable)
    }

    fun d(message: String, tag: String? = null, throwable: Throwable? = null) {
        printer.log(Priority.DEBUG, tag, message, throwable)
    }

    fun i(message: String, tag: String? = null, throwable: Throwable? = null) {
        printer.log(Priority.INFO, tag, message, throwable)
    }

    fun w(message: String, tag: String? = null, throwable: Throwable? = null) {
        printer.log(Priority.WARN, tag, message, throwable)
    }

    fun e(message: String, tag: String? = null, throwable: Throwable? = null) {
        printer.log(Priority.ERROR, tag, message, throwable)
    }

    fun wtf(message: String, tag: String? = null, throwable: Throwable? = null) {
        printer.log(Priority.WTF, tag, message, throwable)
    }

    fun log(priority: Priority, message: String, tag: String? = null, throwable: Throwable? = null) {
        printer.log(priority, tag, message, throwable)
    }

    @WorkerThread
    fun loadCurrentLog(): Log {
        return printer.loadCurrentLog()
    }
}
