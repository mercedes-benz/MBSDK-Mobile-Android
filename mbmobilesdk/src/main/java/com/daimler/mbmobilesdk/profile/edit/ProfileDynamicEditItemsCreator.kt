package com.daimler.mbmobilesdk.profile.edit

import android.content.Context
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.profile.creator.DynamicProfileItemsCreator
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileFieldActionHandler
import com.daimler.mbmobilesdk.profile.fields.ProfileFieldResolutionStrategy
import com.daimler.mbmobilesdk.profile.fields.ProfileViewable
import com.daimler.mbmobilesdk.profile.format.ProfileFieldValueFormatter
import com.daimler.mbingresskit.common.User

internal class ProfileDynamicEditItemsCreator(
    context: Context,
    resolver: ProfileFieldActionHandler,
    user: User?,
    resolutionStrategy: ProfileFieldResolutionStrategy,
    formatter: ProfileFieldValueFormatter
) : DynamicProfileItemsCreator(
    context,
    resolver,
    user,
    resolutionStrategy,
    formatter,
    true
) {

    override fun createViewable(profileField: ProfileField.AddressCountryCode): ProfileViewable {
        val selectedItem = user?.address?.countryCode
            ?: profileField.values.defaultSelectableValueKey
        val value = selectedItem?.let { selected ->
            profileField.values.selectableValues.firstOrNull { it.key == selected }?.description
        }.orProfileDefault()
        return clickableEditTextView(context.getString(R.string.profile_country), value, profileField.usage.isMandatory).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }
}