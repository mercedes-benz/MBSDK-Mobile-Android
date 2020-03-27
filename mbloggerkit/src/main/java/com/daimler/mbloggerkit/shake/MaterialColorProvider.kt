package com.daimler.mbloggerkit.shake

import com.daimler.mbloggerkit.Priority

class MaterialColorProvider : LogRecyclerAdapter.ColorProvider {
    override fun getColor(priority: Priority): String {
        return when (priority) {
            Priority.VERBOSE -> "#FF9E9E9E"
            Priority.DEBUG -> "#FF2196F3"
            Priority.INFO -> "#FFFFC107"
            Priority.WARN -> "#FFFF9800"
            Priority.ERROR -> "#FFF44336"
            Priority.WTF -> "#FF9C27B0"
        }
    }
}
