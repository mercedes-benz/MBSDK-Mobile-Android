package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.ServiceStatusDesire
import com.google.gson.annotations.SerializedName

internal data class ApiDesireServiceStatus(
    @SerializedName("serviceId") val serviceId: Int,
    @SerializedName("desiredServiceStatus") val status: ApiServiceDesire
) {
    companion object {
        fun fromServiceStatusDesire(serviceStatusDesire: ServiceStatusDesire) = ApiDesireServiceStatus(
            serviceStatusDesire.serviceId,
            if (serviceStatusDesire.activate) ApiServiceDesire.ACTIVE else ApiServiceDesire.INACTIVE
        )
    }
}
