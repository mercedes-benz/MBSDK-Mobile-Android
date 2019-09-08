package com.daimler.mbmobilesdk.profile.completion

import com.daimler.mbingresskit.common.User

internal interface ProfileCompletionCallback {

    fun onUserUpdated(user: User)

    fun onUpdateProfileTitle(title: String)
}