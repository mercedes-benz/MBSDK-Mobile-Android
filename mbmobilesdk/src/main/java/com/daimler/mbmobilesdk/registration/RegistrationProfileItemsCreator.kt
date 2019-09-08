package com.daimler.mbmobilesdk.registration

import android.content.Context
import android.text.InputType
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.profile.creator.DynamicProfileViewCreator
import com.daimler.mbmobilesdk.profile.fields.*
import com.daimler.mbmobilesdk.profile.fields.ProfileView
import com.daimler.mbmobilesdk.profile.format.ProfileFieldValueFormatter
import com.daimler.mbmobilesdk.profile.views.*
import com.daimler.mbmobilesdk.profile.views.ProfileCheckBoxView
import com.daimler.mbmobilesdk.profile.views.ProfileEditTextView
import com.daimler.mbmobilesdk.profile.views.ProfileSwitchView
import com.daimler.mbmobilesdk.utils.extensions.orNullIfBlank
import com.daimler.mbingresskit.common.ProfileFieldUsage
import com.daimler.mbingresskit.common.User

internal class RegistrationProfileItemsCreator(
    context: Context,
    resolver: ProfileFieldActionHandler,
    user: User?,
    resolutionStrategy: ProfileFieldResolutionStrategy,
    formatter: ProfileFieldValueFormatter,
    isMailIdentifier: Boolean
) : DynamicProfileViewCreator(
    context,
    resolver,
    user,
    resolutionStrategy,
    formatter,
    true
) {

    private val internalCreator = if (isMailIdentifier) MailCreator() else MobilePhoneCreator()

    override fun createViewable(profileField: ProfileField.AddressCountryCode): ProfileView {
        return baseTextView(
            context.getString(R.string.profile_country),
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.LanguageCode): ProfileView {
        return baseTextView(
            context.getString(R.string.profile_preferred_language),
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.Email): ProfileView {
        return internalCreator.createMail(profileField)
    }

    override fun createViewable(profileField: ProfileField.MobilePhone): ProfileView {
        return internalCreator.createMobilePhone(profileField)
    }

    override fun createViewable(profileField: ProfileField.Salutation): ProfileViewable {
        return baseSelectableValuesViewOrClickable(
            profileField.values,
            false,
            profileField,
            user?.salutationCode.orNullIfBlank(),
            null,
            null
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.Title): ProfileViewable {
        return baseSelectableValuesViewOrClickable(
            profileField.values,
            false,
            profileField,
            user?.title.orNullIfBlank(),
            profileDefaultString(),
            context.getString(R.string.profile_name_title)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun baseTextView(title: String?, content: String?, active: Boolean): ProfileView {
        return ProfileValueSingleLineTextView.Builder()
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
            .withMandatory(isMandatory)
            .build(context)
    }

    override fun clickableEditTextView(hint: String?, content: String?, isMandatory: Boolean): ProfileView {
        return ProfileEditTextView.Builder()
            .withContent(content)
            .withHasNext(true)
            .withHint(hint)
            .withMode(ProfileEditTextView.Mode.CLICK)
            .withMandatory(isMandatory)
            .build(context)
    }

    override fun baseSelectableValuesView(items: Map<String, String>, keySelection: String?, emptyValue: String?, hint: String?, notifyKeys: Boolean): ProfileView {
        return ProfileSelectableValuesEditTextView.Builder()
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

    private abstract inner class InternalCreator {

        abstract fun createMail(profileField: ProfileField.Email): ProfileView
        abstract fun createMobilePhone(profileField: ProfileField.MobilePhone): ProfileView

        protected fun ProfileField.Email.hint() = context.getString(R.string.profile_mail)
        protected fun ProfileField.Email.value() = formatter.getValueForField(this)

        protected fun ProfileField.MobilePhone.hint() = context.getString(R.string.profile_mobile_phone)
        protected fun ProfileField.MobilePhone.value() = formatter.getValueForField(this)
    }

    private inner class MailCreator : InternalCreator() {

        override fun createMail(profileField: ProfileField.Email): ProfileView {
            return baseTextView(profileField.hint(), profileField.value()).apply {
                applyAssociatedField(profileField)
                profileField.addCallback(resolver, this)
            }
        }

        override fun createMobilePhone(profileField: ProfileField.MobilePhone): ProfileView {
            return baseEditTextView(
                profileField.hint(),
                null,
                InputType.TYPE_CLASS_PHONE,
                profileField.usage == ProfileFieldUsage.MANDATORY
            ).apply {
                applyAssociatedField(profileField)
                profileField.addCallback(resolver, this)
            }
        }
    }

    private inner class MobilePhoneCreator : InternalCreator() {

        override fun createMail(profileField: ProfileField.Email): ProfileView {
            return baseEditTextView(
                profileField.hint(),
                null,
                InputType.TYPE_CLASS_TEXT,
                profileField.usage == ProfileFieldUsage.MANDATORY
            ).apply {
                applyAssociatedField(profileField)
                profileField.addCallback(resolver, this)
            }
        }

        override fun createMobilePhone(profileField: ProfileField.MobilePhone): ProfileView {
            return baseTextView(profileField.hint(), profileField.value()).apply {
                applyAssociatedField(profileField)
                profileField.addCallback(resolver, this)
            }
        }
    }
}