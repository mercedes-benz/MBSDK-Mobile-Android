package com.daimler.mbmobilesdk.support.viewmodels

import android.app.Application
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.featuretoggling.*
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.Token
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.Before
import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daimler.mbmobilesdk.app.Stage
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbmobilesdk.app.format
import com.daimler.mbingresskit.common.User
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.TaskObject
import com.daimler.mbsupportkit.MBSupportKit
import com.daimler.mbsupportkit.common.PhoneNumber
import org.junit.After
import org.junit.Rule

class SupportPhoneViewModelTest {

    private val mockApplication: Application = mockk(relaxUnitFun = true)
    private val mockToken: Token = mockk(relaxUnitFun = true)
    private val mockUser: User = mockk(relaxUnitFun = true)

    private val userTaskObject = TaskObject<User, ResponseError<out RequestError>?>()
    private val cacPhoneTaskObject = TaskObject<PhoneNumber, ResponseError<out RequestError>?>()
    private val userLocale = UserLocale("DE", "de")
    private val cacPhoneNumber = PhoneNumber("1332443", "", "", "", "", "", "", "")

    private lateinit var testObject: SupportPhoneViewModel

    private var tokenTaskObject = TaskObject<Token, Throwable?>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        // Preparation for LaunchDarkly, Preferences, locale, stage
        mockkObject(MBMobileSDK)
        every { MBMobileSDK.featureToggleService().isToggleEnabled(FLAG_SUPPORT_TRANSFER_DATA_WITH_CALL) } returns true
        every { MBMobileSDK.supportSettings().cacCallDataSwitchEnabled.get() } returns false
        every { MBMobileSDK.supportSettings().cacPhoneNumber.get() } returns ""
        every { MBMobileSDK.supportSettings().ownPhoneNumber.get() } returns ""
        every { MBMobileSDK.selectedStage() } returns Stage.MOCK
        every { MBMobileSDK.userLocale() } returns userLocale

        // Preparation for MBIngressKit
        every { mockToken.jwtToken.plainToken } returns MY_TOKEN
        mockkObject(MBIngressKit)
        every { MBIngressKit.refreshTokenIfRequired() } answers { tokenTaskObject.futureTask() }
        every { MBIngressKit.userService().loadUser(MY_TOKEN) } answers { userTaskObject.futureTask() }

        mockkObject(MBSupportKit)
        every { MBSupportKit.cacPhoneService().fetchCacPhoneNumber(MY_TOKEN, userLocale.format()) } answers { cacPhoneTaskObject }
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun onInit_withUnsavedStateAndStageNonProd_shouldSetDefaultData() {
        initTestObjectWithDefaultMockData()

        // Check Buttons and Visabilities
        assertThat(testObject.cacAdditionalDataEnabled.value).isTrue()
        assertThat(testObject.switchStatus.value).isFalse()
        assertThat(testObject.additionalDataVisible.value).isFalse()
        assertThat(testObject.callButtonEnabled.value).isTrue()

        // Check LiveData Values
        assertThat(testObject.cacPhoneNumber.value).isEqualTo("0800")
        assertThat(testObject.ownPhoneNumber.value).isEmpty()
        assertThat(testObject.user).isEqualTo(mockUser)
    }

    @Test
    fun onInit_withUnsavedStateAndStageProd_shouldSetNumberFromService() {
        every { MBMobileSDK.selectedStage() } returns Stage.PROD

        initTestObjectWithDefaultMockData()

        assertThat(testObject.cacPhoneNumber.value).isEqualTo("1332443")
    }

    @Test
    fun onInit_withToggleCallDataFalse_shouldSetCacAdditionalDataEnabledToFalse() {
        every { MBMobileSDK.featureToggleService().isToggleEnabled(FLAG_SUPPORT_TRANSFER_DATA_WITH_CALL) } returns false

        initTestObjectWithDefaultMockData()

        assertThat(testObject.cacAdditionalDataEnabled.value).isFalse()
    }

    @Test
    fun onInit_withSavedStateSwitchIsTrueAndNumberIsInvalid_shouldSetCallButtonDisabled() {
        every { MBMobileSDK.supportSettings().cacCallDataSwitchEnabled.get() } returns true
        every { MBMobileSDK.supportSettings().cacPhoneNumber.get() } returns "+12313244"
        every { MBMobileSDK.supportSettings().ownPhoneNumber.get() } returns "+12"

        initTestObjectWithDefaultMockData()

        assertThat(testObject.cacAdditionalDataEnabled.value).isTrue()
        assertThat(testObject.switchStatus.value).isTrue()
        assertThat(testObject.additionalDataVisible.value).isTrue()
        assertThat(testObject.callButtonEnabled.value).isFalse()
    }

    @Test
    fun onInit_withSavedStateSwitchIsTrueAndNumberIsValid_shouldSetCallButtonEnabled() {
        every { MBMobileSDK.supportSettings().cacCallDataSwitchEnabled.get() } returns true
        every { MBMobileSDK.supportSettings().cacPhoneNumber.get() } returns "+12313244"
        every { MBMobileSDK.supportSettings().ownPhoneNumber.get() } returns "+4912352535"

        initTestObjectWithDefaultMockData()

        assertThat(testObject.cacAdditionalDataEnabled.value).isTrue()
        assertThat(testObject.switchStatus.value).isTrue()
        assertThat(testObject.additionalDataVisible.value).isTrue()
        assertThat(testObject.callButtonEnabled.value).isTrue()
    }

    @Test
    fun onSwitchStatusChanged_withStatusFalse_shouldSetCallButtonEnabledAndAdditionalDataFieldGone() {
        initTestObjectWithDefaultMockData()

        testObject.switchStatusChanged(false)

        assertThat(testObject.callButtonEnabled.value).isTrue()
        assertThat(testObject.additionalDataVisible.value).isFalse()
    }

    @Test
    fun onSwitchStatusChanged_withStatusTrueAndOwnNumberInvalid_shouldSetCallButtonDisabledAndAdditionalDataFieldVisible() {
        initTestObjectWithDefaultMockData()

        testObject.ownPhoneNumber.value = "+123"
        testObject.switchStatusChanged(true)

        assertThat(testObject.callButtonEnabled.value).isFalse()
        assertThat(testObject.additionalDataVisible.value).isTrue()
    }

    @Test
    fun onSwitchStatusChanged_withStatusTrueAndOwnNumberValid_shouldSetCallButtonEnabledAndAdditionalDataFieldVisible() {
        initTestObjectWithDefaultMockData()

        testObject.ownPhoneNumber.value = "+123424253"
        testObject.switchStatusChanged(true)

        assertThat(testObject.callButtonEnabled.value).isTrue()
        assertThat(testObject.additionalDataVisible.value).isTrue()
    }

    @Test
    fun onPhoneNumberChanged_OwnNumberInalid_shouldSetCallButtonDisabled() {
        every { MBMobileSDK.supportSettings().cacCallDataSwitchEnabled.get() } returns true

        initTestObjectWithDefaultMockData()

        testObject.onPhoneNumberChanged("123", 0, 0, 0)

        assertThat(testObject.callButtonEnabled.value).isFalse()
    }

    @Test
    fun onPhoneNumberChanged_OwnNumberValid_shouldSetCallButtonEnabled() {
        every { MBMobileSDK.supportSettings().cacCallDataSwitchEnabled.get() } returns true

        initTestObjectWithDefaultMockData()

        testObject.onPhoneNumberChanged("+123435352", 0, 0, 0)

        assertThat(testObject.callButtonEnabled.value).isTrue()
    }

    private fun initTestObjectWithDefaultMockData() {
        testObject = SupportPhoneViewModel(mockApplication)
        tokenTaskObject.complete(mockToken)
        tokenTaskObject = TaskObject()
        userTaskObject.complete(mockUser)
        tokenTaskObject.complete(mockToken)
        cacPhoneTaskObject.complete(cacPhoneNumber)
    }

    companion object {
        const val MY_TOKEN = "MY_TOKEN"
    }
}