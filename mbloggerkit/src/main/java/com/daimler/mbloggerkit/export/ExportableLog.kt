package com.daimler.mbloggerkit.export

import com.daimler.mbloggerkit.strategy.LogStrategy

interface ExportableLog : LogStrategy {
    fun loadLog(): Log
}
