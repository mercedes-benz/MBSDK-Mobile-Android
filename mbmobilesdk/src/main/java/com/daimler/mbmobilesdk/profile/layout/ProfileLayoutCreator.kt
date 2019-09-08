package com.daimler.mbmobilesdk.profile.layout

import com.daimler.mbmobilesdk.profile.fields.ProfileViewable

interface ProfileLayoutCreator<T : ProfileViewable, R> {

    fun createLayoutStructure(items: List<T>): R
}