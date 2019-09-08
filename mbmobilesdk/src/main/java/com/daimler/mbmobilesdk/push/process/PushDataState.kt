package com.daimler.mbmobilesdk.push.process

internal sealed class PushDataState : PushState {

    object General : PushDataState() {

        override var cancelable: Boolean = false

        override fun confirm(process: PushProcess, actionHandler: PushStateActionHandler) {
            actionHandler.onGeneralNotification()
            process.state = Handled
        }

        override fun cancel(process: PushProcess, actionHandler: PushStateActionHandler) = Unit
    }

    class Url(private val url: String) : PushDataState() {

        override var cancelable: Boolean = true

        override fun confirm(process: PushProcess, actionHandler: PushStateActionHandler) {
            actionHandler.onShowUrl(url)
            process.state = Handled
        }

        override fun cancel(process: PushProcess, actionHandler: PushStateActionHandler) {
            actionHandler.onPushHandlingCancelled()
            process.state = Cancelled
        }
    }

    class DeepLink(private val deepLink: String) : PushDataState() {

        override var cancelable: Boolean = true

        override fun confirm(process: PushProcess, actionHandler: PushStateActionHandler) {
            actionHandler.onHandleDeepLink(deepLink)
            process.state = Handled
        }

        override fun cancel(process: PushProcess, actionHandler: PushStateActionHandler) {
            actionHandler.onPushHandlingCancelled()
            process.state = Cancelled
        }
    }

    object Handled : PushDataState() {

        override var cancelable: Boolean = false

        override fun confirm(process: PushProcess, actionHandler: PushStateActionHandler) = Unit

        override fun cancel(process: PushProcess, actionHandler: PushStateActionHandler) = Unit
    }

    object Cancelled : PushDataState() {

        override var cancelable: Boolean = false

        override fun confirm(process: PushProcess, actionHandler: PushStateActionHandler) = Unit

        override fun cancel(process: PushProcess, actionHandler: PushStateActionHandler) = Unit
    }
}