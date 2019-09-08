package com.daimler.mbmobilesdk.app.userswap.id

internal interface UserIdCache {

    var userId: String

    fun clear()
}