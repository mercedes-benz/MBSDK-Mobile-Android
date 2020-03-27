package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.ServiceGroupOption

internal enum class ApiServiceGroupOption(val apiName: String) {
    CATEGORY("categoryName");

    companion object {
        private val map: Map<String, ApiServiceGroupOption> = values().associateBy(ApiServiceGroupOption::name)

        fun fromServiceGroupOption(serviceGroupOption: ServiceGroupOption) = map[serviceGroupOption.name]
    }
}
