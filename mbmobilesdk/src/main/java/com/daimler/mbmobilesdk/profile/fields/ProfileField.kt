package com.daimler.mbmobilesdk.profile.fields

import com.daimler.mbmobilesdk.profile.creator.ProfileViewCreatable
import com.daimler.mbmobilesdk.profile.creator.ProfileViewableCreator
import com.daimler.mbmobilesdk.profile.format.ProfileFieldValueFormattable
import com.daimler.mbmobilesdk.profile.format.ProfileFieldValueFormatter
import com.daimler.mbingresskit.common.ProfileFieldUsage
import com.daimler.mbingresskit.common.ProfileFieldValidation
import com.daimler.mbingresskit.common.ProfileSelectableValues

sealed class ProfileField(
    val usage: ProfileFieldUsage,
    val order: Int,
    private val validation: ProfileFieldValidation? = null,
    val combinedFields: List<ProfileField>? = null
) : ProfileViewCreatable, ProfileFieldActionResolvable, ProfileFieldValueFormattable {

    private val validator = Validator()

    fun validationError(value: String?) = validationError(value, validation)

    fun validationError(value: String?, validation: ProfileFieldValidation?): ValidationError =
        validator.validationError(value, validation)

    /* Account Identifier */
    class AccountIdentifier(usage: ProfileFieldUsage, order: Int, val values: ProfileSelectableValues) : ProfileField(usage, order) {

        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    /* Mail */
    class Email(usage: ProfileFieldUsage, order: Int, validation: ProfileFieldValidation?) : ProfileField(usage, order, validation) {

        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    /* Salutation + Title */
    class Salutation(usage: ProfileFieldUsage, order: Int, val values: ProfileSelectableValues) : ProfileField(usage, order) {

        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class Title(usage: ProfileFieldUsage, order: Int, val values: ProfileSelectableValues, val externalSelection: Boolean) : ProfileField(usage, order) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    /* Name */
    class FirstName(usage: ProfileFieldUsage, order: Int, validation: ProfileFieldValidation?) : ProfileField(usage, order, validation) {

        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class LastName(usage: ProfileFieldUsage, order: Int, validation: ProfileFieldValidation?) : ProfileField(usage, order, validation) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    /* Market Country Code */

    class MarketCountryCode(usage: ProfileFieldUsage, order: Int) : ProfileField(usage, order) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    /* Language Code */
    class LanguageCode(usage: ProfileFieldUsage, order: Int, val values: ProfileSelectableValues, val externalSelection: Boolean) : ProfileField(usage, order) {

        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    /* Birthday */
    class Birthday(usage: ProfileFieldUsage, order: Int) : ProfileField(usage, order) {

        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    /* Phone */
    class MobilePhone(usage: ProfileFieldUsage, order: Int, validation: ProfileFieldValidation?) : ProfileField(usage, order, validation) {

        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class LandlinePhone(usage: ProfileFieldUsage, order: Int, validation: ProfileFieldValidation?) : ProfileField(usage, order, validation) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    /* Address */
    class AddressCountryCode(usage: ProfileFieldUsage, order: Int, val values: ProfileSelectableValues, val externalSelection: Boolean) : ProfileField(usage, order) {

        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class AddressStreet(usage: ProfileFieldUsage, order: Int, validation: ProfileFieldValidation?) : ProfileField(usage, order, validation) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class AddressStreetType(usage: ProfileFieldUsage, order: Int) : ProfileField(usage, order) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class AddressHouseName(usage: ProfileFieldUsage, order: Int) : ProfileField(usage, order) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class AddressState(usage: ProfileFieldUsage, order: Int, val values: ProfileSelectableValues, val externalSelection: Boolean) : ProfileField(usage, order) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class AddressProvince(
        usage: ProfileFieldUsage,
        order: Int,
        validation: ProfileFieldValidation?,
        val values: ProfileSelectableValues?,
        val externalSelection: Boolean
    ) : ProfileField(usage, order, validation) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class AddressFloorNumber(usage: ProfileFieldUsage, order: Int) : ProfileField(usage, order) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class AddressDoorNumber(usage: ProfileFieldUsage, order: Int) : ProfileField(usage, order) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class AddressHouseNumber(usage: ProfileFieldUsage, order: Int, validation: ProfileFieldValidation?) : ProfileField(usage, order, validation) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class AddressZipCode(usage: ProfileFieldUsage, order: Int, validation: ProfileFieldValidation?) : ProfileField(usage, order, validation) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class AddressCity(
        usage: ProfileFieldUsage,
        order: Int,
        validation: ProfileFieldValidation?,
        val values: ProfileSelectableValues?,
        val externalSelection: Boolean
    ) : ProfileField(usage, order, validation) {

        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class AddressLine1(usage: ProfileFieldUsage, order: Int, validation: ProfileFieldValidation?) : ProfileField(usage, order, validation) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class AddressPostOfficeBox(usage: ProfileFieldUsage, order: Int, validation: ProfileFieldValidation?) : ProfileField(usage, order, validation) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    /* Communication Channel */
    class ContactPerMail(usage: ProfileFieldUsage, order: Int) : ProfileField(usage, order) {

        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class ContactPerPhone(usage: ProfileFieldUsage, order: Int) : ProfileField(usage, order) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class ContactPerSms(usage: ProfileFieldUsage, order: Int) : ProfileField(usage, order) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class ContactPerLetter(usage: ProfileFieldUsage, order: Int) : ProfileField(usage, order) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    /*
    Tax
     */

    class TaxNumber(usage: ProfileFieldUsage, order: Int, validation: ProfileFieldValidation?) : ProfileField(usage, order, validation) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    /*
    BodyHeight
     */

    class BodyHeight(usage: ProfileFieldUsage, order: Int, validation: ProfileFieldValidation?) : ProfileField(usage, order, validation) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    /*
    PreAdjustment
     */

    class PreAdjustment(usage: ProfileFieldUsage, order: Int) : ProfileField(usage, order) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    /*
    Custom
     */

    class MePin(usage: ProfileFieldUsage, order: Int) : ProfileField(usage, order) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    /*
    Combinations
     */

    class Address(usage: ProfileFieldUsage, order: Int, fields: List<ProfileField>) : ProfileField(usage, order, null, fields) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class Name(usage: ProfileFieldUsage, order: Int, fields: List<ProfileField>) : ProfileField(usage, order, null, fields) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class AdaptionValues(usage: ProfileFieldUsage, order: Int, fields: List<ProfileField>) : ProfileField(usage, order, null, fields) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    class Communication(usage: ProfileFieldUsage, order: Int, fields: List<ProfileField>) : ProfileField(usage, order, null, fields) {
        override fun create(creator: ProfileViewableCreator): ProfileViewable {
            return creator.createViewable(this)
        }

        override fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable) {
            actionHandler.addActionCallback(this, viewable)
        }

        override fun getFormattedValue(formatter: ProfileFieldValueFormatter): String? {
            return formatter.getValueForField(this)
        }
    }

    sealed class ValidationError {
        object None : ValidationError()
        class Short(val min: Int) : ValidationError()
        class Long(val max: Int) : ValidationError()
        object Invalid : ValidationError()
    }

    private inner class Validator {

        fun validationError(value: String?, validation: ProfileFieldValidation?): ValidationError {
            val minLength = validation?.minLength ?: Integer.MIN_VALUE
            val maxLength = validation?.maxLength ?: Integer.MAX_VALUE
            return when {
                value.isNullOrBlank() -> validationErrorForNullOrBlank(validation)
                validation == null -> ValidationError.None
                value.length < minLength -> ValidationError.Short(minLength)
                value.length > maxLength -> ValidationError.Long(maxLength)
                validation.regularExpression != null -> validateWithRegex(value, validation.regularExpression)
                else -> ValidationError.None
            }
        }

        private fun validationErrorForNullOrBlank(validation: ProfileFieldValidation?): ValidationError {
            return if (usage != ProfileFieldUsage.MANDATORY) {
                ValidationError.None
            } else {
                validation?.minLength?.let {
                    ValidationError.Short(it)
                } ?: ValidationError.Invalid
            }
        }

        private fun validateWithRegex(value: String, regex: String?): ValidationError {
            return if (regex?.toRegex()?.matches(value) == true) {
                ValidationError.None
            } else {
                ValidationError.Invalid
            }
        }
    }
}