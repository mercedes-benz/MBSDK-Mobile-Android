package com.daimler.mbcarkit.business.model.vehicleusers

data class VehicleUserManagement(

    val finOrVin: String,

    val maxProfiles: Int?,

    val occupiedProfiles: Int?,

    val owner: VehicleAssignedUser?,

    val users: List<VehicleAssignedUser>?,

    val profiles: List<VehicleLocalProfile>?
)
