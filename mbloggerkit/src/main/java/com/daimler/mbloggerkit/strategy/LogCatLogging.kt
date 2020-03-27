package com.daimler.mbloggerkit.strategy

import android.util.Log
import com.daimler.mbloggerkit.Priority

internal class LogCatLogging : LogStrategy {

    override fun log(priority: Priority, tag: String, message: String) {
        Log.println(priority.level, tag, message)
    }
}
