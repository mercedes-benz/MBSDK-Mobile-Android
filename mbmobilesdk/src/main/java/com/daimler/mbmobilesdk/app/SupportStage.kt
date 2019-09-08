package com.daimler.mbmobilesdk.app

internal enum class SupportStage(val identifier: String) {
    MOCK("mock"),
    TEST("test"),
    INT("int"),
    PROD("prod")
}