package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.command.capabilities.CommandName
import com.daimler.mbcarkit.network.model.ApiCommandName.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiCommandName {

    @SerializedName("AUXHEAT_CONFIGURE") AUXHEAT_CONFIGURE,
    @SerializedName("AUXHEAT_START") AUXHEAT_START,
    @SerializedName("AUXHEAT_STOP") AUXHEAT_STOP,
    @SerializedName("BATTERY_CHARGE_PROGRAM_CONFIGURE") BATTERY_CHARGE_PROGRAM_CONFIGURE,
    @SerializedName("BATTERY_MAX_SOC_CONFIGURE") BATTERY_MAX_SOC_CONFIGURE,
    @SerializedName("CHARGE_OPT_CONFIGURE") CHARGE_OPT_CONFIGURE,
    @SerializedName("CHARGE_OPT_START") CHARGE_OPT_START,
    @SerializedName("CHARGE_OPT_STOP") CHARGE_OPT_STOP,
    @SerializedName("CHARGE_PROGRAM_CONFIGURE") CHARGE_PROGRAM_CONFIGURE,
    @SerializedName("DOORS_LOCK") DOORS_LOCK,
    @SerializedName("DOORS_UNLOCK") DOORS_UNLOCK,
    @SerializedName("ENGINE_START") ENGINE_START,
    @SerializedName("ENGINE_STOP") ENGINE_STOP,
    @SerializedName("SIGPOS_START") SIGPOS_START,
    @SerializedName("SPEEDALERT_START") SPEEDALERT_START,
    @SerializedName("SPEEDALERT_STOP") SPEEDALERT_STOP,
    @SerializedName("SUNROOF_CLOSE") SUNROOF_CLOSE,
    @SerializedName("SUNROOF_LIFT") SUNROOF_LIFT,
    @SerializedName("SUNROOF_OPEN") SUNROOF_OPEN,
    @SerializedName("TEMPERATURE_CONFIGURE") TEMPERATURE_CONFIGURE,
    @SerializedName("THEFTALARM_CONFIRM_DAMAGEDETECTION") THEFTALARM_CONFIRM_DAMAGE_DETECTION,
    @SerializedName("THEFTALARM_SELECT_DAMAGEDETECTION") THEFTALARM_SELECT_DAMAGE_DETECTION,
    @SerializedName("THEFTALARM_DESELECT_DAMAGEDETECTION") THEFTALARM_DESELECT_DAMAGE_DETECTION,
    @SerializedName("THEFTALARM_SELECT_INTERIOR") THEFTALARM_SELECT_INTERIOR,
    @SerializedName("THEFTALARM_DESELECT_INTERIOR") THEFTALARM_DESELECT_INTERIOR,
    @SerializedName("THEFTALARM_SELECT_TOW") THEFTALARM_SELECT_TOW,
    @SerializedName("THEFTALARM_DESELECT_TOW") THEFTALARM_DESELECT_TOW,
    @SerializedName("THEFTALARM_START") THEFTALARM_START,
    @SerializedName("THEFTALARM_STOP") THEFTALARM_STOP,
    @SerializedName("WEEK_PROFILE_CONFIGURE") WEEK_PROFILE_CONFIGURE,
    @SerializedName("WINDOWS_CLOSE") WINDOWS_CLOSE,
    @SerializedName("WINDOWS_OPEN") WINDOWS_OPEN,
    @SerializedName("WINDOWS_VENTILATE") WINDOWS_VENTILATE,
    @SerializedName("ZEV_PRECONDITION_CONFIGURE") ZEV_PRECONDITION_CONFIGURE,
    @SerializedName("ZEV_PRECONDITION_CONFIGURE_SEATS") ZEV_PRECONDITION_CONFIGURE_SEATS,
    @SerializedName("ZEV_PRECONDITIONING_START") ZEV_PRECONDITIONING_START,
    @SerializedName("ZEV_PRECONDITIONING_STOP") ZEV_PRECONDITIONING_STOP;

    companion object {
        val map: Map<String, CommandName> = CommandName.values().associateBy(CommandName::name)
    }
}

internal fun ApiCommandName?.toCommandName(): CommandName =
    this?.let { map[name] } ?: CommandName.UNKNOWN
