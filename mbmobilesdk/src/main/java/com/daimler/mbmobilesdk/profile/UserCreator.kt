package com.daimler.mbmobilesdk.profile

import com.daimler.mbingresskit.common.*

internal class UserCreator {

    val pendingUser: TempUser

    constructor(user: User) {
        pendingUser = createInitialUser(user)
    }

    constructor(identifier: String, isMail: Boolean, countryCode: String, languageCode: String) {
        pendingUser = createInitialUser(identifier, isMail, countryCode, languageCode)
    }

    fun user() =
        User(
            ciamId = "",
            userId = "",
            firstName = pendingUser.firstName,
            lastName = pendingUser.lastName,
            birthday = pendingUser.birthday,
            email = pendingUser.email,
            mobilePhone = pendingUser.mobilePhone,
            landlinePhone = pendingUser.landlinePhone,
            countryCode = pendingUser.countryCode,
            languageCode = pendingUser.languageCode,
            createdAt = "",
            updatedAt = "",
            pinStatus = pendingUser.pinStatus,
            address = address(),
            communicationPreference = communicationPreferences(),
            unitPreferences = unitPreferences(),
            accountIdentifier = pendingUser.accountIdentifier,
            title = pendingUser.title,
            salutationCode = pendingUser.salutation,
            taxNumber = pendingUser.taxNumber,
            bodyHeight = pendingUser.bodyHeight
        )

    fun address() =
        Address(
            street = pendingUser.address.street,
            houseNumber = pendingUser.address.houseNumber,
            zipCode = pendingUser.address.zipCode,
            city = pendingUser.address.city,
            countryCode = pendingUser.address.countryCode,
            state = pendingUser.address.state,
            province = pendingUser.address.province,
            streetType = pendingUser.address.streetType,
            houseName = pendingUser.address.houseName,
            floorNumber = pendingUser.address.floorNumber,
            doorNumber = pendingUser.address.doorNumber,
            addressLine1 = pendingUser.address.addressLine1,
            addressLine2 = null,
            addressLine3 = null,
            postOfficeBox = pendingUser.address.postOfficeBox
        )

    fun unitPreferences() =
        UnitPreferences(
            UnitPreferences.ClockHoursUnits.TYPE_24H,
            UnitPreferences.SpeedDistanceUnits.KILOMETERS,
            UnitPreferences.ConsumptionCoUnits.LITERS_PER_100_KILOMETERS,
            UnitPreferences.ConsumptionEvUnits.KILOWATT_HOURS_PER_100_KILOMETERS,
            UnitPreferences.ConsumptionGasUnits.KILOGRAM_PER_100_KILOMETERS,
            UnitPreferences.TirePressureUnits.KILOPASCAL,
            UnitPreferences.TemperatureUnits.CELSIUS
        )

    fun communicationPreferences() =
        CommunicationPreference(
            contactByMail = pendingUser.communicationPreference.contactByMail,
            contactByPhone = pendingUser.communicationPreference.contactByPhone,
            contactBySms = pendingUser.communicationPreference.contactBySms,
            contactByLetter = pendingUser.communicationPreference.contactByLetter
        )

    fun updateCommunicationPreferences(
        contactByMail: Boolean,
        contactByPhone: Boolean,
        contactBySms: Boolean,
        contactByLetter: Boolean
    ) {
        pendingUser.communicationPreference.contactByMail = contactByMail
        pendingUser.communicationPreference.contactByPhone = contactByPhone
        pendingUser.communicationPreference.contactBySms = contactBySms
        pendingUser.communicationPreference.contactByLetter = contactByLetter
    }

    private fun createInitialUser(
        identifier: String,
        isMail: Boolean,
        countryCode: String,
        languageCode: String
    ) =
        TempUser(
            firstName = "",
            lastName = "",
            birthday = "",
            email = if (isMail) identifier else "",
            mobilePhone = if (!isMail) identifier else "",
            landlinePhone = "",
            countryCode = countryCode,
            languageCode = languageCode,
            title = "",
            salutation = "",
            address = initialAddress(countryCode),
            accountIdentifier = AccountIdentifier.UNKNOWN,
            communicationPreference = createCommunicationPreference(isMail, !isMail),
            taxNumber = "",
            bodyHeight = null,
            pinStatus = UserPinStatus.NOT_SET
        )

    private fun createInitialUser(user: User) =
        TempUser(
            firstName = user.firstName,
            lastName = user.lastName,
            birthday = user.birthday,
            email = user.email,
            mobilePhone = user.mobilePhone,
            landlinePhone = user.landlinePhone,
            countryCode = user.countryCode,
            languageCode = user.languageCode,
            title = user.title,
            salutation = user.salutationCode,
            address = user.address?.let { initialAddress(it) } ?: initialAddress(user.countryCode),
            accountIdentifier = AccountIdentifier.UNKNOWN,
            communicationPreference = createCommunicationPreference(user.communicationPreference),
            taxNumber = user.taxNumber,
            bodyHeight = user.bodyHeight,
            pinStatus = user.pinStatus
        )

    private fun createCommunicationPreference(mail: Boolean, phone: Boolean) =
        TempCommunicationPreference(
            contactByPhone = phone,
            contactByLetter = false,
            contactByMail = mail,
            contactBySms = false
        )

    private fun createCommunicationPreference(communicationPreference: CommunicationPreference) =
        TempCommunicationPreference(
            contactByPhone = communicationPreference.contactByPhone,
            contactBySms = communicationPreference.contactBySms,
            contactByMail = communicationPreference.contactByMail,
            contactByLetter = communicationPreference.contactByLetter
        )

    private fun initialAddress(countryCode: String) = TempAddress(null, null,
        null, null, countryCode, null, null, null,
        null, null, null, null, null)

    private fun initialAddress(address: Address) =
        TempAddress(
            street = address.street,
            houseNumber = address.houseNumber,
            zipCode = address.zipCode,
            city = address.city,
            countryCode = address.countryCode,
            state = address.state,
            province = address.province,
            streetType = address.streetType,
            houseName = address.houseName,
            floorNumber = address.floorNumber,
            doorNumber = address.doorNumber,
            addressLine1 = address.addressLine1,
            postOfficeBox = address.postOfficeBox
        )

    data class TempUser(
        var firstName: String,
        var lastName: String,
        var birthday: String,
        var email: String,
        var mobilePhone: String,
        var landlinePhone: String,
        var countryCode: String,
        var languageCode: String,
        var title: String,
        var salutation: String,
        var address: TempAddress,
        var accountIdentifier: AccountIdentifier,
        val communicationPreference: TempCommunicationPreference,
        var taxNumber: String,
        var bodyHeight: UserBodyHeight?,
        var pinStatus: UserPinStatus
    ) {
        fun updateBodyHeight(bodyHeight: String?, preAdjustment: Boolean?) {
            val height = bodyHeight?.let {
                try {
                    // TODO units? conversions?
                    it.toInt()
                } catch (e: Exception) {
                    null
                }
            }
            val oldHeight = this.bodyHeight?.bodyHeight ?: 0
            val oldPreAdjustment = this.bodyHeight?.preAdjustment ?: false
            this.bodyHeight = this.bodyHeight?.copy(
                bodyHeight = height ?: oldHeight,
                preAdjustment = preAdjustment ?: oldPreAdjustment
            ) ?: UserBodyHeight(
                height ?: oldHeight,
                preAdjustment ?: oldPreAdjustment
            )
        }
    }

    data class TempAddress(
        var street: String?,
        var houseNumber: String?,
        var zipCode: String?,
        var city: String?,
        var countryCode: String?,
        var state: String?,
        var province: String?,
        var streetType: String?,
        var houseName: String?,
        var floorNumber: String?,
        var doorNumber: String?,
        var addressLine1: String?,
        var postOfficeBox: String?
    )

    data class TempCommunicationPreference(
        var contactByMail: Boolean,
        var contactByPhone: Boolean,
        var contactByLetter: Boolean,
        var contactBySms: Boolean
    )
}