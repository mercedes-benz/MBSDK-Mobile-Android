package com.daimler.mbcarkit.business.model.vehicle

data class TemperaturePoint(
    /**
     * Temperature in °C
     */
    val temperatureInCelsius: Double,

    /**
     * Temperature zone in car, e.g. 'front left' or 'rear middle'
     */
    val zone: TemperatureZone
)
