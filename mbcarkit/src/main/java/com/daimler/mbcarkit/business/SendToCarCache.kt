package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapabilities

interface SendToCarCache {

    fun loadCapabilities(finOrVin: String): SendToCarCapabilities?

    fun saveCapabilities(finOrVin: String, capabilities: SendToCarCapabilities)

    fun deleteCapabilities(finOrVin: String)

    fun deleteAllCapabilities()
}
