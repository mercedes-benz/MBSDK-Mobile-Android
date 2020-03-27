package com.daimler.mbcarkit.utils

import com.daimler.mbcarkit.business.model.vehicle.StatusEnum
import com.daimler.mbcarkit.business.model.vehicle.VehicleAttribute
import com.daimler.mbcarkit.business.model.vehicle.unit.DisplayUnitCase
import com.daimler.mbcommonkit.utils.ifNotNull
import com.daimler.testutils.random.randomEnumValue
import java.util.Date
import kotlin.random.Random

internal inline fun <reified T, R : Enum<*>> randomVehicleAttribute(
    value: T? = null,
    displayUnitCase: DisplayUnitCase = DisplayUnitCase.DISPLAYUNIT_NOT_SET,
    unit: R? = null
) =
    VehicleAttribute(
        randomStatusEnum(),
        randomDate(),
        value ?: randomValue<T>(),
        ifNotNull(value, unit) { v, u ->
            VehicleAttribute.Unit(v.toString(), displayUnitCase, u)
        }
    )

private fun randomStatusEnum() = randomEnumValue<StatusEnum>()

private fun randomDate() =
    with(System.currentTimeMillis()) {
        Date((0 until this).random())
    }

private inline fun <reified T> randomValue(): T? =
    when (T::class) {
        Int::class -> Random.nextInt() as T
        Double::class -> Random.nextDouble() as T
        else -> null
    }
