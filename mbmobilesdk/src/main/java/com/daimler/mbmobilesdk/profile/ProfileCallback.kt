package com.daimler.mbmobilesdk.profile

import com.daimler.mbmobilesdk.login.ProfileLogoutState
import com.daimler.mbingresskit.common.User

internal interface ProfileCallback {

    fun onUserChanged(user: User)

    fun onProfilePictureUpdated(pictureBytes: ByteArray)

    fun onLogoutStateChanged(state: ProfileLogoutState)
}