package com.daimler.mbmobilesdk.profile.creator

import com.daimler.mbmobilesdk.profile.fields.ProfileViewable

interface ProfileViewCreatable {

    fun create(creator: ProfileViewableCreator): ProfileViewable
}