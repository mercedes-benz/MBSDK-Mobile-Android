package com.daimler.mbloggerkit.strategy

import com.daimler.mbloggerkit.Priority

interface LogStrategy {
    fun log(priority: Priority, tag: String, message: String)
}
