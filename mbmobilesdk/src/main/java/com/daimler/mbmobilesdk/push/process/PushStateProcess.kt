package com.daimler.mbmobilesdk.push.process

internal class PushStateProcess(
    override var state: PushState,
    private val actionHandler: PushStateActionHandler
) : PushProcess {

    override fun confirm() {
        state.confirm(this, actionHandler)
    }

    override fun cancel() {
        state.cancel(this, actionHandler)
    }
}