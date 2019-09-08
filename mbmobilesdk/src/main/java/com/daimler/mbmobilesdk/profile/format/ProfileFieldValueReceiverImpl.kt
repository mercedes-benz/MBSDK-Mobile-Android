package com.daimler.mbmobilesdk.profile.format

import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbingresskit.common.ProfileSelectableValues

internal class ProfileFieldValueReceiverImpl(
    private val profileFields: List<ProfileField>
) : ProfileFieldValueReceiver {

    override fun getSalutationForKey(key: String?): String? {
        val salutationField: ProfileField.Salutation? =
            profileFields.findField() ?: profileFields.findFromNameField()
        return salutationField?.values?.getForKey(key)
    }

    override fun getTitleForKey(key: String?): String? {
        val titleField: ProfileField.Title? =
            profileFields.findField() ?: profileFields.findFromNameField()
        return titleField?.values?.getForKey(key)
    }

    private fun ProfileSelectableValues.getForKey(key: String?): String? {
        return selectableValues.find { it.key == key }?.description
    }

    private inline fun <reified T : ProfileField> List<ProfileField>.findFromNameField(): T? {
        return find { it is ProfileField.Name }?.combinedFields?.findField()
    }

    private inline fun <reified T : ProfileField> List<ProfileField>.findField(): T? {
        return find { it is T } as? T
    }
}