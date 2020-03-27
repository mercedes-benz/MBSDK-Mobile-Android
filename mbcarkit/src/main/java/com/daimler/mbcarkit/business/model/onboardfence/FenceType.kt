package com.daimler.mbcarkit.business.model.onboardfence

enum class FenceType {
    CIRCLE,
    POLYGON;

    companion object {
        val map: Map<String, FenceType> = values().associateBy(FenceType::name)
    }
}
