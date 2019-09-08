package com.daimler.mbmobilesdk.profile.creator

import android.content.Context
import android.text.InputType
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileFieldActionHandler
import com.daimler.mbmobilesdk.profile.fields.ProfileFieldResolutionStrategy
import com.daimler.mbmobilesdk.profile.fields.ProfileViewable
import com.daimler.mbmobilesdk.profile.format.ProfileFieldValueFormatter
import com.daimler.mbmobilesdk.utils.extensions.orNullIfBlank
import com.daimler.mbingresskit.common.ProfileFieldUsage
import com.daimler.mbingresskit.common.ProfileSelectableValues
import com.daimler.mbingresskit.common.User

internal abstract class BaseDynamicProfileCreator<T : ProfileViewable>(
    context: Context,
    resolver: ProfileFieldActionHandler,
    protected val user: User?,
    private val resolutionStrategy: ProfileFieldResolutionStrategy,
    protected val formatter: ProfileFieldValueFormatter,
    private val emptyResolvable: T,
    private val allowLiveTextEdit: Boolean
) : DynamicProfileCreator<T>, ProfileViewableCreator {

    private var _context: Context? = context
    protected val context: Context
        get() = _context ?: throw IllegalStateException("Context was invalidated!")

    private var _resolver: ProfileFieldActionHandler? = resolver
    protected val resolver: ProfileFieldActionHandler
        get() = _resolver ?: throw IllegalStateException("Resolver was invalidated!")

    override fun destroy() {
        _context = null
        _resolver = null
    }

    @Suppress("UNCHECKED_CAST")
    override fun createViewables(fields: List<ProfileField>): List<T> {
        return fields
            .filter { shouldCreateAndResolve(it) }
            .mapNotNull { it.create(this) as? T }
    }

    override fun createViewable(profileField: ProfileField.AccountIdentifier): ProfileViewable {
        return baseSelectableValuesViewOrClickable(
            profileField.values,
            false,
            profileField,
            profileField.values.defaultSelectableValueKey,
            null,
            context.getString(R.string.profile_account_identifier)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.Email): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_mail),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField),
            active = profileField.usage != ProfileFieldUsage.READ_ONLY
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.Salutation): ProfileViewable {
        return baseSelectableValuesViewOrClickable(
            profileField.values,
            false,
            profileField,
            user?.salutationCode.orNullIfBlank(),
            null,
            context.getString(R.string.profile_salutation)
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

    override fun createViewable(profileField: ProfileField.FirstName): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_forename),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.LastName): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_surname),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.MarketCountryCode): ProfileViewable {
        return baseTextView(
            context.getString(R.string.profile_countrycode),
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.LanguageCode): ProfileViewable {
        return baseSelectableValuesViewOrClickable(
            profileField.values,
            profileField.externalSelection,
            profileField,
            user?.languageCode,
            null,
            context.getString(R.string.profile_preferred_language)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.Birthday): ProfileViewable {
        return textViewOrClickableEditText(
            context.getString(R.string.profile_birthday),
            formatter.getValueForField(profileField),
            profileField.usage.isMandatory
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.MobilePhone): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_mobile_phone),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField),
            InputType.TYPE_CLASS_PHONE
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.LandlinePhone): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_landline_phone),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField),
            InputType.TYPE_CLASS_PHONE
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.AddressCountryCode): ProfileViewable {
        return baseSelectableValuesViewOrClickable(
            profileField.values,
            profileField.externalSelection,
            profileField,
            user?.address?.countryCode,
            profileDefaultString(),
            context.getString(R.string.profile_country)
        )
    }

    override fun createViewable(profileField: ProfileField.AddressStreet): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_street_address),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.AddressStreetType): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_street_type),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.AddressHouseName): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_house_name),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.AddressState): ProfileViewable {
        return baseSelectableValuesViewOrClickable(
            profileField.values,
            profileField.externalSelection,
            profileField,
            user?.address?.state,
            profileDefaultString(),
            context.getString(R.string.profile_state)
        )
    }

    override fun createViewable(profileField: ProfileField.AddressProvince): ProfileViewable {
        return profileField.values?.let {
            baseSelectableValuesViewOrClickable(
                it,
                profileField.externalSelection,
                profileField,
                user?.address?.province,
                profileDefaultString(),
                context.getString(R.string.profile_province)
            )
        } ?: run {
            textViewOrEditText(
                context.getString(R.string.profile_province),
                profileField.usage.isMandatory,
                formatter.getValueForField(profileField)
            ).apply {
                applyAssociatedField(profileField)
                profileField.addCallback(resolver, this)
            }
        }
    }

    override fun createViewable(profileField: ProfileField.AddressFloorNumber): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_floor_number),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.AddressDoorNumber): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_door_number),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.AddressHouseNumber): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_house_no),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.AddressZipCode): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_postcode),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.AddressCity): ProfileViewable {
        return profileField.values?.let {
            baseSelectableValuesViewOrClickable(
                it,
                profileField.externalSelection,
                profileField,
                user?.address?.city,
                profileDefaultString(),
                context.getString(R.string.profile_city)
            )
        } ?: run {
            textViewOrEditText(
                context.getString(R.string.profile_city),
                profileField.usage.isMandatory,
                formatter.getValueForField(profileField)
            ).apply {
                applyAssociatedField(profileField)
                profileField.addCallback(resolver, this)
            }
        }
    }

    override fun createViewable(profileField: ProfileField.AddressLine1): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_address_additional),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.AddressPostOfficeBox): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_post_office_box),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.TaxNumber): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_tax_number),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.BodyHeight): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_body_height),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField),
            InputType.TYPE_CLASS_NUMBER
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.PreAdjustment): ProfileViewable {
        return baseSwitchView(
            context.getString(R.string.profile_pre_adjustment),
            user?.bodyHeight?.preAdjustment ?: false
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.ContactPerMail): ProfileViewable {
        return baseSwitchView(
            context.getString(R.string.profile_is_contacted_by_email),
            user?.communicationPreference?.contactByMail ?: false
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.ContactPerPhone): ProfileViewable {
        return baseSwitchView(
            context.getString(R.string.profile_is_contacted_by_phone),
            user?.communicationPreference?.contactByPhone ?: false
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.ContactPerSms): ProfileViewable {
        return baseSwitchView(
            context.getString(R.string.profile_is_contacted_by_sms),
            user?.communicationPreference?.contactBySms ?: false
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.ContactPerLetter): ProfileViewable {
        return baseSwitchView(
            context.getString(R.string.profile_is_contacted_by_letter),
            user?.communicationPreference?.contactByLetter ?: false
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.Communication): ProfileViewable {
        return baseTextView(
                context.getString(R.string.profile_contacted),
                formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.MePin): ProfileViewable {
        return baseTextView(
            context.getString(R.string.profile_custom_pin_title),
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.Address): ProfileViewable {
        return baseTextView(
            context.getString(R.string.profile_address),
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.Name): ProfileViewable {
        return baseTextView(
            context.getString(R.string.profile_name),
            formatter.getValueForField(profileField)
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    override fun createViewable(profileField: ProfileField.AdaptionValues): ProfileViewable {
        return textViewOrEditText(
            context.getString(R.string.profile_body_height),
            profileField.usage.isMandatory,
            formatter.getValueForField(profileField),
            InputType.TYPE_CLASS_NUMBER
        ).apply {
            applyAssociatedField(profileField)
            profileField.addCallback(resolver, this)
        }
    }

    private fun textViewOrEditText(
        title: String,
        isMandatory: Boolean,
        content: String?,
        inputType: Int = InputType.TYPE_CLASS_TEXT,
        active: Boolean = true
    ): T =
        if (allowLiveTextEdit) {
            baseEditTextView(title, content, inputType, isMandatory)
        } else {
            baseTextView(title, content, active)
        }

    private fun textViewOrClickableEditText(title: String, content: String?, isMandatory: Boolean): T =
        if (allowLiveTextEdit) {
            clickableEditTextView(title, content, isMandatory)
        } else {
            baseTextView(title, content)
        }

    protected abstract fun baseTextView(title: String?, content: String?, active: Boolean = true): T

    protected abstract fun baseEditTextView(hint: String?, content: String?, inputType: Int, isMandatory: Boolean): T

    protected abstract fun clickableEditTextView(hint: String?, content: String?, isMandatory: Boolean): T

    protected fun baseSelectableValuesViewOrClickable(
        values: ProfileSelectableValues,
        showExternal: Boolean,
        field: ProfileField,
        initialKey: String?,
        emptyValue: String?,
        hint: String?
    ): T {
        val itemList = values.selectableValues
        val selectedItem = initialKey ?: values.defaultSelectableValueKey
        return when {
            itemList.isEmpty() -> emptyResolvable
            showExternal -> {
                val value = selectedItem?.let { selected ->
                    values.selectableValues.find { it.key == selected || it.description == selected }?.description
                }
                clickableEditTextView(hint, value, field.usage.isMandatory).apply {
                    applyAssociatedField(field)
                    field.addCallback(resolver, this)
                }
            }
            else -> {
                baseSelectableValuesView(
                    itemList.map { it.key to it.description }.toMap(),
                    selectedItem,
                    emptyValue,
                    hint,
                    values.matchSelectableValueByKey
                )
            }
        }
    }

    protected abstract fun baseSelectableValuesView(
        items: Map<String, String>,
        keySelection: String?,
        emptyValue: String?,
        hint: String?,
        notifyKeys: Boolean
    ): T

    protected abstract fun baseCheckBoxView(
        title: String,
        checked: Boolean
    ): T

    protected abstract fun baseSwitchView(
        title: String,
        checked: Boolean
    ): T

    protected open fun shouldCreateAndResolve(field: ProfileField) =
        when (resolutionStrategy) {
            is ProfileFieldResolutionStrategy.All ->
                !resolutionStrategy.exclusions.contains(field::class.java)
            is ProfileFieldResolutionStrategy.Mandatory ->
                when {
                    field.usage == ProfileFieldUsage.MANDATORY -> !resolutionStrategy.exclusions.contains(field::class.java)
                    else -> resolutionStrategy.inclusions.contains(field::class.java)
                }
            is ProfileFieldResolutionStrategy.Only ->
                resolutionStrategy.inclusions.contains(field::class.java)
        }

    protected fun String?.orProfileDefault(): String =
        takeIf { !it.isNullOrBlank() } ?: profileDefaultString()

    protected fun profileDefaultString() = "-"

    protected val ProfileFieldUsage.isMandatory: Boolean
        get() = this == ProfileFieldUsage.MANDATORY

    private fun ProfileSelectableValues.defaultSelectableValueOrDefault(default: String) =
        defaultSelectableValueKey?.let { key ->
            selectableValues.firstOrNull { it.key == key }?.description
        } ?: default
}