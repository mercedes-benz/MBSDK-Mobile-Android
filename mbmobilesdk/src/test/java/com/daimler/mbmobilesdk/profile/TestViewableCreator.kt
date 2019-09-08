package com.daimler.mbmobilesdk.profile

import com.daimler.mbmobilesdk.profile.creator.ProfileViewableCreator
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileViewable

class TestViewableCreator : ProfileViewableCreator {

    override fun createViewable(profileField: ProfileField.AccountIdentifier): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.Email): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.Salutation): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.Title): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.FirstName): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.LastName): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.MarketCountryCode): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.LanguageCode): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.Birthday): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.MobilePhone): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.LandlinePhone): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.AddressCountryCode): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.AddressStreet): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.AddressStreetType): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.AddressHouseName): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.AddressState): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.AddressProvince): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.AddressFloorNumber): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.AddressDoorNumber): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.AddressHouseNumber): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.AddressZipCode): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.AddressCity): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.AddressLine1): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.AddressPostOfficeBox): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.TaxNumber): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.BodyHeight): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.ContactPerMail): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.ContactPerPhone): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.ContactPerSms): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.ContactPerLetter): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.MePin): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.Address): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.Name): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.PreAdjustment): ProfileViewable {
        return create(profileField)
    }

    override fun createViewable(profileField: ProfileField.AdaptionValues): ProfileViewable {
        return create(profileField)
    }

    private fun create(profileField: ProfileField): TestViewable =
        TestViewable.create(profileField)
}