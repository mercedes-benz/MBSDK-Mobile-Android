package com.daimler.mbmobilesdk.profile.creator

import com.daimler.mbmobilesdk.profile.fields.ProfileViewable

internal interface ProfileViewableUpdater {

    fun updateViewableValues(viewables: List<ProfileViewable>)
}