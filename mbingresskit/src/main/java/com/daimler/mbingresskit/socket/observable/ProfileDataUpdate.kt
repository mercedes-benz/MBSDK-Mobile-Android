package com.daimler.mbingresskit.socket.observable

import com.daimler.mbprotokit.dto.user.UserDataUpdate

data class ProfileDataUpdate(
    val sequenceNumber: Int
)

internal fun UserDataUpdate.toProfileDataUpdate() =
    ProfileDataUpdate(sequenceNumber)
