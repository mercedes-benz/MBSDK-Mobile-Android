package com.daimler.mbcarkit.persistance

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbcarkit.business.SelectedVehicleStorage
import com.daimler.mbcommonkit.extensions.getEncryptedSharedPreferences

internal class SelectedVehiclePreferencesStorage(
    private val context: Context
) : SelectedVehicleStorage {

    private val encryptedPrefs = encryptedPref()

    override fun selectedFinOrVin(): String? {
        return encryptedPrefs.getString(KEY_SELECTED_VEHICLE, Defaults.FIN_OR_VIN).takeIf { !it.isNullOrBlank() }
    }

    override fun selectFinOrVin(finOrVin: String) {
        encryptedPrefs.edit().putString(KEY_SELECTED_VEHICLE, finOrVin).apply()
    }

    override fun clear() {
        encryptedPrefs.edit().putString(KEY_SELECTED_VEHICLE, Defaults.FIN_OR_VIN).apply()
    }

    private fun encryptedPref(): SharedPreferences =
        context.getEncryptedSharedPreferences(ALIAS, SETTINGS_NAME, keepEncryptedValuesInMemory = true)

    private object Defaults {
        const val FIN_OR_VIN = ""
    }

    private companion object {
        private const val SETTINGS_NAME = "mb.carkit.vehicle.preferences.internal.storage"
        private const val ALIAS = "com.daimler.mb.carkit.vehicle.preferences.internal.storage.alias"

        private const val KEY_SELECTED_VEHICLE = "mb.vehicle.storage.selected.vehicle"
    }
}
