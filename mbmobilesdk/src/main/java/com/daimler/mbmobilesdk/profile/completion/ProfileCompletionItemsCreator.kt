package com.daimler.mbmobilesdk.profile.completion

import android.content.Context
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.profile.creator.DynamicProfileItemsCreator
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileFieldActionHandler
import com.daimler.mbmobilesdk.profile.fields.ProfileFieldResolutionStrategy
import com.daimler.mbmobilesdk.profile.fields.ProfileViewable
import com.daimler.mbmobilesdk.profile.format.ProfileFieldValueFormatter
import com.daimler.mbingresskit.common.User

internal class ProfileCompletionItemsCreator(
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
        return baseTextView(
            context.getString(R.string.profile_country),
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.LanguageCode): ProfileViewable {
        return baseTextView(
            context.getString(R.string.profile_language),
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }
}