package com.daimler.mbingresskit.socket.observable

import com.daimler.mbprotokit.dto.user.UserPinUpdate

data class ProfilePinUpdate(
    val sequenceNumber: Int
)

internal fun UserPinUpdate.toProfilePinUpdate() =
    ProfilePinUpdate(sequenceNumber)
