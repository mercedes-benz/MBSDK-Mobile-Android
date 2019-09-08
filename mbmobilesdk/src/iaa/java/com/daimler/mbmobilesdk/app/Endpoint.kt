package com.daimler.mbmobilesdk.app

import com.google.gson.annotations.SerializedName

internal data class Endpoint(
    @SerializedName("region") val region: Region,
    @SerializedName("stage") val stage: Stage
) {

    fun jwtUrl() =
        "https://auth-${stage.stageName}.risingstars${stage.stageSuffix}.daimler.com"

    fun authUrl() =
        "https://keycloak.risingstars${stage.stageSuffix}${region.regionSuffix}.daimler.com"

    fun bffUrl() =
        "https://bff-${stage.stageName}.risingstars${stage.stageSuffix}${region.regionSuffix}.daimler.com"

    fun socketUrl() =
        "wss://websocket-${stage.stageName}.risingstars${stage.stageSuffix}${region.regionSuffix}.daimler.com/ws"

    fun supportUrl(): String {
        return when (stage) {
            Stage.MOCK, Stage.INT -> "https://rs-support-int.corpinter.net/"
            Stage.PROD -> "https://rs-support.corpinter.net/"
        }
    }

    fun isAvailable() = AVAILABLE_STAGES[region]?.contains(stage) == true

    private companion object {
        val AVAILABLE_STAGES = mapOf(
            Region.ECE to listOf(Stage.PROD, Stage.MOCK)
        )
    }
}
