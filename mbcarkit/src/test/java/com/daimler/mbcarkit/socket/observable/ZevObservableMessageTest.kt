package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.business.model.vehicle.Zev
import com.daimler.mbcarkit.business.model.vehicle.ZevTemperature
import com.daimler.mbcarkit.utils.randomVehicleAttribute
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class ZevObservableMessageTest {

    @Test
    fun `verify hasChanged`(softly: SoftAssertions) {
        val oldZev = randomZev()
        val newZev = oldZev.copy(
            endOfChargeTime = oldZev.endOfChargeTime.copy(
                value = (oldZev.endOfChargeTime.value ?: 1) * 2
            )
        )
        val oldStatus = mockk<VehicleStatus>().also {
            every { it.zev } returns oldZev
        }
        val newStatus = mockk<VehicleStatus>().also {
            every { it.zev } returns newZev
        }
        softly.assertThat(ZevObservableMessage(newZev).hasChanged(oldStatus, newStatus)).isTrue
        softly.assertThat(ZevObservableMessage(newZev).hasChanged(newStatus, oldStatus)).isTrue
        softly.assertThat(ZevObservableMessage(newZev).hasChanged(oldStatus, oldStatus)).isFalse
        softly.assertThat(ZevObservableMessage(newZev).hasChanged(newStatus, newStatus)).isFalse
    }

    private fun randomZev() = Zev(
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        ZevTemperature(
            randomVehicleAttribute(),
            randomVehicleAttribute(),
            randomVehicleAttribute(),
            randomVehicleAttribute(),
            randomVehicleAttribute(),
            randomVehicleAttribute(),
            randomVehicleAttribute()
        ),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute(),
        randomVehicleAttribute()
    )
}
