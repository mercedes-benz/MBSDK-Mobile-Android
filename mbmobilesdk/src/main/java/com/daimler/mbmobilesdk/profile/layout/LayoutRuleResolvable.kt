package com.daimler.mbmobilesdk.profile.layout

import com.daimler.mbmobilesdk.profile.fields.ProfileViewable

internal interface LayoutRuleResolvable {

    fun resolve(root: LayoutGroup<*>, viewable: ProfileViewable, resolver: LayoutRuleResolver)
}