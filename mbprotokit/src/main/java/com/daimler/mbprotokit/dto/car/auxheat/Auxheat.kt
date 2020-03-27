package com.daimler.mbprotokit.dto.car.auxheat

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Auxheat(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * AuxheatActive
     * auxiliary heating / ventilation active / inactive
     * false.. Off
     * true.. On
     */
    val activeState: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.AUXHEAT_ACTIVE
    )

    /**
     * Auxiliary heating remaining runtime:
     * 0..60 minutes
     */
    val runtime: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.AUXHEAT_RUNTIME
    )

    /**
     * Current auxiliary heating state, e.g. heating or ventilating
     * 0: "SNA/Signal Default"
     * 1: "Heizen" "normal mode / NORM"
     * 2: "Lüften" "normal mode /NORM"
     * 3:"Heizen" "Auxiliary heating /AUX"
     * 4:"Nachlauf - Heizen" "5 min post running / POST_RUN"
     * 5:"Nachlauf - Lüften" "5 min post running / POST_RUN"
     * 6:"Heizen" "Pseudo Auxiliary heating / PSEUDO_AUX"
     */
    val state: Pair<AuxheatState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.AUXHEAT_STATUS,
        AuxheatState.map()
    )

    /**
     * Auxiliary heating pre-selection timer 1 in minutes from begin of day
     * 0..1439
     */
    val time1: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.AUXHEAT_TIME_1
    )

    /**
     * Auxiliary heating pre-selection timer 2 in minutes from begin of day
     * 0..1439
     */
    val time2: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.AUXHEAT_TIME_2
    )

    /**
     * Auxiliary heating pre-selection timer 3 in minutes from begin of day
     * 0..1439
     */
    val time3: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.AUXHEAT_TIME_3
    )

    /**
     * One out of NONE, TIME1, TIME2, TIME3, UNKNOWN
     */
    val timeSelection: Pair<AuxheatTimeSelectionState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.AUXHEAT_TIME_SELECTION,
        AuxheatTimeSelectionState.map()
    )

    /**
     * Warning for e.g. tank reserve reached
     */
    val warnings: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.AUXHEAT_WARNINGS
    )
}
