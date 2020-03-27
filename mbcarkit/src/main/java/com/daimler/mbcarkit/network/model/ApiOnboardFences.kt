package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.onboardfence.OnboardFence
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiOnboardFences(
    @SerializedName("onboardfences") val onboardFences: List<ApiOnboardFence>?
) : Mappable<List<OnboardFence>> {

    override fun map(): List<OnboardFence> =
        onboardFences?.map { it.toOnboardFence() } ?: emptyList()
}
