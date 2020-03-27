package com.daimler.mbloggerkit.crash

import com.daimler.mbloggerkit.export.Log

internal interface ErrorLogFileWriter : ErrorLogFileReader {

    fun writeLog(log: Log)
}
