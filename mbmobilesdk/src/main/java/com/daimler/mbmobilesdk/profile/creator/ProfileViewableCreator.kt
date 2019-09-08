package com.daimler.mbmobilesdk.profile.creator

import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileViewable

interface ProfileViewableCreator {

    fun createViewable(profileField: ProfileField.AccountIdentifier): ProfileViewable

    fun createViewable(profileField: ProfileField.Email): ProfileViewable

    fun createViewable(profileField: ProfileField.Salutation): ProfileViewable
    fun createViewable(profileField: ProfileField.Title): ProfileViewable

    fun createViewable(profileField: ProfileField.FirstName): ProfileViewable
    fun createViewable(profileField: ProfileField.LastName): ProfileViewable

    fun createViewable(profileField: ProfileField.MarketCountryCode): ProfileViewable

    fun createViewable(profileField: ProfileField.LanguageCode): ProfileViewable

    fun createViewable(profileField: ProfileField.Birthday): ProfileViewable

    fun createViewable(profileField: ProfileField.MobilePhone): ProfileViewable
    fun createViewable(profileField: ProfileField.LandlinePhone): ProfileViewable

    fun createViewable(profileField: ProfileField.AddressCountryCode): ProfileViewable
    fun createViewable(profileField: ProfileField.AddressStreet): ProfileViewable
    fun createViewable(profileField: ProfileField.AddressStreetType): ProfileViewable
    fun createViewable(profileField: ProfileField.AddressHouseName): ProfileViewable
    fun createViewable(profileField: ProfileField.AddressState): ProfileViewable
    fun createViewable(profileField: ProfileField.AddressProvince): ProfileViewable
    fun createViewable(profileField: ProfileField.AddressFloorNumber): ProfileViewable
    fun createViewable(profileField: ProfileField.AddressDoorNumber): ProfileViewable
    fun createViewable(profileField: ProfileField.AddressHouseNumber): ProfileViewable
    fun createViewable(profileField: ProfileField.AddressZipCode): ProfileViewable
    fun createViewable(profileField: ProfileField.AddressCity): ProfileViewable
    fun createViewable(profileField: ProfileField.AddressLine1): ProfileViewable
    fun createViewable(profileField: ProfileField.AddressPostOfficeBox): ProfileViewable

    fun createViewable(profileField: ProfileField.TaxNumber): ProfileViewable

    fun createViewable(profileField: ProfileField.BodyHeight): ProfileViewable
    fun createViewable(profileField: ProfileField.PreAdjustment): ProfileViewable

    fun createViewable(profileField: ProfileField.ContactPerMail): ProfileViewable
    fun createViewable(profileField: ProfileField.ContactPerPhone): ProfileViewable
    fun createViewable(profileField: ProfileField.ContactPerSms): ProfileViewable
    fun createViewable(profileField: ProfileField.ContactPerLetter): ProfileViewable
    fun createViewable(profileField: ProfileField.Communication): ProfileViewable

    fun createViewable(profileField: ProfileField.MePin): ProfileViewable

    fun createViewable(profileField: ProfileField.Address): ProfileViewable
    fun createViewable(profileField: ProfileField.Name): ProfileViewable
    fun createViewable(profileField: ProfileField.AdaptionValues): ProfileViewable
}