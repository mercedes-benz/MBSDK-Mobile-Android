package com.daimler.mbmobilesdk.implementation

import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbmobilesdk.configuration.EndpointUrlProvider
import com.daimler.mbmobilesdk.configuration.Region
import com.daimler.mbmobilesdk.configuration.Stage

internal class StageBasedEndpointUrlProvider(
    private val region: Region,
    private val stage: Stage
) : EndpointUrlProvider {

    override val isProductiveEnvironment: Boolean = stage == Stage.PROD

    override fun authUrl(authenticationType: AuthenticationType): String = when (authenticationType) {
        AuthenticationType.KEYCLOAK -> "https://keycloak.risingstars${stage.stageSuffix}${region.regionSuffix}.daimler.com"
        AuthenticationType.CIAM -> CiamAuthEndpoint.byStageAndRegion(stage, region).url
    }

    override val bffUrl: String =
        "https://bff-${stage.stageName}.risingstars${stage.stageSuffix}${region.regionSuffix}.daimler.com"

    override val socketUrl: String =
        "wss://websocket-${stage.stageName}.risingstars${stage.stageSuffix}${region.regionSuffix}.daimler.com/ws"

    private enum class CiamAuthEndpoint(
        private val stage: Stage,
        private val region: Region,
        val url: String
    ) {
        PROD_EMEA(Stage.PROD, Region.ECE, "https://id.mercedes-benz.com"),
        NONPROD_EMEA(Stage.INT, Region.ECE, "https://id-int.mercedes-benz.com"),
        PROD_CN(Stage.PROD, Region.CN, "https://ciam-1.mercedes-benz.com.cn"),
        NONPROD_CN(Stage.INT, Region.CN, "https://ciam-int-1.mercedes-benz.com.cn"),

        // TODO: Mock enviroment with CIAM. Currently not working
        MOCK_EMEA(Stage.MOCK, Region.ECE, "TODO"),
        MOCK_CN(Stage.MOCK, Region.CN, "TODO");

        companion object {
            fun byStageAndRegion(stage: Stage, region: Region) = values().first {
                it.stage == stage && it.region == region
            }
        }
    }
}
