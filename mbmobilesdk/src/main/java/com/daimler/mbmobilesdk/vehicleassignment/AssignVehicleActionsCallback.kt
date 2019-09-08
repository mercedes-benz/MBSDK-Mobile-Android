package com.daimler.mbmobilesdk.vehicleassignment

internal interface AssignVehicleActionsCallback {

    fun onShowQrCode()

    fun onAssignWithVin(vin: String?)

    fun onShowHelp()

    fun onInfoCall()

    fun onSearchRetailer()

    fun onCancel()
}