package com.daimler.mbmobilesdk.profile.layout

import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileViewable

interface LayoutRuleResolver {

    fun <S : ProfileField, E : ProfileField> resolveRule(
        rule: ProfileFieldLayoutRule.ToSideOf<S, E>,
        viewable: ProfileViewable,
        root: LayoutGroup<*>
    )

    fun <T : ProfileField> resolveRule(
        rule: ProfileFieldLayoutRule.Position<T>,
        viewable: ProfileViewable,
        root: LayoutGroup<*>
    )
}