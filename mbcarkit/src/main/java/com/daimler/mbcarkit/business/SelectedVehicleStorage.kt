package com.daimler.mbcarkit.business

/**
 * Storage for the FIN/ VIN of the currently selected vehicle.
 */
interface SelectedVehicleStorage {

    /**
     * Returns the currently selected FIN/ VIN or null if none was selected.
     */
    fun selectedFinOrVin(): String?

    /**
     * Selects the given FIN/ VIN.
     */
    fun selectFinOrVin(finOrVin: String)

    /**
     * Clears the selection.
     */
    fun clear()
}
