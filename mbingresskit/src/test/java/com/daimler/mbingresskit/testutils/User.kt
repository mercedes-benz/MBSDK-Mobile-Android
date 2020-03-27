package com.daimler.mbingresskit.testutils

import com.daimler.mbingresskit.common.AccountIdentifier
import com.daimler.mbingresskit.common.CommunicationPreference
import com.daimler.mbingresskit.common.UnitPreferences
import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.common.UserPinStatus

fun createUser(
    pinStatus: UserPinStatus = UserPinStatus.UNKNOWN
) = User(
    "",
    "",
    "",
    "",
    "",
    "",
    "",
    "",
    "",
    "",
    "",
    "",
    pinStatus,
    null,
    CommunicationPreference.initialState(),
    UnitPreferences.defaultUnitPreferences(),
    AccountIdentifier.UNKNOWN,
    "",
    "",
    "",
    null,
    null,
    false,
    false,
    null
)
