package com.daimler.mbmobilesdk.app

enum class Stage(
    internal val stageName: String,
    internal val stageSuffix: String,
    internal val displayName: String
) {
    INT("int", "-int", "INT/ NonProd"),
    PROD("prod", "", "PROD"),
    MOCK("mock", "-mock", "MOCK")
}