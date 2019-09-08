package com.daimler.mbmobilesdk.profile.format

import com.daimler.mbmobilesdk.profile.fields.ProfileField

interface ProfileFieldValueFormatter {

    fun getValueForField(profileField: ProfileField.AccountIdentifier): String?

    fun getValueForField(profileField: ProfileField.Email): String?

    fun getValueForField(profileField: ProfileField.Salutation): String?
    fun getValueForField(profileField: ProfileField.Title): String?

    fun getValueForField(profileField: ProfileField.FirstName): String?
    fun getValueForField(profileField: ProfileField.LastName): String?

    fun getValueForField(profileField: ProfileField.MarketCountryCode): String?

    fun getValueForField(profileField: ProfileField.LanguageCode): String?

    fun getValueForField(profileField: ProfileField.Birthday): String?

    fun getValueForField(profileField: ProfileField.MobilePhone): String?
    fun getValueForField(profileField: ProfileField.LandlinePhone): String?

    fun getValueForField(profileField: ProfileField.AddressCountryCode): String?
    fun getValueForField(profileField: ProfileField.AddressStreet): String?
    fun getValueForField(profileField: ProfileField.AddressStreetType): String?
    fun getValueForField(profileField: ProfileField.AddressHouseName): String?
    fun getValueForField(profileField: ProfileField.AddressState): String?
    fun getValueForField(profileField: ProfileField.AddressProvince): String?
    fun getValueForField(profileField: ProfileField.AddressFloorNumber): String?
    fun getValueForField(profileField: ProfileField.AddressDoorNumber): String?
    fun getValueForField(profileField: ProfileField.AddressHouseNumber): String?
    fun getValueForField(profileField: ProfileField.AddressZipCode): String?
    fun getValueForField(profileField: ProfileField.AddressCity): String?
    fun getValueForField(profileField: ProfileField.AddressLine1): String?
    fun getValueForField(profileField: ProfileField.AddressPostOfficeBox): String?

    fun getValueForField(profileField: ProfileField.TaxNumber): String?

    fun getValueForField(profileField: ProfileField.BodyHeight): String?
    fun getValueForField(profileField: ProfileField.PreAdjustment): String?

    fun getValueForField(profileField: ProfileField.ContactPerMail): String?
    fun getValueForField(profileField: ProfileField.ContactPerPhone): String?
    fun getValueForField(profileField: ProfileField.ContactPerSms): String?
    fun getValueForField(profileField: ProfileField.ContactPerLetter): String?
    fun getValueForField(profileField: ProfileField.Communication): String?

    fun getValueForField(profileField: ProfileField.MePin): String?

    fun getValueForField(profileField: ProfileField.Address): String?
    fun getValueForField(profileField: ProfileField.Name): String?
    fun getValueForField(profileField: ProfileField.AdaptionValues): String?
}