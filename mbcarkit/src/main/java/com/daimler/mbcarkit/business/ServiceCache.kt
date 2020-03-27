package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbcarkit.business.model.services.ServiceResolvedPrecondition
import com.daimler.mbcarkit.business.model.services.ServiceUpdate

interface ServiceCache {

    fun updateServices(finOrVin: String, services: List<Service>, fillPreconditions: Boolean)

    fun loadServices(finOrVin: String): List<Service>?

    fun loadServiceById(finOrVin: String, id: Int): Service?

    fun loadServicesById(finOrVin: String, ids: List<Int>): List<Service>?

    fun updateServiceStatus(finOrVin: String, updates: List<ServiceUpdate>)

    fun updatePrecondition(finOrVin: String, precondition: ServiceResolvedPrecondition)

    fun clearForFinOrVin(finOrVin: String?)

    fun clearAll()

    fun deleteServices(finOrVin: String, services: List<Service>)
}
