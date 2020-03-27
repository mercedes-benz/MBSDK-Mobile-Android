package com.daimler.mbloggerkit.adapter

import com.daimler.mbloggerkit.LogMessage

interface LogAdapter {

    val isLoggable: Boolean

    fun log(tag: String, logMessage: LogMessage)
}
