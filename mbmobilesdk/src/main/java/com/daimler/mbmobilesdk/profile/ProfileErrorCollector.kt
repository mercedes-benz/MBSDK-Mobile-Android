package com.daimler.mbmobilesdk.profile

import android.content.Context
import androidx.annotation.StringRes
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileValueCallback
import com.daimler.mbmobilesdk.profile.fields.ProfileViewable
import com.daimler.mbloggerkit.MBLoggerKit

/**
 * Helper class to collect errors for ProfileFields.
 */
internal class ProfileErrorCollector {

    private val fields = LinkedHashMap<ProfileField, ProfileField.ValidationError>()

    /**
     * Applies a [ProfileValueCallback] to the given [viewable].
     *
     * @param viewable the [ProfileViewable]
     * @param validate true to validate values using [ProfileField.validationError]
     * @param action an action to execute when the value changed
     * (when [ProfileValueCallback.onValueChanged] is called)
     */
    fun applyCallback(viewable: ProfileViewable, validate: Boolean = true, action: (String?) -> Unit) {
        collectInitial(viewable, validate)
        viewable.applyCallback(object : ProfileValueCallback {
            override fun onValueChanged(value: String?) {
                action(value)
                if (validate) collect(viewable, value)
            }
        })
    }

    fun findFirstError(context: Context): String? {
        fields.forEach {
            MBLoggerKit.d("${it.key::class.java.simpleName} -> ${it.value::class.java.simpleName}")
        }
        val entry = fields.entries.firstOrNull {
            it.value != ProfileField.ValidationError.None
        }
        return entry?.let { generateError(context, it.key, it.value) }
    }

    fun clear() {
        fields.clear()
    }

    private fun collect(viewable: ProfileViewable, value: String?) {
        viewable.associatedField()?.let {
            fields[it] = it.validationError(value)
        }
    }

    inline fun <reified T : ProfileField> collectManual(value: String?) {
        val key = fields.keys.firstOrNull { it::class.java == T::class.java }
        key?.let { fields[it] = it.validationError(value) }
    }

    private fun collectInitial(viewable: ProfileViewable, validate: Boolean) {
        viewable.associatedField()?.let {
            fields[it] = if (validate) {
                it.validationError(viewable.currentValue())
            } else {
                ProfileField.ValidationError.None
            }
        }
    }

    private fun generateError(context: Context, profileField: ProfileField, error: ProfileField.ValidationError): String? =
        when (profileField) {
            is ProfileField.AccountIdentifier -> generalError(context)
            is ProfileField.Email -> context.getString(R.string.registration_mail_invalid)
            is ProfileField.Salutation -> error(context, R.string.profile_salutation, error)
            is ProfileField.Title -> error(context, R.string.profile_title, error)
            is ProfileField.FirstName -> error(context, R.string.profile_name, error)
            is ProfileField.LastName -> error(context, R.string.profile_name, error)
            is ProfileField.MarketCountryCode -> generalError(context)
            is ProfileField.LanguageCode -> generalError(context)
            is ProfileField.Birthday -> error(context, R.string.profile_birthday, error)
            is ProfileField.MobilePhone -> error(context, R.string.profile_mobile_phone, error)
            is ProfileField.LandlinePhone -> error(context, R.string.profile_landline_phone, error)
            is ProfileField.AddressCountryCode -> generalError(context)
            is ProfileField.AddressStreet -> error(context, R.string.profile_street_address, error)
            is ProfileField.AddressStreetType -> generalError(context)
            is ProfileField.AddressHouseName -> generalError(context)
            is ProfileField.AddressState -> error(context, R.string.profile_state, error)
            is ProfileField.AddressProvince -> error(context, R.string.profile_province, error)
            is ProfileField.AddressFloorNumber -> generalError(context)
            is ProfileField.AddressDoorNumber -> generalError(context)
            is ProfileField.AddressHouseNumber -> error(context, R.string.profile_house_no, error)
            is ProfileField.AddressZipCode -> error(context, R.string.profile_postcode, error)
            is ProfileField.AddressCity -> error(context, R.string.profile_city, error)
            is ProfileField.AddressLine1 -> generalError(context)
            is ProfileField.AddressPostOfficeBox -> error(context, R.string.profile_post_office_box, error)
            is ProfileField.TaxNumber -> error(context, R.string.profile_tax_number, error)
            is ProfileField.BodyHeight -> error(context, R.string.profile_body_height, error)
            is ProfileField.PreAdjustment -> generalError(context)
            is ProfileField.ContactPerMail -> generalError(context)
            is ProfileField.ContactPerPhone -> generalError(context)
            is ProfileField.ContactPerSms -> generalError(context)
            is ProfileField.ContactPerLetter -> generalError(context)
            is ProfileField.AdaptionValues -> null
            is ProfileField.Communication -> null
            is ProfileField.MePin -> null
            is ProfileField.Address -> null
            is ProfileField.Name -> null
        }

    private fun error(context: Context, @StringRes resIdName: Int, error: ProfileField.ValidationError): String? =
        when (error) {
            ProfileField.ValidationError.None -> null
            is ProfileField.ValidationError.Short -> errorShort(context, resIdName, error.min)
            is ProfileField.ValidationError.Long -> errorLong(context, resIdName, error.max)
            ProfileField.ValidationError.Invalid -> errorInvalid(context, resIdName)
        }

    private fun errorShort(context: Context, @StringRes resIdName: Int, minLength: Int) =
        String.format(
            context.getString(R.string.profile_field_validation_min_length_error),
            context.getString(resIdName),
            minLength
        )

    private fun errorLong(context: Context, @StringRes resIdName: Int, maxLength: Int) =
        String.format(
            context.getString(R.string.profile_field_validation_max_length_error),
            context.getString(resIdName),
            maxLength
        )

    private fun errorInvalid(context: Context, @StringRes resIdName: Int) =
        String.format(
            context.getString(R.string.profile_field_validation_wrong_format),
            context.getString(resIdName)
        )

    private fun generalError(context: Context) =
        context.getString(R.string.profile_input_invalid)
}