package com.daimler.mbmobilesdk.push.process

internal interface PushProcess {

    var state: PushState

    fun confirm()

    fun cancel()
}