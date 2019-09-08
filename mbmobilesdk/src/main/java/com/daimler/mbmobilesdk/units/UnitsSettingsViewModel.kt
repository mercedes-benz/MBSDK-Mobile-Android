package com.daimler.mbmobilesdk.units

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.logic.UserTask
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.UnitPreferences
import com.daimler.mbuikit.components.viewmodels.MBBaseToolbarViewModel
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.utils.extensions.getString
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

internal class UnitsSettingsViewModel(app: Application) : MBBaseToolbarViewModel(app) {

    inner class UnitPreferenceGroup<T>(
        val title: CharSequence,
        val values: Map<T, CharSequence>,
        val selectedIndex: MutableLiveData<Int>,
        private val onSelectedIndexChanged: (group: UnitPreferenceGroup<T>, oldIndex: Int, newIndex: Int) -> Unit
    ) {

        val displayValues = values.values.toList()

        fun onSelectedIndexChanged(oldIndex: Int, newIndex: Int) {
            selectedIndex.value = newIndex
            onSelectedIndexChanged(this, oldIndex, newIndex)
        }
    }

    private val unitPreferenceGroupsMap = mutableMapOf<Class<out Enum<*>>, UnitPreferenceGroup<*>>()
    val unitPreferenceGroups = mutableLiveDataOf<List<UnitPreferenceGroup<*>>>(emptyList())
    val progressVisible = mutableLiveDataOf(true)
    val errorText = MutableLiveEvent<CharSequence>()

    init {
        MBIngressKit.refreshTokenIfRequired()
            .onComplete {
                UserTask().fetchUser(true)
                    .onComplete {
                        unitPreferenceGroups.value = mapUnitPreferences(it.user.unitPreferences, app.resources)
                    }
                    .onFailure {
                        errorText.sendEvent(getString(R.string.general_error_msg))
                    }
                    .onAlways { _, _, _ -> progressVisible.value = false }
            }
            .onFailure {
                MBLoggerKit.e("Error while refreshing token", throwable = it)
                progressVisible.value = false
                errorText.sendEvent(getString(R.string.snack_bar_error))
            }
    }

    private fun mapUnitPreferences(
        unitPreferences: UnitPreferences,
        resources: Resources
    ): List<UnitPreferenceGroup<*>> {
        unitPreferenceGroupsMap.clear()

        unitPreferenceGroupsMap[UnitPreferences.SpeedDistanceUnits::class.java] = mapUnitPreferences(
            getString(R.string.setting_units_speed_distance_header),
            unitPreferences.speedDistance,
            UnitResourcesMapper.getSpeedDistanceUnitsResources(resources)
        )

        unitPreferenceGroupsMap[UnitPreferences.ConsumptionCoUnits::class.java] = mapUnitPreferences(
            getString(R.string.setting_units_consumption_header) + " " +
                getString(R.string.setting_units_consumption_co),
            unitPreferences.consumptionCo,
            UnitResourcesMapper.getConsumptionCoUnitsResources(resources)
        )

        unitPreferenceGroupsMap[UnitPreferences.ConsumptionEvUnits::class.java] = mapUnitPreferences(
            getString(R.string.setting_units_consumption_header) + " " +
                getString(R.string.setting_units_consumption_ev),
            unitPreferences.consumptionEv,
            UnitResourcesMapper.getConsumptionEvUnitsResources(resources)
        )

        unitPreferenceGroupsMap[UnitPreferences.ConsumptionGasUnits::class.java] = mapUnitPreferences(
            getString(R.string.setting_units_consumption_header) + " " +
                getString(R.string.setting_units_consumption_gas),
            unitPreferences.consumptionGas,
            UnitResourcesMapper.getConsumptionGasUnitsResources(resources)
        )

        unitPreferenceGroupsMap[UnitPreferences.TirePressureUnits::class.java] = mapUnitPreferences(
            getString(R.string.setting_units_tire_pressure_header),
            unitPreferences.tirePressure,
            UnitResourcesMapper.getTirePressureUnitsResources(resources)
        )

        unitPreferenceGroupsMap[UnitPreferences.TemperatureUnits::class.java] = mapUnitPreferences(
            getString(R.string.setting_units_temperature_header),
            unitPreferences.temperature,
            UnitResourcesMapper.getTemperatureUnitsResources(resources)
        )

        unitPreferenceGroupsMap[UnitPreferences.ClockHoursUnits::class.java] = mapUnitPreferences(
            getString(R.string.setting_units_time_format_header),
            unitPreferences.clockHours,
            UnitResourcesMapper.getTimeFormatUnitsResources(resources)
        )
        return unitPreferenceGroupsMap.values.toList()
    }

    private fun <T> mapUnitPreferences(
        header: String,
        selectedValue: T,
        resources: Map<T, CharSequence>
    ): UnitPreferenceGroup<T> {
        var resetting = false
        return UnitPreferenceGroup(
            header,
            resources,
            MutableLiveData<Int>().apply {
                value = resources.values.indexOf(resources[selectedValue])
            }
        ) { group, oldIndex, newIndex ->
            if (resetting) return@UnitPreferenceGroup

            progressVisible.value = true

            MBIngressKit.refreshTokenIfRequired()
                .onComplete { token ->
                    MBIngressKit.userService()
                        .updateUnitPreferences(token.jwtToken.plainToken, getUnitPreferences())
                        .onComplete { MBLoggerKit.d("Updated unit preferences.") }
                        .onFailure {
                            MBLoggerKit.e("Error while fetching user $it")
                            resetting = true
                            group.selectedIndex.value = oldIndex
                            errorText.sendEvent(getString(R.string.general_error_msg))
                            resetting = false
                        }
                        .onAlways { _, _, _ -> progressVisible.value = false }
                }
                .onFailure {
                    MBLoggerKit.e("Error while refreshing token", throwable = it)
                    progressVisible.value = false
                    errorText.sendEvent(getString(R.string.snack_bar_error))
                }
        }
    }

    private fun getUnitPreferences(): UnitPreferences {
        return UnitPreferences(
            getUnitPreferenceValueForType(),
            getUnitPreferenceValueForType(),
            getUnitPreferenceValueForType(),
            getUnitPreferenceValueForType(),
            getUnitPreferenceValueForType(),
            getUnitPreferenceValueForType(),
            getUnitPreferenceValueForType()
        )
    }

    @Suppress("UNCHECKED_CAST")
    private inline fun <reified T : Enum<*>> getUnitPreferenceValueForType(): T {
        val group: UnitPreferenceGroup<T> = unitPreferenceGroupsMap[T::class.java] as UnitPreferenceGroup<T>
        val selectedDisplayValue = group.displayValues[group.selectedIndex.value ?: 0]
        return group.values.entries.first { it.value == selectedDisplayValue }.key
    }
}