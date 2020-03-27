package com.daimler.mbprotokit.dto.car.vehicledata

enum class FilterParticleState(val id: Int) {
    HIGH(0),
    MEDIUM(1),
    LOW(2);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> FilterParticleState? = { map(it) }
    }
}
