package com.daimler.mbmobilesdk.configuration

private const val STAGE_INT = "int"
private const val STAGE_PROD = "prod"
private const val STAGE_MOCK = "mock"

enum class Stage(
    val stageName: String,
    val stageSuffix: String
) {
    PROD(STAGE_PROD, ""),
    INT(STAGE_INT, "-$STAGE_INT"),
    MOCK(STAGE_MOCK, "-$STAGE_MOCK")
}

internal enum class IngressStage(val identifier: String) {
    INT(STAGE_INT),
    PROD(STAGE_PROD)
}
