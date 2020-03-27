package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.TcuSoftwareVersion
import com.daimler.mbcarkit.network.model.ApiTcuSoftwareVersion.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiTcuSoftwareVersion {
    @SerializedName("30.4") V_30_4,
    @SerializedName("24.6") V_24_6,
    @SerializedName("31.5") V_31_5,
    @SerializedName("25.1") V_25_1,
    @SerializedName("25.4") V_25_4,
    @SerializedName("24.7") V_24_7,
    @SerializedName("43.1") V_43_1,
    @SerializedName("44.2") V_44_2,
    @SerializedName("45.0") V_45_0,
    @SerializedName("46.0") V_46_0,
    @SerializedName("211.1") V_211_1,
    @SerializedName("216.4") V_216_4,
    @SerializedName("216.5") V_216_5,
    @SerializedName("218.2") V_218_2,
    @SerializedName("218.4") V_218_4,
    @SerializedName("218.5") V_218_5,
    @SerializedName("218.6") V_218_6,
    @SerializedName("220") V_220,
    @SerializedName("318") V_318,
    @SerializedName("218.5n") V_218_5n,
    @SerializedName("218.5p") V_218_5p,
    @SerializedName("319.3") V_319_3,
    @SerializedName("321") V_321,
    @SerializedName("322.6") V_322_6,
    @SerializedName("322.8") V_322_8,
    @SerializedName("322.9") V_322_9,
    @SerializedName("333.1") V_333_1,
    @SerializedName("333.5") V_333_5,
    @SerializedName("334.2") V_334_2,
    @SerializedName("355.0") V_355_0,
    @SerializedName("356.0") V_356_0,
    @SerializedName("408.3") V_408_3,
    @SerializedName("409.X") V_409_X,
    @SerializedName("50x.x") V_50x_x;

    companion object {
        val map: Map<String, TcuSoftwareVersion> = TcuSoftwareVersion.values().associateBy(TcuSoftwareVersion::name)
    }
}

internal fun ApiTcuSoftwareVersion?.toTcuSoftwareVersion(): TcuSoftwareVersion? =
    this?.let { map[name] }
