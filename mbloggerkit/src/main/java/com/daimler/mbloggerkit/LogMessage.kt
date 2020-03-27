package com.daimler.mbloggerkit

data class LogMessage(val timestamp: Long, val priority: Priority, val message: String, val throwable: Throwable?)
