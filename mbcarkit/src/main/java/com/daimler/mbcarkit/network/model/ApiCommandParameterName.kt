package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.command.capabilities.CommandParameterName
import com.daimler.mbcarkit.network.model.ApiCommandParameterName.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiCommandParameterName {

    @SerializedName("TIME_SELECTION") TIME_SELECTION,
    @SerializedName("CHARGE_PROGRAM") CHARGE_PROGRAM,
    @SerializedName("MAX_SOC") MAX_SOC,
    @SerializedName("WEEKDAY_TARIFF_RATE") WEEKDAY_TARIFF_RATE,
    @SerializedName("WEEKDAY_TARIFF_TIME") WEEKDAY_TARIFF_TIME,
    @SerializedName("AUTO_UNLOCK") AUTO_UNLOCK,
    @SerializedName("LOCATION_BASED_CHARGING") LOCATION_BASED_CHARGING,
    @SerializedName("WEEKLY_PROFILE") WEEKLY_PROFILE,
    @SerializedName("DOORS") DOORS,
    @SerializedName("HORN_REPEAT") HORN_REPEAT,
    @SerializedName("HORN_TYPE") HORN_TYPE,
    @SerializedName("LIGHT_TYPE") LIGHT_TYPE,
    @SerializedName("SIGPOS_DURATION") SIGPOS_DURATION,
    @SerializedName("SIGPOS_TYPE") SIGPOS_TYPE,
    @SerializedName("THRESHOLD") THRESHOLD,
    @SerializedName("ALERT_END_TIME") ALERT_END_TIME,
    @SerializedName("TEMPERATURE_POINTS_TEMPERATURE") TEMPERATURE_POINTS_TEMPERATURE,
    @SerializedName("TEMPERATURE_POINTS_ZONE") TEMPERATURE_POINTS_ZONE,
    @SerializedName("WEEKLY_SET_HU_DAY") WEEKLY_SET_HU_DAY,
    @SerializedName("WEEKLY_SET_HU_TIME") WEEKLY_SET_HU_TIME,
    @SerializedName("DEPARTURE_TIME_MODE") DEPARTURE_TIME_MODE,
    @SerializedName("DEPARTURE_TIME") DEPARTURE_TIME,
    @SerializedName("FRONT_LEFT_SEAT") FRONT_LEFT_SEAT,
    @SerializedName("FRONT_RIGHT_SEAT") FRONT_RIGHT_SEAT,
    @SerializedName("REAR_LEFT_SEAT") REAR_LEFT_SEAT,
    @SerializedName("REAR_RIGHT_SEAT") REAR_RIGHT_SEAT,
    @SerializedName("TYPE") TYPE,
    @SerializedName("PARAM_TIME_1") PARAM_TIME_1,
    @SerializedName("PARAM_TIME_2") PARAM_TIME_2,
    @SerializedName("PARAM_TIME_3") PARAM_TIME_3;

    companion object {
        val map: Map<String, CommandParameterName> = CommandParameterName.values().associateBy(CommandParameterName::name)
    }
}

internal fun ApiCommandParameterName?.toCommandParameterName(): CommandParameterName =
    this?.let { map[name] } ?: CommandParameterName.UNKNOWN
