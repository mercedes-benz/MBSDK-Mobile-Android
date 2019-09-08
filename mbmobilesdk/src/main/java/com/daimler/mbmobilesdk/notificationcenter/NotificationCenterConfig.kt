package com.daimler.mbmobilesdk.notificationcenter

class NotificationCenterConfig(private val stage: Stage) {

    fun url(): String {
        return "https://services.me-${stage.value}.mercedes-benz.com/reach-me/api/msg/"
    }

    enum class Stage(internal val value: String) {
        INT("int")
    }
}