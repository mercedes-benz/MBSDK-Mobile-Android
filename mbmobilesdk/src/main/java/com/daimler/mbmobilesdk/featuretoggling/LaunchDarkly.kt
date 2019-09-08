package com.daimler.mbmobilesdk.featuretoggling

import com.daimler.mbmobilesdk.BuildConfig

internal const val LD_ENVIRONMENT_NAME_TEST = "TEST"
internal const val LD_ENVIRONMENT_NAME_PROD = "PROD"

internal val LD_ENVIRONMENT_TEST = FeatureToggleEnvironment(
    LD_ENVIRONMENT_NAME_TEST, BuildConfig.LD_API_KEY_DEV
)

internal val LD_ENVIRONMENT_PROD = FeatureToggleEnvironment(
    LD_ENVIRONMENT_NAME_PROD, BuildConfig.LD_API_KEY_PROD
)