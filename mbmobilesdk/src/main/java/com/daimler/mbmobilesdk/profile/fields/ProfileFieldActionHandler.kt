package com.daimler.mbmobilesdk.profile.fields

interface ProfileFieldActionHandler {

    fun addActionCallback(profileField: ProfileField.AccountIdentifier, viewable: ProfileViewable)

    fun addActionCallback(profileField: ProfileField.Email, viewable: ProfileViewable)

    fun addActionCallback(profileField: ProfileField.Salutation, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.Title, viewable: ProfileViewable)

    fun addActionCallback(profileField: ProfileField.FirstName, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.LastName, viewable: ProfileViewable)

    fun addActionCallback(profileField: ProfileField.MarketCountryCode, viewable: ProfileViewable)

    fun addActionCallback(profileField: ProfileField.LanguageCode, viewable: ProfileViewable)

    fun addActionCallback(profileField: ProfileField.Birthday, viewable: ProfileViewable)

    fun addActionCallback(profileField: ProfileField.MobilePhone, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.LandlinePhone, viewable: ProfileViewable)

    fun addActionCallback(profileField: ProfileField.AddressCountryCode, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.AddressStreet, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.AddressStreetType, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.AddressHouseName, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.AddressState, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.AddressProvince, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.AddressFloorNumber, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.AddressDoorNumber, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.AddressHouseNumber, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.AddressZipCode, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.AddressCity, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.AddressLine1, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.AddressPostOfficeBox, viewable: ProfileViewable)

    fun addActionCallback(profileField: ProfileField.TaxNumber, viewable: ProfileViewable)

    fun addActionCallback(profileField: ProfileField.BodyHeight, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.PreAdjustment, viewable: ProfileViewable)

    fun addActionCallback(profileField: ProfileField.ContactPerMail, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.ContactPerPhone, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.ContactPerSms, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.ContactPerLetter, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.Communication, viewable: ProfileViewable)

    fun addActionCallback(profileField: ProfileField.MePin, viewable: ProfileViewable)

    fun addActionCallback(profileField: ProfileField.Address, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.Name, viewable: ProfileViewable)
    fun addActionCallback(profileField: ProfileField.AdaptionValues, viewable: ProfileViewable)
}