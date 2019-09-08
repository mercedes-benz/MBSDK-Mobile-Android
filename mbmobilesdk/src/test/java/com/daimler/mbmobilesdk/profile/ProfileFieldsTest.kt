package com.daimler.mbmobilesdk.profile

import com.daimler.mbmobilesdk.profile.creator.ProfileViewableCreator
import com.daimler.mbmobilesdk.profile.fields.*
import com.daimler.mbingresskit.common.ProfileFieldUsage
import com.daimler.mbingresskit.common.ProfileSelectableValues
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProfileFieldsTest : ProfileFieldActionHandler {

    private var changeValue: String? = null
    private lateinit var fields: List<ProfileField>

    private val preparation = TestPreparation()
    private val creator: ProfileViewableCreator = TestViewableCreator()

    @Before
    fun setup() {
        changeValue = null
        fields = preparation.fields()
    }

    @Test
    fun testResolvablesCreated() {
        fields.forEach {
            val resolvable = it.create(creator) as TestViewable
            assertEquals(true, resolvable.changeValue == it::class.java.simpleName)
        }
    }

    @Test
    fun testCallbackSet() {
        fields.forEach {
            val resolvable = it.create(creator) as TestViewable
            it.addCallback(this, resolvable)
            assertEquals(true, resolvable.callback != null)
        }
    }

    @Test
    fun testCallbackCalled() {
        fields.forEach {
            val resolvable = it.create(creator) as TestViewable
            it.addCallback(this, resolvable)
            resolvable.changeValue()
            assertEquals(resolvable.changeValue, changeValue)
            changeValue = null
        }
    }

    /* ProfileFieldActionHandler */

    override fun addActionCallback(profileField: ProfileField.AccountIdentifier, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.Email, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.Salutation, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.Title, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.FirstName, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.LastName, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.MarketCountryCode, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.LanguageCode, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.Birthday, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.MobilePhone, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.LandlinePhone, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.AddressCountryCode, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.AddressStreet, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.AddressStreetType, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.AddressHouseName, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.AddressState, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.AddressProvince, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.AddressFloorNumber, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.AddressDoorNumber, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.AddressHouseNumber, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.AddressZipCode, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.AddressCity, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.AddressLine1, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.AddressPostOfficeBox, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.TaxNumber, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.BodyHeight, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.ContactPerMail, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.ContactPerPhone, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.ContactPerSms, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.ContactPerLetter, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.MePin, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.Address, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.Name, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.PreAdjustment, viewable: ProfileViewable) {
        resolve(viewable)
    }

    override fun addActionCallback(profileField: ProfileField.AdaptionValues, viewable: ProfileViewable) {
        resolve(viewable)
    }

    private fun resolve(viewable: ProfileViewable) {
        viewable.applyCallback(object : ProfileValueCallback {
            override fun onValueChanged(value: String?) {
                changeValue = value
            }
        })
    }

    private class TestPreparation : ProfileFieldPreparation {

        override fun fields(): List<ProfileField> {
            var counter = 0
            return listOf(
                ProfileField.Email(ProfileFieldUsage.MANDATORY, counter++, null),
                ProfileField.Salutation(ProfileFieldUsage.MANDATORY, counter++, emptySelectableValues()),
                ProfileField.Title(ProfileFieldUsage.MANDATORY, counter++, emptySelectableValues(), false),
                ProfileField.LanguageCode(ProfileFieldUsage.MANDATORY, counter++, emptySelectableValues(), false),
                ProfileField.MobilePhone(ProfileFieldUsage.MANDATORY, counter++, null),
                ProfileField.LandlinePhone(ProfileFieldUsage.MANDATORY, counter++, null),
                ProfileField.Name(ProfileFieldUsage.MANDATORY, counter++, listOf(
                    ProfileField.FirstName(ProfileFieldUsage.MANDATORY, counter++, null),
                    ProfileField.LastName(ProfileFieldUsage.MANDATORY, counter, null)
                ))
            )
        }

        private fun emptySelectableValues() =
            ProfileSelectableValues(false, null, emptyList())
    }
}