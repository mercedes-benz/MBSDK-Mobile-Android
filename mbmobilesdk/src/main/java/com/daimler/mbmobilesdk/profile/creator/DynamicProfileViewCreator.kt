package com.daimler.mbmobilesdk.profile.creator

import android.content.Context
import android.view.View
import com.daimler.mbmobilesdk.profile.fields.*
import com.daimler.mbmobilesdk.profile.format.ProfileFieldValueFormatter
import com.daimler.mbmobilesdk.profile.views.ProfileCheckBoxView
import com.daimler.mbmobilesdk.profile.views.ProfileEditTextView
import com.daimler.mbmobilesdk.profile.views.ProfileSelectableValuesView
import com.daimler.mbmobilesdk.profile.views.ProfileSwitchView
import com.daimler.mbmobilesdk.profile.views.ProfileValueTextView
import com.daimler.mbingresskit.common.User

internal open class DynamicProfileViewCreator(
    context: Context,
    resolver: ProfileFieldActionHandler,
    user: User?,
    resolutionStrategy: ProfileFieldResolutionStrategy,
    formatter: ProfileFieldValueFormatter,
    allowLiveTextEdit: Boolean
) : BaseDynamicProfileCreator<ProfileView>(
    context,
    resolver,
    user,
    resolutionStrategy,
    formatter,
    EmptyView,
    allowLiveTextEdit
) {

    override fun baseTextView(title: String?, content: String?, active: Boolean): ProfileView {
        return ProfileValueTextView.Builder()
            .withContent(content.orProfileDefault())
            .withTitle(title)
            .withActive(active)
            .build(context)
    }

    override fun baseEditTextView(hint: String?, content: String?, inputType: Int, isMandatory: Boolean): ProfileView {
        return ProfileEditTextView.Builder()
            .withContent(content)
            .withHasNext(true)
            .withHint(hint)
            .withMode(ProfileEditTextView.Mode.TEXT)
            .withInputType(inputType)
            .build(context)
    }

    override fun clickableEditTextView(hint: String?, content: String?, isMandatory: Boolean): ProfileView {
        return ProfileEditTextView.Builder()
            .withContent(content)
            .withHasNext(true)
            .withHint(hint)
            .withMode(ProfileEditTextView.Mode.CLICK)
            .build(context)
    }

    override fun baseSelectableValuesView(items: Map<String, String>, keySelection: String?, emptyValue: String?, hint: String?, notifyKeys: Boolean): ProfileView {
        return ProfileSelectableValuesView.Builder()
            .withEmptyItem(emptyValue)
            .withHint(hint)
            .withItems(items)
            .withKeySelection(keySelection)
            .withNotifyKeys(notifyKeys)
            .build(context)
    }

    override fun baseCheckBoxView(title: String, checked: Boolean): ProfileView {
        return ProfileCheckBoxView.Builder()
            .withChecked(checked)
            .withTitle(title)
            .build(context)
    }

    override fun baseSwitchView(title: String, checked: Boolean): ProfileView {
        return ProfileSwitchView.Builder()
            .withChecked(checked)
            .withTitle(title)
            .build(context)
    }

    private object EmptyView : ProfileView {

        override fun view(): View? = null
        override fun applyCallback(callback: ProfileValueCallback) = Unit
        override fun applyAssociatedField(profileField: ProfileField) = Unit
        override fun associatedField(): ProfileField? = null
        override fun applyValue(value: String?) = Unit
        override fun currentValue(): String? = null
    }
}