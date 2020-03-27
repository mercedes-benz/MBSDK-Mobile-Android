package com.daimler.mbprotokit.mapping.car

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.generated.VehicleEvents
import kotlin.properties.ReadOnlyProperty

/**
 * Custom val property delegation to get value of the known proto properties
 * For more information: https://kotlinlang.org/docs/reference/delegated-properties.html
 *
 * @param attributes: this map is provided from the generated proto class.
 * Keys of the map are stored in [com.daimler.mbprotokit.mapping.car.ApiVehicleKey]
 *
 * @param apiKey: associated key to get proto data
 *
 * @return returns a Pair with the value and the attribute informations [com.daimler.mbprotokit.dto.car.AttributeInfo]
 *
 * OWNER is the property owner (e.g. data class)
 * VALUE is the expected type from proto specification
 *
 * Hint: Only supported for readonly attributes (val).
 */
internal inline fun <OWNER, reified VALUE> OWNER.proto(
    attributes: Map<String, VehicleEvents.VehicleAttributeStatus>,
    apiKey: ApiVehicleKey
): ReadOnlyProperty<OWNER, Pair<VALUE?, AttributeInfo>> =
    ReadOnlyProperty<OWNER, Pair<VALUE?, AttributeInfo>> { _, _ ->
        attributes[apiKey.id].getValueAndInfo<VALUE>(
            apiKey.id
        )
    }

/**
 * Custom val property delegation to get value of the known proto properties
 * and applies the given mapping strategy on the retrieved value.
 * For more information: https://kotlinlang.org/docs/reference/delegated-properties.html
 *
 * @param attributes: this map is provided from the generated proto class.
 * Keys of the map are stored in [com.daimler.mbprotokit.mapping.car.ApiVehicleKey]
 *
 * @param apiKey: associated key to get proto data
 *
 * @param mapStrategy: mapping strategy/delegation. Maps the retrieved value from proto to your WANTED type with the given strategy
 *
 * @return returns a Pair with the mapped value and the attribute informations [com.daimler.mbprotokit.dto.car.AttributeInfo]
 *
 * OWNER is the property owner (e.g. data class)
 * VALUE is the expected type from proto specification
 * WANTED is the type which is the result type after the given mapStrategy (VALUE to WANTED)
 *
 * Hint: Only supported for readonly attributes (val).
 *
 */
internal inline fun <OWNER, reified VALUE, WANTED> OWNER.proto(
    attributes: Map<String, VehicleEvents.VehicleAttributeStatus>,
    apiKey: ApiVehicleKey,
    crossinline mapStrategy: (VALUE?) -> (WANTED)
): ReadOnlyProperty<OWNER, Pair<WANTED?, AttributeInfo>> =
    ReadOnlyProperty<OWNER, Pair<WANTED?, AttributeInfo>> { _, _ ->
        attributes[apiKey.id].mapValueAndInfo<VALUE, WANTED>(
            apiKey.id,
            mapStrategy
        )
    }
