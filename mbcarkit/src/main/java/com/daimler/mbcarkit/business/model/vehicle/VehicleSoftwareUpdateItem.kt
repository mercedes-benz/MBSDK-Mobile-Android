package com.daimler.mbcarkit.business.model.vehicle

import java.util.Date

data class VehicleSoftwareUpdateItem(
    /**
     * Localized title of the campaign/software update item
     * example: Update for the communication module
     */
    val title: String,
    /**
     * Localized short description of the campaign/software update item
     * example: This free update contains optimisations for Mercedes me connect.
     */
    val description: String,
    /**
     * issuing timestamp (date and time) of the campaign/software update item
     * example: 2020-11-18T10:00:00.000Z
     */
    val timestamp: Date?,
    /**
     * current status of the update operation
     */
    val status: VehicleSoftwareUpdateStatus?
)
