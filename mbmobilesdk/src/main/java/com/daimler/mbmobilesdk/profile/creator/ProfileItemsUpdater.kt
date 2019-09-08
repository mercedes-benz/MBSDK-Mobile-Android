package com.daimler.mbmobilesdk.profile.creator

import com.daimler.mbmobilesdk.profile.fields.ProfileViewable
import com.daimler.mbmobilesdk.profile.format.ProfileFieldValueFormatter

internal class ProfileItemsUpdater(
    private val formatter: ProfileFieldValueFormatter
) : ProfileViewableUpdater {

    override fun updateViewableValues(viewables: List<ProfileViewable>) {
        viewables.forEach { viewable ->
            viewable.associatedField()?.let {
                viewable.applyValue(it.getFormattedValue(formatter))
            }
        }
    }
}