package com.daimler.mbingresskit.implementation.network

import com.daimler.mbingresskit.common.AccountIdentifier
import com.daimler.mbingresskit.common.Address
import com.daimler.mbingresskit.common.CommunicationPreference
import com.daimler.mbingresskit.common.CountryInstance
import com.daimler.mbingresskit.common.ProfileFieldOwnerType
import com.daimler.mbingresskit.common.ProfileFieldRelationshipType
import com.daimler.mbingresskit.common.ProfileFieldType
import com.daimler.mbingresskit.common.ProfileFieldUsage
import com.daimler.mbingresskit.common.RegistrationUserAgreementUpdate
import com.daimler.mbingresskit.common.UnitPreferences
import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.common.UserAdaptionValues
import com.daimler.mbingresskit.common.UserBiometricState
import com.daimler.mbingresskit.common.UserBodyHeight
import com.daimler.mbingresskit.common.UserPinStatus
import com.daimler.mbingresskit.implementation.network.model.adaptionvalues.ApiUserAdaptionValues
import com.daimler.mbingresskit.implementation.network.model.adaptionvalues.toApiUserAdaptionValues
import com.daimler.mbingresskit.implementation.network.model.adaptionvalues.toUserAdaptionValues
import com.daimler.mbingresskit.implementation.network.model.adaptionvalues.toUserBodyHeight
import com.daimler.mbingresskit.implementation.network.model.biometric.UserBiometricApiState
import com.daimler.mbingresskit.implementation.network.model.country.ApiCountryInstance
import com.daimler.mbingresskit.implementation.network.model.country.CountryResponse
import com.daimler.mbingresskit.implementation.network.model.country.LocaleResponse
import com.daimler.mbingresskit.implementation.network.model.country.toCountry
import com.daimler.mbingresskit.implementation.network.model.profilefields.CustomerDataFieldResponse
import com.daimler.mbingresskit.implementation.network.model.profilefields.FieldOwnerTypeResponse
import com.daimler.mbingresskit.implementation.network.model.profilefields.ProfileDataFieldRelationshipTypeResponse
import com.daimler.mbingresskit.implementation.network.model.profilefields.ProfileFieldUsageResponse
import com.daimler.mbingresskit.implementation.network.model.profilefields.ProfileFieldValidationResponse
import com.daimler.mbingresskit.implementation.network.model.profilefields.toCustomerDataField
import com.daimler.mbingresskit.implementation.network.model.profilefields.toProfileFieldValidation
import com.daimler.mbingresskit.implementation.network.model.unitpreferences.toUserUnitPreferences
import com.daimler.mbingresskit.implementation.network.model.user.ApiAccountIdentifier
import com.daimler.mbingresskit.implementation.network.model.user.create.toCreateUserRequest
import com.daimler.mbingresskit.implementation.network.model.user.fetch.AddressResponse
import com.daimler.mbingresskit.implementation.network.model.user.fetch.UserPinStatusResponse
import com.daimler.mbingresskit.implementation.network.model.user.fetch.UserTokenResponse
import com.daimler.mbingresskit.implementation.network.model.user.fetch.toAddress
import com.daimler.mbingresskit.implementation.network.model.user.fetch.toUser
import com.daimler.mbingresskit.implementation.network.model.user.toApiAgreementUpdate
import com.daimler.mbingresskit.implementation.network.model.user.toUserCommunicationPreference
import com.daimler.mbingresskit.implementation.network.model.user.update.toUpdateUserRequest
import com.daimler.mbingresskit.implementation.network.model.user.update.toUpdateUserRequestAddress
import io.mockk.mockk
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@ExtendWith(SoftAssertionsExtension::class)
internal class UserApiConverterTest {

    @Test
    fun `convert User to CreateUserRequest`(softly: SoftAssertions) {
        val testUser = getTestUser()
        val agreementUpdates = getTestAgreementUpdates()
        val createUserRequest = testUser.toCreateUserRequest(true, agreementUpdates, SAMPLE_NONCE)
        softly.assertThat(createUserRequest.firstName).isEqualTo(testUser.firstName)
        softly.assertThat(createUserRequest.lastName1).isEqualTo(testUser.lastName)
        softly.assertThat(createUserRequest.birthday).isEqualTo(testUser.birthday)
        softly.assertThat(createUserRequest.email).isEqualTo(testUser.email)
        softly.assertThat(createUserRequest.mobilePhone).isEqualTo(testUser.mobilePhone)
        softly.assertThat(createUserRequest.landlinePhone).isEqualTo(testUser.landlinePhone)
        softly.assertThat(createUserRequest.preferredLanguageCode).isEqualTo(testUser.languageCode)
        softly.assertThat(createUserRequest.title).isEqualTo(testUser.title)
        softly.assertThat(createUserRequest.salutationCode).isEqualTo(testUser.salutationCode)
        softly.assertThat(createUserRequest.useEmailAsUsername).isEqualTo(true)
        softly.assertThat(createUserRequest.toasConsents).isNotNull
        softly.assertThat(createUserRequest.toasConsents).isEqualTo(agreementUpdates.map { it.toApiAgreementUpdate() })
        softly.assertThat(createUserRequest.nonce).isEqualTo(SAMPLE_NONCE)
    }

    @Test
    fun `convert User with nulls to CreateUserRequest`(softly: SoftAssertions) {
        val testUser = getTestUser().copy(
            birthday = null,
            email = null,
            mobilePhone = null,
            landlinePhone = null,
            title = null,
            taxNumber = null,
            createdAt = "",
            updatedAt = ""
        )

        val createUserRequest = testUser.toCreateUserRequest(true, null, SAMPLE_NONCE)
        softly.assertThat(createUserRequest.birthday).isEqualTo(null)
        softly.assertThat(createUserRequest.email).isEqualTo(null)
        softly.assertThat(createUserRequest.mobilePhone).isEqualTo(null)
        softly.assertThat(createUserRequest.landlinePhone).isEqualTo(null)
        softly.assertThat(createUserRequest.title).isEqualTo(null)
        softly.assertThat(createUserRequest.taxNumber).isEqualTo(null)
        softly.assertThat(createUserRequest.toasConsents).isNull()
        softly.assertThat(createUserRequest.nonce).isEqualTo(SAMPLE_NONCE)
    }

    @Test
    fun `convert User with empty strings to CreateUserRequest`(softly: SoftAssertions) {
        val testUser = getTestUser().copy(
            birthday = "",
            email = "",
            mobilePhone = "",
            landlinePhone = "",
            title = "",
            salutationCode = "",
            taxNumber = ""
        )
        val request = testUser.toCreateUserRequest(true, null, null)
        softly.assertThat(request.birthday).isNull()
        softly.assertThat(request.email).isNull()
        softly.assertThat(request.mobilePhone).isNull()
        softly.assertThat(request.landlinePhone).isNull()
        softly.assertThat(request.title).isNull()
        softly.assertThat(request.salutationCode).isNull()
        softly.assertThat(request.taxNumber).isNull()
        softly.assertThat(request.nonce).isNull()
    }

    @Test
    fun `convert Address to UpdateUserRequestAddress`(softly: SoftAssertions) {
        val address = Address(
            countryCode = "countryCode",
            state = "state",
            province = "province",
            street = "street",
            houseNumber = "houseNumber",
            zipCode = "zipCode",
            city = "city",
            streetType = "streetType",
            houseName = "houseName",
            floorNumber = "floorNumber",
            doorNumber = "doorNumber",
            addressLine1 = "addressLine1",
            addressLine2 = "addressLine2",
            addressLine3 = "addressLine3",
            postOfficeBox = "postOfficeBox"
        )

        val updateUserRequestAddress = address.toUpdateUserRequestAddress()
        softly.assertThat(updateUserRequestAddress.countryCode).isEqualTo(address.countryCode)
        softly.assertThat(updateUserRequestAddress.state).isEqualTo(address.state)
        softly.assertThat(updateUserRequestAddress.province).isEqualTo(address.province)
        softly.assertThat(updateUserRequestAddress.street).isEqualTo(address.street)
        softly.assertThat(updateUserRequestAddress.houseNumber).isEqualTo(address.houseNumber)
        softly.assertThat(updateUserRequestAddress.zipCode).isEqualTo(address.zipCode)
        softly.assertThat(updateUserRequestAddress.city).isEqualTo(address.city)
        softly.assertThat(updateUserRequestAddress.streetType).isEqualTo(address.streetType)
        softly.assertThat(updateUserRequestAddress.houseName).isEqualTo(address.houseName)
        softly.assertThat(updateUserRequestAddress.floorNumber).isEqualTo(address.floorNumber)
        softly.assertThat(updateUserRequestAddress.doorNumber).isEqualTo(address.doorNumber)
        softly.assertThat(updateUserRequestAddress.addressLine1).isEqualTo(address.addressLine1)
        softly.assertThat(updateUserRequestAddress.addressLine2).isEqualTo(address.addressLine2)
        softly.assertThat(updateUserRequestAddress.addressLine3).isEqualTo(address.addressLine3)
        softly.assertThat(updateUserRequestAddress.postOfficeBox).isEqualTo(address.postOfficeBox)
    }

    @Test
    fun `convert CommunicationPreference to UserCommunicationPreference`(softly: SoftAssertions) {
        val communicationPreference = CommunicationPreference.initialState()
        val userCommunicationPreference = communicationPreference.toUserCommunicationPreference()
        softly.assertThat(userCommunicationPreference.contactByPhone).isEqualTo(communicationPreference.contactByPhone)
        softly.assertThat(userCommunicationPreference.contactByLetter).isEqualTo(communicationPreference.contactByLetter)
        softly.assertThat(userCommunicationPreference.contactByMail).isEqualTo(communicationPreference.contactByMail)
        softly.assertThat(userCommunicationPreference.contactBySms).isEqualTo(communicationPreference.contactBySms)
    }

    @Test
    fun `convert User to UpdateUserRequest`(softly: SoftAssertions) {
        val testUser = getTestUser()
        val updateUserRequest = testUser.toUpdateUserRequest()
        softly.assertThat(updateUserRequest.firstName).isEqualTo(testUser.firstName)
        softly.assertThat(updateUserRequest.lastName1).isEqualTo(testUser.lastName)
        softly.assertThat(updateUserRequest.lastName2).isEqualTo(null)
        softly.assertThat(updateUserRequest.birthday).isEqualTo(testUser.birthday)
        softly.assertThat(updateUserRequest.email).isEqualTo(testUser.email)
        softly.assertThat(updateUserRequest.mobilePhone).isEqualTo(testUser.mobilePhone)
        softly.assertThat(updateUserRequest.landlinePhone).isEqualTo(testUser.landlinePhone)
        softly.assertThat(updateUserRequest.accountCountryCode).isEqualTo(testUser.countryCode)
        softly.assertThat(updateUserRequest.preferredLanguageCode).isEqualTo(testUser.languageCode)
        softly.assertThat(updateUserRequest.title).isEqualTo(testUser.title)
        softly.assertThat(updateUserRequest.salutationCode).isEqualTo(testUser.salutationCode)
        softly.assertThat(updateUserRequest.taxNumber).isEqualTo(testUser.taxNumber)
    }

    @Test
    fun `convert UnitPreferences to UserUnitPreferences`(softly: SoftAssertions) {
        val unitPreferences = UnitPreferences.defaultUnitPreferences()
        val userUnitPreferences = unitPreferences.toUserUnitPreferences()
        softly.assertThat(userUnitPreferences.clockHours).isEqualTo(unitPreferences.clockHours)
        softly.assertThat(userUnitPreferences.speedDistance).isEqualTo(unitPreferences.speedDistance)
        softly.assertThat(userUnitPreferences.consumptionCo).isEqualTo(unitPreferences.consumptionCo)
        softly.assertThat(userUnitPreferences.consumptionEv).isEqualTo(unitPreferences.consumptionEv)
        softly.assertThat(userUnitPreferences.consumptionGas).isEqualTo(unitPreferences.consumptionGas)
        softly.assertThat(userUnitPreferences.tirePressure).isEqualTo(unitPreferences.tirePressure)
        softly.assertThat(userUnitPreferences.temperature).isEqualTo(unitPreferences.temperature)
    }

    @Test
    fun `convert ApiUserAdaptionValues to UserBodyHeight`(softly: SoftAssertions) {
        val apiUserAdaptionValues = ApiUserAdaptionValues(123, true, null)
        val userBodyHeight = apiUserAdaptionValues.toUserBodyHeight()
        softly.assertThat(userBodyHeight.bodyHeight).isEqualTo(apiUserAdaptionValues.bodyHeight)
        softly.assertThat(userBodyHeight.preAdjustment).isEqualTo(apiUserAdaptionValues.preAdjustment)
    }

    @Test
    fun `convert ApiUserAdaptionValues to UserAdaptionValues`(softly: SoftAssertions) {
        val apiUserAdaptionValues = ApiUserAdaptionValues(180, true, "alias")
        val userAdaptionValues = apiUserAdaptionValues.toUserAdaptionValues()
        softly.assertThat(userAdaptionValues.bodyHeight?.bodyHeight).isEqualTo(apiUserAdaptionValues.bodyHeight)
        softly.assertThat(userAdaptionValues.bodyHeight?.preAdjustment).isEqualTo(apiUserAdaptionValues.preAdjustment)
        softly.assertThat(userAdaptionValues.alias).isEqualTo(apiUserAdaptionValues.alias)
    }

    @Test
    fun `convert UserAdaptionValues to ApiUserAdaptionValues`(softly: SoftAssertions) {
        val userAdaptionValues = UserAdaptionValues(UserBodyHeight(180, true), "alias")
        val apiUserAdaptionValues = userAdaptionValues.toApiUserAdaptionValues()
        softly.assertThat(apiUserAdaptionValues.bodyHeight).isEqualTo(userAdaptionValues.bodyHeight?.bodyHeight)
        softly.assertThat(apiUserAdaptionValues.preAdjustment).isEqualTo(userAdaptionValues.bodyHeight?.preAdjustment)
        softly.assertThat(apiUserAdaptionValues.alias).isEqualTo(userAdaptionValues.alias)
    }

    @Test
    fun `convert CustomerDataFieldResponse to CustomerDataField`(softly: SoftAssertions) {
        val customerDataFieldResponse = CustomerDataFieldResponse(
            null,
            123,
            null,
            mockk(relaxed = true),
            mockk(relaxed = true)
        )

        val customerDataField = customerDataFieldResponse.toCustomerDataField()
        softly.assertThat(customerDataField.fieldType).isEqualTo(ProfileFieldType.UNKNOWN)
        softly.assertThat(customerDataField.sequenceOrder).isEqualTo(customerDataFieldResponse.sequenceOrder)
        softly.assertThat(customerDataField.fieldUsage).isEqualTo(ProfileFieldUsage.UNKNOWN)
    }

    @Test
    fun `convert ProfileFieldValidationResponse to ProfileFieldValidation`(softly: SoftAssertions) {
        val profileFieldValidationResponse = ProfileFieldValidationResponse(0, 2, "abc")
        val profileFieldValidation = profileFieldValidationResponse.toProfileFieldValidation()
        softly.assertThat(profileFieldValidation.minLength).isEqualTo(profileFieldValidationResponse.minLength)
        softly.assertThat(profileFieldValidation.maxLength).isEqualTo(profileFieldValidationResponse.maxLength)
        softly.assertThat(profileFieldValidation.regularExpression).isEqualTo(profileFieldValidationResponse.regularExpression)
    }

    @Test
    fun `convert UserTokenResponse to User`(softly: SoftAssertions) {
        val userTokenResponse = UserTokenResponse(
            ciamId = JSON_FIELD_VALUE,
            userId = JSON_FIELD_VALUE,
            firstName = JSON_FIELD_VALUE,
            lastName1 = JSON_FIELD_VALUE,
            lastName2 = JSON_FIELD_VALUE,
            birthday = JSON_FIELD_VALUE,
            email = JSON_FIELD_VALUE,
            mobilePhone = JSON_FIELD_VALUE,
            landlinePhone = JSON_FIELD_VALUE,
            accountCountryCode = JSON_FIELD_VALUE,
            preferredLanguageCode = JSON_FIELD_VALUE,
            createdAt = JSON_FIELD_VALUE,
            updatedAt = JSON_FIELD_VALUE,
            address = mockk(relaxed = true),
            userPinStatus = UserPinStatusResponse.UNKNOWN,
            communicationPreference = mockk(relaxed = true),
            unitPreferences = null,
            accountIdentifier = ApiAccountIdentifier.EMAIL_AND_MOBILE,
            title = JSON_FIELD_VALUE,
            salutationCode = JSON_FIELD_VALUE,
            taxNumber = JSON_FIELD_VALUE,
            userAdaptionValues = mockk(relaxed = true),
            accountVerified = true,
            isEmailVerified = true,
            isMobileVerified = true
        )

        val user = userTokenResponse.toUser()
        softly.assertThat(user.ciamId).isEqualTo(userTokenResponse.ciamId)
        softly.assertThat(user.userId).isEqualTo(userTokenResponse.userId)
        softly.assertThat(user.firstName).isEqualTo(userTokenResponse.firstName)
        softly.assertThat(user.lastName).isEqualTo(userTokenResponse.lastName1)
        softly.assertThat(user.birthday).isEqualTo(userTokenResponse.birthday)
        softly.assertThat(user.email).isEqualTo(userTokenResponse.email)
        softly.assertThat(user.mobilePhone).isEqualTo(userTokenResponse.mobilePhone)
        softly.assertThat(user.landlinePhone).isEqualTo(userTokenResponse.landlinePhone)
        softly.assertThat(user.countryCode).isEqualTo(userTokenResponse.accountCountryCode)
        softly.assertThat(user.languageCode).isEqualTo(userTokenResponse.preferredLanguageCode)
        softly.assertThat(user.createdAt).isEqualTo(userTokenResponse.createdAt)
        softly.assertThat(user.updatedAt).isEqualTo(userTokenResponse.updatedAt)
        softly.assertThat(user.title).isEqualTo(userTokenResponse.title)
        softly.assertThat(user.salutationCode).isEqualTo(userTokenResponse.salutationCode)
        softly.assertThat(user.accountVerified).isEqualTo(userTokenResponse.accountVerified)
        softly.assertThat(user.emailVerified).isEqualTo(userTokenResponse.isEmailVerified)
        softly.assertThat(user.mobileVerified).isEqualTo(userTokenResponse.isMobileVerified)
    }

    @Test
    fun `convert UserTokenResponseAddress to Address`(softly: SoftAssertions) {
        val userTokenAddress = AddressResponse(
            countryCode = JSON_FIELD_VALUE,
            state = JSON_FIELD_VALUE,
            province = JSON_FIELD_VALUE,
            street = JSON_FIELD_VALUE,
            houseNumber = JSON_FIELD_VALUE,
            zipCode = JSON_FIELD_VALUE,
            city = JSON_FIELD_VALUE,
            streetType = JSON_FIELD_VALUE,
            houseName = JSON_FIELD_VALUE,
            floorNumber = JSON_FIELD_VALUE,
            doorNumber = JSON_FIELD_VALUE,
            addressLine1 = JSON_FIELD_VALUE,
            addressLine2 = JSON_FIELD_VALUE,
            addressLine3 = JSON_FIELD_VALUE,
            postOfficeBox = JSON_FIELD_VALUE
        )

        val address = userTokenAddress.toAddress()

        softly.assertThat(address.countryCode).isEqualTo(userTokenAddress.countryCode)
        softly.assertThat(address.state).isEqualTo(userTokenAddress.state)
        softly.assertThat(address.province).isEqualTo(userTokenAddress.province)
        softly.assertThat(address.street).isEqualTo(userTokenAddress.street)
        softly.assertThat(address.houseNumber).isEqualTo(userTokenAddress.houseNumber)
        softly.assertThat(address.zipCode).isEqualTo(userTokenAddress.zipCode)
        softly.assertThat(address.city).isEqualTo(userTokenAddress.city)
        softly.assertThat(address.streetType).isEqualTo(userTokenAddress.streetType)
        softly.assertThat(address.houseName).isEqualTo(userTokenAddress.houseName)
        softly.assertThat(address.floorNumber).isEqualTo(userTokenAddress.floorNumber)
        softly.assertThat(address.doorNumber).isEqualTo(userTokenAddress.doorNumber)
        softly.assertThat(address.addressLine1).isEqualTo(userTokenAddress.addressLine1)
        softly.assertThat(address.addressLine2).isEqualTo(userTokenAddress.addressLine2)
        softly.assertThat(address.addressLine3).isEqualTo(userTokenAddress.addressLine3)
        softly.assertThat(address.postOfficeBox).isEqualTo(userTokenAddress.postOfficeBox)
    }

    @Test
    fun `convert CountryResponse to Country`(softly: SoftAssertions) {
        val countryLocale = mockk<LocaleResponse>(relaxed = true)
        val countryResponse = CountryResponse(
            countryCode = JSON_FIELD_VALUE,
            countryName = JSON_FIELD_VALUE,
            instance = ApiCountryInstance.ECE,
            legalRegion = JSON_FIELD_VALUE,
            defaultLocale = JSON_FIELD_VALUE,
            natconCountry = true,
            connectCountry = true,
            locales = listOf(countryLocale),
            availability = false
        )

        val country = countryResponse.toCountry()

        softly.assertThat(country.countryCode).isEqualTo(country.countryCode)
        softly.assertThat(country.countryName).isEqualTo(country.countryName)
        softly.assertThat(country.legalRegion).isEqualTo(country.legalRegion)
        softly.assertThat(country.defaultLocale).isEqualTo(country.defaultLocale)
        softly.assertThat(country.natconCountry).isEqualTo(country.natconCountry)
        softly.assertThat(country.connectCountry).isEqualTo(country.connectCountry)
        softly.assertThat(country.availability).isEqualTo(country.availability)
    }

    @ParameterizedTest
    @EnumSource(ApiCountryInstance::class)
    fun `Mapping from ApiCountryInstance to CountryInstance enum`(apiCountryInstance: ApiCountryInstance) {
        val countryInstance = CountryInstance.forName(apiCountryInstance.name)
        assertEquals(apiCountryInstance.name, countryInstance.name)
    }

    @ParameterizedTest
    @EnumSource(UserBiometricState::class)
    fun `Mapping from UserBiometricState to UserBiometricApiState enum`(userBiometricState: UserBiometricState) {
        val userBiometricApiState = UserBiometricApiState.forName(userBiometricState.name)
        assertEquals(userBiometricState.name, userBiometricApiState.name)
    }

    @ParameterizedTest
    @EnumSource(ApiAccountIdentifier::class)
    fun `Mapping from AccountIdentifier to ApiAccountIdentifier enum and vice-versa`(apiAccountIdentifier: ApiAccountIdentifier) {
        val accountIdentifier = AccountIdentifier.forName(apiAccountIdentifier.name)
        val sameApiAccountIdentifier = ApiAccountIdentifier.forName(accountIdentifier.name)
        val sameAccountIdentifier = AccountIdentifier.forName(sameApiAccountIdentifier?.name ?: "")
        assertEquals(sameAccountIdentifier, accountIdentifier)
    }

    @ParameterizedTest
    @EnumSource(ProfileFieldUsageResponse::class)
    fun `Mapping from ProfileFieldUsageResponse to ProfileFieldUsage enum`(
        profileFieldUsageResponse: ProfileFieldUsageResponse
    ) {
        val profileFieldUsage = ProfileFieldUsage.forName(profileFieldUsageResponse.name)
        assertEquals(profileFieldUsage.name, profileFieldUsageResponse.name)
    }

    @ParameterizedTest
    @EnumSource(UserPinStatusResponse::class)
    fun `Mapping from UserPinStatusResponse to UserPinStatus enum`(userPinStatusResponse: UserPinStatusResponse) {
        val userPinStatus = UserPinStatus.forName(userPinStatusResponse.name)
        assertEquals(userPinStatusResponse.name, userPinStatus.name)
    }

    @ParameterizedTest
    @EnumSource(FieldOwnerTypeResponse::class)
    fun `Mapping from FieldOwnerTypeResponse to ProfileFieldOwnerType enum`(fieldOwnerTypeResponse: FieldOwnerTypeResponse) {
        val profileFieldOwnerType = ProfileFieldOwnerType.forName(fieldOwnerTypeResponse.name)
        assertEquals(fieldOwnerTypeResponse.name, profileFieldOwnerType.name)
    }

    @ParameterizedTest
    @EnumSource(ProfileDataFieldRelationshipTypeResponse::class)
    fun `Mapping from ProfileDataFieldRelationshipTypeResponse to ProfileFieldRelationshipType enum`(
        profileDataFieldRelationshipTypeResponse: ProfileDataFieldRelationshipTypeResponse
    ) {
        val profileFieldOwnerType = ProfileFieldRelationshipType.forName(profileDataFieldRelationshipTypeResponse.name)
        assertEquals(profileDataFieldRelationshipTypeResponse.name, profileFieldOwnerType.name)
    }

    private fun getTestUser(): User {
        val address = mockk<Address>(relaxed = true)
        val bodyHeight = mockk<UserBodyHeight>(relaxed = true)
        val communicationPreference = mockk<CommunicationPreference>(relaxed = true)
        val unitPreference = mockk<UnitPreferences>(relaxed = true)
        val adaptionValues = mockk<UserAdaptionValues>(relaxed = true)
        return User(
            ciamId = "MyCiamId",
            userId = "UserId",
            firstName = "Max",
            lastName = "Mustermann",
            birthday = "02.02.2020",
            email = "maxmustermann@example.de",
            mobilePhone = "+49 1234567",
            landlinePhone = "01234 56789",
            countryCode = "DE",
            languageCode = "de",
            address = address,
            title = "Dr.",
            salutationCode = "Sir",
            taxNumber = "123 456 789 11",
            bodyHeight = bodyHeight,
            accountVerified = true,
            emailVerified = true,
            mobileVerified = true,
            createdAt = "1.1.1991",
            updatedAt = "1.1.1992",
            communicationPreference = communicationPreference,
            unitPreferences = unitPreference,
            adaptionValues = adaptionValues
        )
    }

    private fun getTestAgreementUpdates(): List<RegistrationUserAgreementUpdate> =
        listOf(
            RegistrationUserAgreementUpdate("id1", "1", "de-DE", true),
            RegistrationUserAgreementUpdate("id2", "2", "de-DE", false)
        )

    companion object {
        private const val JSON_FIELD_VALUE = "String"
        private const val SAMPLE_NONCE = "abc-def-ghij"
    }
}
