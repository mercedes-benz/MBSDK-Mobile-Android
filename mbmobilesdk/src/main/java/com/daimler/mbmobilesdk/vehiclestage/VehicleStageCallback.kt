package com.daimler.mbmobilesdk.vehiclestage

internal interface VehicleStageCallback {

    fun onAssignVehicle()

    fun onAssignmentCancelled()

    fun onActivateServices()

    fun onServiceActivationCancelled()
}