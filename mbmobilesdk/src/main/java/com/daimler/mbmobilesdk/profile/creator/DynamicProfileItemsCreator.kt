package com.daimler.mbmobilesdk.profile.creator

import android.content.Context
import android.text.InputType
import com.daimler.mbmobilesdk.profile.fields.*
import com.daimler.mbmobilesdk.profile.format.ProfileFieldValueFormatter
import com.daimler.mbmobilesdk.profile.items.*
import com.daimler.mbingresskit.common.User

internal open class DynamicProfileItemsCreator(
    context: Context,
    resolver: ProfileFieldActionHandler,
    user: User?,
    resolutionStrategy: ProfileFieldResolutionStrategy,
    formatter: ProfileFieldValueFormatter,
    allowLiveTextEdit: Boolean
) : BaseDynamicProfileCreator<ProfileRecyclerViewable>(
    context,
    resolver,
    user,
    resolutionStrategy,
    formatter,
    EmptyViewable,
    allowLiveTextEdit
) {

    override fun baseTextView(title: String?, content: String?, active: Boolean): ProfileRecyclerViewable {
        return ProfileValueTextViewItem(title, content.orProfileDefault())
    }

    override fun baseEditTextView(hint: String?, content: String?, inputType: Int, isMandatory: Boolean): ProfileRecyclerViewable {
        return ProfileEditTextItem(content, hint, true, inputType = inputType, isMandatory = isMandatory)
    }

    override fun clickableEditTextView(hint: String?, content: String?, isMandatory: Boolean): ProfileRecyclerViewable {
        return ProfileEditTextItem(content, hint, true, ProfileEditTextItem.Mode.CLICK, InputType.TYPE_CLASS_TEXT, isMandatory)
    }

    override fun createViewables(fields: List<ProfileField>): List<ProfileRecyclerViewable> {
        val profileViewableList = super.createViewables(fields)
        (profileViewableList.lastOrNull { it is ProfileEditTextItem } as? ProfileEditTextItem)?.hasNext = false
        return profileViewableList
    }

    override fun baseSelectableValuesView(
        items: Map<String, String>,
        keySelection: String?,
        emptyValue: String?,
        hint: String?,
        notifyKeys: Boolean
    ): ProfileRecyclerViewable {
        return ProfileSelectableValuesItem(
            items,
            hint,
            emptyValue,
            keySelection,
            notifyKeys
        )
    }

    override fun baseCheckBoxView(
        title: String,
        checked: Boolean
    ): ProfileRecyclerViewable {
        return ProfileFieldCheckBoxItem(title, checked)
    }

    override fun baseSwitchView(
        title: String,
        checked: Boolean
    ): ProfileRecyclerViewable {
        return ProfileFieldSwitchItem(title, checked)
    }

    private object EmptyViewable : ProfileRecyclerViewable {

        override fun applyCallback(callback: ProfileValueCallback) = Unit
        override fun applyAssociatedField(profileField: ProfileField) = Unit
        override fun associatedField(): ProfileField? = null
        override fun applyValue(value: String?) = Unit
        override fun currentValue(): String? = null
        override fun item(): BaseProfileRecyclerItem? = null
        override fun destroy() = Unit
    }
}