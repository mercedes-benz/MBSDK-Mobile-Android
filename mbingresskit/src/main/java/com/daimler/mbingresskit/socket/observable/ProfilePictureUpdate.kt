package com.daimler.mbingresskit.socket.observable

import com.daimler.mbprotokit.dto.user.UserPictureUpdate

data class ProfilePictureUpdate(
    val sequenceNumber: Int
)

internal fun UserPictureUpdate.toProfilePictureUpdate() =
    ProfilePictureUpdate(sequenceNumber)
