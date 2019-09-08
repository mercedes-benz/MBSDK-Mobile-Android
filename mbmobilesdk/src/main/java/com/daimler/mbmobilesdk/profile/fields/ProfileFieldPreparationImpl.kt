package com.daimler.mbmobilesdk.profile.fields

import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.common.*

internal class ProfileFieldPreparationImpl(
    private val profileFieldsData: ProfileFieldsData,
    private val addCustomFields: Boolean,
    vararg combinationOptions: CombinationOption
) : ProfileFieldPreparation {

    private val combinationOptions: List<CombinationOption> = combinationOptions.toList()

    override fun fields(): List<ProfileField> {
        val combinationHandler = CombinationHandler(combinationOptions) { mapCustomerDataFieldToProfileField(it) }
        val result = mutableListOf<ProfileField>()
        profileFieldsData.customerDataFields.mapNotNullTo(result) {
            if (!combinationHandler.handleDataField(it)) {
                mapCustomerDataFieldToProfileField(it)
            } else {
                null
            }
        }
        result.addAll(combinationHandler.fields())
        if (addCustomFields) {
            result.addAll(getCustomFields(result.maxBy { it.order }?.order ?: result.size))
        }
        return result.sortedBy { it.order }
    }

    private fun mapCustomerDataFieldToProfileField(field: CustomerDataField): ProfileField? {
        MBLoggerKit.d("$field")
        val type = field.fieldType
        val usage = field.fieldUsage
        val order = field.sequenceOrder
        val validation = field.fieldValidation
        val values = field.selectableValues
        return when (type) {
            ProfileFieldType.EMAIL -> ProfileField.Email(usage, order, validation)
            ProfileFieldType.SALUTATION -> values?.let { ProfileField.Salutation(usage, order, it) }
            ProfileFieldType.TITLE -> values?.let { ProfileField.Title(usage, order, it, it.shouldShowExternally()) }
            ProfileFieldType.FIRST_NAME -> ProfileField.FirstName(usage, order, validation)
            ProfileFieldType.LAST_NAME_1 -> ProfileField.LastName(usage, order, validation)
            ProfileFieldType.LANGUAGE_CODE -> values?.let { ProfileField.LanguageCode(usage, order, it, it.shouldShowExternally()) }
            ProfileFieldType.BIRTHDAY -> ProfileField.Birthday(usage, order)
            ProfileFieldType.MOBILE_PHONE -> ProfileField.MobilePhone(usage, order, validation)
            ProfileFieldType.LANDLINE_PHONE -> ProfileField.LandlinePhone(usage, order, validation)
            ProfileFieldType.ADDRESS_COUNTRY_CODE -> values?.let { ProfileField.AddressCountryCode(usage, order, it, it.shouldShowExternally()) }
            ProfileFieldType.ADDRESS_STREET -> ProfileField.AddressStreet(usage, order, validation)
            ProfileFieldType.ADDRESS_STREET_TYPE -> ProfileField.AddressStreetType(usage, order)
            ProfileFieldType.ADDRESS_HOUSE_NAME -> ProfileField.AddressHouseName(usage, order)
            ProfileFieldType.ADDRESS_STATE -> values?.let { ProfileField.AddressState(usage, order, it, it.shouldShowExternally()) }
            ProfileFieldType.ADDRESS_PROVINCE -> ProfileField.AddressProvince(usage, order, validation, values, values?.shouldShowExternally() ?: false)
            ProfileFieldType.ADDRESS_FLOOR_NUMBER -> ProfileField.AddressFloorNumber(usage, order)
            ProfileFieldType.ADDRESS_DOOR_NUMBER -> ProfileField.AddressDoorNumber(usage, order)
            ProfileFieldType.ADDRESS_HOUSE_NUMBER -> ProfileField.AddressHouseNumber(usage, order, validation)
            ProfileFieldType.ADDRESS_ZIP_CODE -> ProfileField.AddressZipCode(usage, order, validation)
            ProfileFieldType.ADDRESS_CITY -> ProfileField.AddressCity(usage, order, validation, values, values?.shouldShowExternally() ?: false)
            ProfileFieldType.COMMUNICATION_EMAIL -> ProfileField.ContactPerMail(usage, order)
            ProfileFieldType.COMMUNICATION_LETTER -> ProfileField.ContactPerLetter(usage, order)
            ProfileFieldType.COMMUNICATION_PHONE -> ProfileField.ContactPerPhone(usage, order)
            ProfileFieldType.COMMUNICATION_SMS -> ProfileField.ContactPerSms(usage, order)
            ProfileFieldType.LAST_NAME_2 -> null
            ProfileFieldType.ADDRESS_LINE_1 -> ProfileField.AddressLine1(usage, order, validation)
            ProfileFieldType.ADDRESS_LINE_2 -> null
            ProfileFieldType.ADDRESS_LINE_3 -> null
            ProfileFieldType.ADDRESS_POST_OFFICE_BOX -> ProfileField.AddressPostOfficeBox(usage, order, validation)
            ProfileFieldType.ACCOUNT_IDENTIFIER -> null
            ProfileFieldType.MOBILE_PHONE_NUMBER_VERIFIED -> null
            ProfileFieldType.MARKET_COUNTRY -> ProfileField.MarketCountryCode(usage, order)
            ProfileFieldType.SERVICE_DEALER -> null
            ProfileFieldType.LICENSE_PLATE -> null
            ProfileFieldType.TAX_NUMBER -> ProfileField.TaxNumber(usage, order, validation)
            ProfileFieldType.BODY_HEIGHT -> ProfileField.BodyHeight(usage, order, validation)
            ProfileFieldType.PRE_ADJUSTMENT -> ProfileField.PreAdjustment(usage, order)
            ProfileFieldType.UNKNOWN -> null
            else -> null
        }
    }

    private fun ProfileSelectableValues.shouldShowExternally() =
        selectableValues.size > MAX_DROPDOWN_ITEMS

    private fun getCustomFields(minOrder: Int): List<ProfileField> =
        listOf(
            ProfileField.MePin(ProfileFieldUsage.OPTIONAL, minOrder)
        )

    private class CombinationHandler(
        private val combinations: List<CombinationOption>,
        private val fieldConverter: (CustomerDataField) -> ProfileField?
    ) {

        private val combinationCollection = mutableMapOf<CombinationOption, MutableList<ProfileField>>()

        fun handleDataField(field: CustomerDataField): Boolean {
            val option = getCombinationOf(field.fieldType)
            return option?.let {
                addCustomerDataField(it, field)
                true
            } ?: false
        }

        fun fields(): List<ProfileField> {
            return combinationCollection.map { entry ->
                val order = entry.value.minBy { it.order }?.order ?: 0
                val usage = getUsage(entry.value)
                when (entry.key) {
                    is CombinationOption.Address -> ProfileField.Address(usage, order, entry.value)
                    is CombinationOption.Name -> ProfileField.Name(usage, order, entry.value)
                    is CombinationOption.AdaptionValues -> ProfileField.AdaptionValues(usage, order, entry.value)
                    is CombinationOption.Communication -> ProfileField.Communication(usage, order, entry.value)
                }
            }
        }

        private fun getUsage(fields: List<ProfileField>): ProfileFieldUsage {
            return if (fields.any { it.usage == ProfileFieldUsage.MANDATORY }) {
                ProfileFieldUsage.MANDATORY
            } else {
                ProfileFieldUsage.OPTIONAL
            }
        }

        private fun addCustomerDataField(combinationOption: CombinationOption, fieldType: CustomerDataField) {
            fieldConverter(fieldType)?.let {
                val list = combinationCollection[combinationOption]
                    ?: mutableListOf()
                list.add(it)
                combinationCollection[combinationOption] = list
            }
        }

        private fun getCombinationOf(fieldType: ProfileFieldType): CombinationOption? =
            combinations.find { it.combines(fieldType) }
    }

    sealed class CombinationOption {

        protected abstract val combinedTypes: List<ProfileFieldType>

        fun combines(type: ProfileFieldType): Boolean {
            return combinedTypes.contains(type)
        }

        object Address : CombinationOption() {
            override val combinedTypes: List<ProfileFieldType> = listOf(
                ProfileFieldType.ADDRESS_COUNTRY_CODE,
                ProfileFieldType.ADDRESS_STREET,
                ProfileFieldType.ADDRESS_STREET_TYPE,
                ProfileFieldType.ADDRESS_HOUSE_NAME,
                ProfileFieldType.ADDRESS_STATE,
                ProfileFieldType.ADDRESS_PROVINCE,
                ProfileFieldType.ADDRESS_FLOOR_NUMBER,
                ProfileFieldType.ADDRESS_DOOR_NUMBER,
                ProfileFieldType.ADDRESS_HOUSE_NUMBER,
                ProfileFieldType.ADDRESS_ZIP_CODE,
                ProfileFieldType.ADDRESS_CITY,
                ProfileFieldType.ADDRESS_LINE_1,
                ProfileFieldType.ADDRESS_LINE_2,
                ProfileFieldType.ADDRESS_LINE_3,
                ProfileFieldType.ADDRESS_POST_OFFICE_BOX
            )
        }

        object Name : CombinationOption() {
            override val combinedTypes: List<ProfileFieldType> = listOf(
                ProfileFieldType.FIRST_NAME,
                ProfileFieldType.LAST_NAME_1,
                ProfileFieldType.LAST_NAME_2
            )
        }

        object AdaptionValues : CombinationOption() {
            override val combinedTypes: List<ProfileFieldType> = listOf(
                ProfileFieldType.BODY_HEIGHT,
                ProfileFieldType.PRE_ADJUSTMENT
            )
        }

        object Communication : CombinationOption() {
            override val combinedTypes: List<ProfileFieldType> = listOf(
                    ProfileFieldType.COMMUNICATION_EMAIL,
                    ProfileFieldType.COMMUNICATION_LETTER,
                    ProfileFieldType.COMMUNICATION_SMS,
                    ProfileFieldType.COMMUNICATION_PHONE
            )
        }
    }

    private companion object {
        private const val MAX_DROPDOWN_ITEMS = 10
    }
}