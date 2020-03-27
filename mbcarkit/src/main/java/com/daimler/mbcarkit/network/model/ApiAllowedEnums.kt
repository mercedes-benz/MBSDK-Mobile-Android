package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.command.capabilities.AllowedEnums
import com.daimler.mbcarkit.network.model.ApiAllowedEnums.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiAllowedEnums(val allowedEnums: AllowedEnums? = null) {

    @SerializedName("NO_SELECTION") NO_SELECTION,
    @SerializedName("TIME_1") TIME_1,
    @SerializedName("TIME_2") TIME_2,
    @SerializedName("TIME_3") TIME_3,
    @SerializedName("DEFAULT") DEFAULT,
    @SerializedName("INSTANT") INSTANT,
    @SerializedName("LOW_PRICE") LOW_PRICE,
    @SerializedName("NORMAL_PRICE") NORMAL_PRICE,
    @SerializedName("HIGH_PRICE") HIGH_PRICE,
    @SerializedName("DEFAULT_CHARGEPROGRAM") DEFAULT_CHARGEPROGRAM(AllowedEnums.DEFAULT_CHARGE_PROGRAM),
    @SerializedName("INSTANT_CHARGEPROGRAM") INSTANT_CHARGEPROGRAM(AllowedEnums.INSTANT_CHARGE_PROGRAM),
    @SerializedName("HOME_CHARGEPROGRAM") HOME_CHARGEPROGRAM(AllowedEnums.HOME_CHARGE_PROGRAM),
    @SerializedName("WORK_CHARGEPROGRAM") WORK_CHARGEPROGRAM(AllowedEnums.WORK_CHARGE_PROGRAM),
    @SerializedName("TRUNK") TRUNK,
    @SerializedName("FUEL_FLAP") FUEL_FLAP,
    @SerializedName("CHARGE_FLAP") CHARGE_FLAP,
    @SerializedName("CHARGE_COUPLER") CHARGE_COUPLER,
    @SerializedName("HORN_OFF") HORN_OFF,
    @SerializedName("HORN_LOW_VOLUME") HORN_LOW_VOLUME,
    @SerializedName("HORN_HIGH_VOLUME") HORN_HIGH_VOLUME,
    @SerializedName("LIGHT_OFF") LIGHT_OFF,
    @SerializedName("DIPPED_HEAD_LIGHT") DIPPED_HEAD_LIGHT,
    @SerializedName("WARNING_LIGHT") WARNING_LIGHT,
    @SerializedName("LIGHT_ONLY") LIGHT_ONLY,
    @SerializedName("HORN_ONLY") HORN_ONLY,
    @SerializedName("LIGHT_AND_HORN") LIGHT_AND_HORN,
    @SerializedName("PANIC_ALARM") PANIC_ALARM,
    @SerializedName("FRONT_LEFT") FRONT_LEFT,
    @SerializedName("FRONT_RIGHT") FRONT_RIGHT,
    @SerializedName("FRONT_CENTER") FRONT_CENTER,
    @SerializedName("REAR_LEFT") REAR_LEFT,
    @SerializedName("REAR_RIGHT") REAR_RIGHT,
    @SerializedName("REAR_CENTER") REAR_CENTER,
    @SerializedName("REAR_2_LEFT") REAR_2_LEFT,
    @SerializedName("REAR_2_RIGHT") REAR_2_RIGHT,
    @SerializedName("REAR_2_CENTER") REAR_2_CENTER,
    @SerializedName("MONDAY") MONDAY,
    @SerializedName("TUESDAY") TUESDAY,
    @SerializedName("WEDNESDAY") WEDNESDAY,
    @SerializedName("THURSDAY") THURSDAY,
    @SerializedName("FRIDAY") FRIDAY,
    @SerializedName("SATURDAY") SATURDAY,
    @SerializedName("SUNDAY") SUNDAY,
    @SerializedName("IMMEDIATE") IMMEDIATE,
    @SerializedName("DEPARTURE") DEPARTURE,
    @SerializedName("NOW") NOW,
    @SerializedName("DEPARTURE_WEEKLY") DEPARTURE_WEEKLY,
    @SerializedName("DISABLED") DISABLED,
    @SerializedName("SINGLE_DEPARTURE") SINGLE_DEPARTURE,
    @SerializedName("WEEKLY_DEPARTURE") WEEKLY_DEPARTURE;

    companion object {
        val map: Map<String, AllowedEnums> = AllowedEnums.values().associateBy(AllowedEnums::name)
    }
}

internal fun ApiAllowedEnums?.toAllowedEnums(): AllowedEnums =
    this?.let { allowedEnums ?: map[name] } ?: AllowedEnums.UNKNOWN
