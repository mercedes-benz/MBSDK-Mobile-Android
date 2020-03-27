package com.daimler.mbcarkit.persistance

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbcarkit.business.SelectedVehicleStorage
import com.daimler.mbcommonkit.extensions.getEncryptedSharedPreferences
import com.daimler.mbcommonkit.preferences.SharedUserIdNotSetException
import com.daimler.mbcommonkit.security.Crypto
import com.daimler.mbcommonkit.utils.getPackagesWithSharedUserId

internal class SharedSelectedVehiclePreferencesStorage(
    private val context: Context,
    private val sharedUserId: String
) : SelectedVehicleStorage {

    private val crypto = Crypto(true)

    init {
        checkSharedUserIdConfigured()
        initializePreferences()
    }

    override fun selectedFinOrVin(): String? {
        return encryptedPref().getString(KEY_SELECTED_VEHICLE, Defaults.FIN_OR_VIN)?.takeIf { !it.isBlank() }
    }

    override fun selectFinOrVin(finOrVin: String) {
        encryptedPrefs().forEach { it.edit().putString(KEY_SELECTED_VEHICLE, finOrVin).apply() }
    }

    override fun clear() {
        encryptedPrefs().forEach { it.edit().putString(KEY_SELECTED_VEHICLE, Defaults.FIN_OR_VIN).apply() }
    }

    private fun encryptedPrefs(): List<SharedPreferences> {
        val packages = getPackagesWithSharedUserId(context, sharedUserId)
        return packages.map {
            context.createPackageContext(it.packageName, 0)
                .getEncryptedSharedPreferences(ALIAS, SETTINGS_NAME, Context.MODE_MULTI_PROCESS, crypto)
        }
    }

    private fun encryptedPref(): SharedPreferences =
        context.getEncryptedSharedPreferences(ALIAS, SETTINGS_NAME, Context.MODE_MULTI_PROCESS, crypto)

    private fun checkSharedUserIdConfigured() {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        if (packageInfo.sharedUserId != sharedUserId) throw SharedUserIdNotSetException(sharedUserId)
    }

    private fun initializePreferences() {
        val preferences = encryptedPref()
        if (!preferences.getBoolean(KEY_INITIALIZED, false)) {
            encryptedPrefs().find {
                it.getBoolean(KEY_INITIALIZED, false)
            }?.let {
                preferences.edit().putString(
                    KEY_SELECTED_VEHICLE,
                    it.getString(KEY_SELECTED_VEHICLE, Defaults.FIN_OR_VIN)
                ).apply()
            }
        }
        preferences.edit().putBoolean(KEY_INITIALIZED, true).apply()
    }

    private object Defaults {
        const val FIN_OR_VIN = ""
    }

    private companion object {
        private const val SETTINGS_NAME = "mb.carkit.vehicle.preferences.storage"
        private const val ALIAS = "com.daimler.mb.carkit.vehicle.preferences.storage"

        private const val KEY_SELECTED_VEHICLE = "mb.vehicle.storage.selected.vehicle"
        private const val KEY_INITIALIZED = "mb.vehicle.storage.selected.initialized"
    }
}
