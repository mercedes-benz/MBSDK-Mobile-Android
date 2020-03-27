package com.daimler.mbprotokit.dto.car.windows

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Windows(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Window open state of all windows combined
     * 0: windows in intermediate position
     * 1: windows opened
     * 2: windows closed
     */
    val stateOverall: Pair<WindowsOverallStatus?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WINDOW_STATUS_OVERALL,
        WindowsOverallStatus.map()
    )

    /**
     * Window open state front left
     * 0: "window in intermediate position"
     * 1: "window completely opened"
     * 2: "window completely closed"
     * 3: "window airing position"
     * 4: "window intermidiate airing position"
     * 5: "window currently running"
     */
    val stateFrontLeft: Pair<WindowState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WINDOW_STATUS_FRONT_LEFT,
        WindowState.map()
    )

    /**
     * Window open state front right
     * 0: "window in intermediate position"
     * 1: "window completely opened"
     * 2: "window completely closed"
     * 3: "window airing position"
     * 4: "window intermidiate airing position"
     * 5: "window currently running"
     */
    val stateFrontRight: Pair<WindowState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WINDOW_STATUS_FRONT_RIGHT,
        WindowState.map()
    )

    /**
     * Window open state front left
     * 0: "window in intermediate position"
     * 1: "window completely opened"
     * 2: "window completely closed"
     * 3: "window airing position"
     * 4: "window intermidiate airing position"
     * 5: "window currently running"
     */
    val stateRearLeft: Pair<WindowState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WINDOW_STATUS_REAR_LEFT,
        WindowState.map()
    )

    /**
     * Window open state rear right
     * 0: "window in intermediate position"
     * 1: "window completely opened"
     * 2: "window completely closed"
     * 3: "window airing position"
     * 4: "window intermidiate airing position"
     * 5: "window currently running"
     */
    val stateRearRight: Pair<WindowState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WINDOW_STATUS_REAR_RIGHT,
        WindowState.map()
    )

    /**
     * Flipper window rotary latch state opened/closed
     * false: “closed”
     * true: “open”
     */
    val flipWindowStatus: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WINDOW_STATUS_FLIP
    )

    /**
     * Rear passenger compartment side roller blind left state
     * 0: intermediate position
     * 1: completely opened
     * 2: completely closed
     */
    val blindRearState: Pair<WindowBlindState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WINDOW_BLIND_STATUS_REAR,
        WindowBlindState.map()
    )

    /**
     * Rear passenger compartment side roller blind right state
     * 0: intermediate position
     * 1: completely opened
     * 2: completely closed
     */
    val blindRearLeftState: Pair<WindowBlindState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WINDOW_BLIND_STATUS_REAR_LEFT,
        WindowBlindState.map()
    )

    /**
     * Window open state of all windows combined
     * 0: windows in intermediate position
     * 1: windows opened
     * 2: windows closed
     */
    val blindRearRightState: Pair<WindowBlindState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WINDOW_BLIND_STATUS_REAR_RIGHT,
        WindowBlindState.map()
    )
}
