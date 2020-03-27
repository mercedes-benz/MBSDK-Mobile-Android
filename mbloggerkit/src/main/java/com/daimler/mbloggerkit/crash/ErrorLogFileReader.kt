package com.daimler.mbloggerkit.crash

import com.daimler.mbloggerkit.export.Log

internal interface ErrorLogFileReader {

    fun readLatestErrorLog(): Log?
}
