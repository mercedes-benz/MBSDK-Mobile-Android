package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.command.capabilities.CommandCapabilities

interface CommandCapabilitiesCache {

    fun writeCache(finOrVin: String, capabilities: CommandCapabilities)

    fun loadFromCache(finOrVin: String): CommandCapabilities?

    fun deleteForFinOrVin(finOrVin: String)

    fun deleteAll()
}
