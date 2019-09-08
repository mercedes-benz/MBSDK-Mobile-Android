package com.daimler.mbmobilesdk.push.process

internal interface PushState {

    val cancelable: Boolean

    fun cancel(process: PushProcess, actionHandler: PushStateActionHandler)

    fun confirm(process: PushProcess, actionHandler: PushStateActionHandler)
}