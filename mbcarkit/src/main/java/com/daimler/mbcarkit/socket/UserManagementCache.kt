package com.daimler.mbcarkit.socket

import com.daimler.mbcarkit.business.model.vehicleusers.VehicleUserManagement

interface UserManagementCache {

    fun updateUserManagement(userManagement: VehicleUserManagement)

    fun loadUserManagement(finOrVin: String): VehicleUserManagement?

    fun deleteUserManagement(finOrVin: String)

    fun upgradeTemporaryUser(authorizationId: String)

    fun deleteAll()
}
