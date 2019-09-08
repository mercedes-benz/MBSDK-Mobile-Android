package com.daimler.mbmobilesdk.profile.format

import android.content.Context
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.utils.UserValuePolicy
import com.daimler.mbmobilesdk.utils.UserValuePolicy.Companion.BIRTHDAY_DATE_DISPLAY_PATTERN
import com.daimler.mbmobilesdk.utils.extensions.*
import com.daimler.mbingresskit.common.CommunicationPreference
import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.common.UserPinStatus
import java.text.SimpleDateFormat
import java.util.*

internal class UserAwareProfileFieldValueFormatter(
    private val context: Context,
    private val profileFieldValueReceiver: ProfileFieldValueReceiver,
    private val user: User
) : ProfileFieldValueFormatter {

    override fun getValueForField(profileField: ProfileField.AccountIdentifier): String? {
        return null
    }

    override fun getValueForField(profileField: ProfileField.Email): String? {
        return user.email.takeIf { !it.isBlank() }
    }

    override fun getValueForField(profileField: ProfileField.Salutation): String? {
        return profileField.values.getDescription(user.salutationCode)
    }

    override fun getValueForField(profileField: ProfileField.Title): String? {
        return profileField.values.getDescription(user.title)
    }

    override fun getValueForField(profileField: ProfileField.FirstName): String? {
        return user.firstName
    }

    override fun getValueForField(profileField: ProfileField.LastName): String? {
        return user.lastName
    }

    override fun getValueForField(profileField: ProfileField.MarketCountryCode): String? {
        return getCountryByCountryCode(user.countryCode) ?: user.countryCode
    }

    override fun getValueForField(profileField: ProfileField.LanguageCode): String? {
        return profileField.values.getDescription(user.languageCode) ?: user.languageCode
    }

    override fun getValueForField(profileField: ProfileField.Birthday): String? {
        return user.birthday.toDate(UserValuePolicy.BIRTHDAY_DATE_PATTERN)?.let {
            SimpleDateFormat(BIRTHDAY_DATE_DISPLAY_PATTERN, Locale.getDefault()).format(it)
        }
    }

    override fun getValueForField(profileField: ProfileField.MobilePhone): String? {
        return user.mobilePhone.takeIf { !it.isBlank() }
    }

    override fun getValueForField(profileField: ProfileField.LandlinePhone): String? {
        return user.landlinePhone.takeIf { !it.isBlank() }
    }

    override fun getValueForField(profileField: ProfileField.AddressCountryCode): String? {
        return profileField.values.getDescription(user.address?.countryCode)
            ?: user.address?.countryCode.orEmpty()
    }

    override fun getValueForField(profileField: ProfileField.AddressStreet): String? {
        return user.address?.street.orEmpty()
    }

    override fun getValueForField(profileField: ProfileField.AddressStreetType): String? {
        return user.address?.streetType.orEmpty()
    }

    override fun getValueForField(profileField: ProfileField.AddressHouseName): String? {
        return user.address?.houseName.orEmpty()
    }

    override fun getValueForField(profileField: ProfileField.AddressState): String? {
        return profileField.values.getDescription(user.address?.state)
    }

    override fun getValueForField(profileField: ProfileField.AddressProvince): String? {
        val userProvince = user.address?.province
        return profileField.values?.getDescription(userProvince) ?: userProvince
    }

    override fun getValueForField(profileField: ProfileField.AddressFloorNumber): String? {
        return user.address?.floorNumber.orEmpty()
    }

    override fun getValueForField(profileField: ProfileField.AddressDoorNumber): String? {
        return user.address?.doorNumber.orEmpty()
    }

    override fun getValueForField(profileField: ProfileField.AddressHouseNumber): String? {
        return user.address?.houseNumber.orEmpty()
    }

    override fun getValueForField(profileField: ProfileField.AddressZipCode): String? {
        return user.address?.zipCode.orEmpty()
    }

    override fun getValueForField(profileField: ProfileField.AddressCity): String? {
        val userCity = user.address?.city
        return profileField.values?.getDescription(userCity) ?: userCity
    }

    override fun getValueForField(profileField: ProfileField.AddressLine1): String? {
        return user.address?.addressLine1.orEmpty()
    }

    override fun getValueForField(profileField: ProfileField.AddressPostOfficeBox): String? {
        return user.address?.postOfficeBox.orEmpty()
    }

    override fun getValueForField(profileField: ProfileField.TaxNumber): String? {
        return user.taxNumber.takeIf { !it.isBlank() }
    }

    override fun getValueForField(profileField: ProfileField.BodyHeight): String? {
        return user.bodyHeight?.bodyHeight?.toString()
    }

    override fun getValueForField(profileField: ProfileField.PreAdjustment): String? {
        return user.bodyHeight?.preAdjustment?.let {
            if (it) "" else null
        } ?: run {
            null
        }
    }

    override fun getValueForField(profileField: ProfileField.ContactPerMail): String? {
        return user.communicationPreference.toNullIfFalse { contactByMail }
    }

    override fun getValueForField(profileField: ProfileField.ContactPerPhone): String? {
        return user.communicationPreference.toNullIfFalse { contactByPhone }
    }

    override fun getValueForField(profileField: ProfileField.ContactPerSms): String? {
        return user.communicationPreference.toNullIfFalse { contactBySms }
    }

    override fun getValueForField(profileField: ProfileField.ContactPerLetter): String? {
        return user.communicationPreference.toNullIfFalse { contactByLetter }
    }

    override fun getValueForField(profileField: ProfileField.Communication): String? {
        return user.communicationPreference.getCommunication()
    }

    override fun getValueForField(profileField: ProfileField.MePin): String? {
        return if (user.pinStatus == UserPinStatus.SET) "****" else context.getString(R.string.profile_custom_pin_empty)
    }

    override fun getValueForField(profileField: ProfileField.Address): String? {
        val builder = StringBuilder()
        val address = user.address
        builder.apply {
            // Street
            address?.street?.takeIf { !it.isBlank() }?.let {
                append(it).blank().append(address.houseNumber ?: "").newLine()
            }
            // Postal Code
            address?.zipCode?.takeIf { !it.isBlank() }?.let {
                append(it).blank()
            }
            // City
            profileField.getNestedField<ProfileField.AddressCity>()?.let { getValueForField(it) }?.takeIf {
                !it.isBlank()
            }?.let {
                append(it).newLine()
            } ?: newLine()
            // State
            profileField.getNestedField<ProfileField.AddressState>()?.let { getValueForField(it) }?.takeIf {
                !it.isBlank()
            }?.let {
                append(it).newLine()
            }
            // Province
            profileField.getNestedField<ProfileField.AddressProvince>()?.let { getValueForField(it) }?.takeIf {
                !it.isBlank()
            }?.let {
                append(it).newLine()
            }
            // Country
            address?.countryCode?.takeIf { !it.isBlank() }?.let {
                append(getCountryByCountryCode(it).orEmpty())
            }
        }
        return builder.toString()
    }

    override fun getValueForField(profileField: ProfileField.Name): String? {
        val salutation = profileFieldValueReceiver.getSalutationForKey(user.salutationCode).orEmpty()
        val title = profileFieldValueReceiver.getTitleForKey(user.title).orEmpty()
        return user.formatNameWithSalutationAndTitle(salutation, title)
    }

    override fun getValueForField(profileField: ProfileField.AdaptionValues): String? {
        return user.bodyHeight?.bodyHeight?.toString()
    }

    private fun CommunicationPreference.toNullIfFalse(accessor: CommunicationPreference.() -> Boolean) =
        if (this.accessor()) "" else null

    private fun CommunicationPreference.getCommunication(): String? {
        val value = StringBuilder()

        if (contactByMail) value.appendln(context.getString(R.string.profile_is_contacted_by_email))
        if (contactByLetter) value.appendln(context.getString(R.string.profile_is_contacted_by_letter))
        if (contactByPhone) value.appendln(context.getString(R.string.profile_is_contacted_by_phone))
        if (contactBySms) value.appendln(context.getString(R.string.profile_is_contacted_by_sms))

        return value.trim().toString()
    }

    private inline fun <reified T : ProfileField> ProfileField.Address.getNestedField(): T? =
        combinedFields?.getField()

    private inline fun <reified T : ProfileField> List<ProfileField>.getField(): T? = find { it is T } as? T
}