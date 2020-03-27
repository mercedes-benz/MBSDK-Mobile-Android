package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.DisplayUnitCase
import java.text.NumberFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

data class VehicleAttribute<T, R : Enum<*>>(
    val status: StatusEnum,
    val lastChanged: Date?,
    val value: T?,
    val unit: Unit<R>?
) {

    fun isValid() = status == StatusEnum.VALID

    fun isInvalid() = status == StatusEnum.INVALID

    fun isNoValue() = status == StatusEnum.NO_VALUE

    /**
     * Returns the timestamp of the last change of the attribute in seconds in Unix time.
     */
    fun timestamp(): Long? {
        return lastChanged?.let {
            TimeUnit.MILLISECONDS.toSeconds(it.time)
        }
    }

    data class Unit<R : Enum<*>>(val displayValue: String, val displayUnitCase: DisplayUnitCase, val displayUnit: R)
}

/**
 * Formats the units display value to a location based formated number.
 *
 * @param locale The locale used for localisation
 *
 * @throws NumberFormatException If the displayValue of Unit is not a valid double
 */
fun VehicleAttribute.Unit<*>.localisedDisplayValue(locale: Locale = Locale.getDefault()): String {
    if (displayValue.isEmpty()) {
        return displayValue
    }

    val numberFormat = NumberFormat.getInstance(locale)
    numberFormat.minimumFractionDigits = displayValue.split(".").getOrNull(1)?.length ?: 0
    return numberFormat.format(displayValue.toDouble())
}
