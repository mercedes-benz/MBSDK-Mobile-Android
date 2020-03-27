package com.daimler.mbnetworkkit.header

internal interface RisHeaderService : HeaderService {

    val applicationName: String
    val applicationVersion: String
    val sdkVersion: String
    val osName: String
    val osVersion: String
    val sessionId: String?
}
