package com.daimler.mbmobilesdk.app.userswap.id

internal interface UserIdProvider {

    fun activeUserId(): String?

    fun lastKnownUserId(): String?

    fun saveCurrentUserId()
}