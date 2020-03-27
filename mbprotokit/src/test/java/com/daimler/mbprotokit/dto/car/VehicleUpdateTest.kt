package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import org.junit.jupiter.api.Test
import kotlin.random.Random

class VehicleUpdateTest {

    @Test
    fun performanceMappingTest() {
        val attributes = ApiVehicleKey.values().map {
            it.id to VehicleEvents.VehicleAttributeStatus.getDefaultInstance()
        }.toMap()
        var processingTime = 0L
        val start = System.nanoTime()
        VehicleUpdate(
            Random.nextBoolean(),
            "123",
            Random.nextLong(),
            Random.nextInt(),
            attributes
        )
        processingTime += System.nanoTime() - start

        println("Benchmark Mapping: ${processingTime / 1000000} ms")
    }
}
