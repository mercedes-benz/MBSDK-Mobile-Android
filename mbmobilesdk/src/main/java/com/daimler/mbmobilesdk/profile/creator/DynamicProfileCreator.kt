package com.daimler.mbmobilesdk.profile.creator

import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileViewable

interface DynamicProfileCreator<T : ProfileViewable> {

    fun createViewables(fields: List<ProfileField>): List<T>
    fun destroy()
}