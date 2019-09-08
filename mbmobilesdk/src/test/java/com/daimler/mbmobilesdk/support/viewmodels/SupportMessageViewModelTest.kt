package com.daimler.mbmobilesdk.support.viewmodels

import android.app.Application
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.app.Stage
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbmobilesdk.support.SupportUtils
import com.daimler.mbmobilesdk.utils.extensions.toLocalDateTimeString
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.common.User
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.vehicle.*
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.TaskObject
import com.daimler.mbsupportkit.MBSupportKit
import com.daimler.mbsupportkit.common.MessageAnswer
import com.daimler.mbsupportkit.common.MessageAnswerType
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class SupportMessageViewModelTest {

    // Mocks
    private val mockApplication: Application = mockk(relaxUnitFun = true)
    private val mockToken: Token = mockk(relaxUnitFun = true)
    private val mockUser: User = mockk(relaxUnitFun = true)
    private val mockVehicle: VehicleInfo = mockk(relaxUnitFun = true)
    private val mockBitmap: Bitmap = mockk(relaxUnitFun = true)

    // Tasks
    private val vehiclesTaskObject = TaskObject<Vehicles, ResponseError<out RequestError>?>()
    private val userTaskObject = TaskObject<User, ResponseError<out RequestError>?>()
    private var tokenTaskObject = TaskObject<Token, Throwable?>()
    private var answerTaskObject = TaskObject<MessageAnswer, ResponseError<out RequestError>?>()

    // Results
    private var vehicles: Vehicles = initVehicles(2)

    // Variables
    private lateinit var testObject: SupportMessageViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        every { mockToken.jwtToken.plainToken } returns MY_TOKEN
        every { mockApplication.resources } returns Resources.getSystem()
        every { mockApplication.getString(R.string.default_error_msg) } returns GENERAL_ERROR

        // Preparation for LaunchDarkly, Preferences, locale, stage
        mockkObject(MBMobileSDK)
        every { MBMobileSDK.selectedStage() } returns Stage.MOCK
        every { MBMobileSDK.userLocale() } returns USER_LOCALE
        every { MBMobileSDK.appSessionId() } returns TEST_SESSIONID

        // Preparation for MBCarKit
        mockkObject(MBCarKit)
        every { MBCarKit.vehicleService().fetchVehicles(MY_TOKEN) } answers { vehiclesTaskObject.futureTask() }

        // Preparation for MBIngressKit
        mockkObject(MBIngressKit)
        every { MBIngressKit.refreshTokenIfRequired() } answers { tokenTaskObject.futureTask() }
        every { MBIngressKit.userService().loadUser(MY_TOKEN) } answers { userTaskObject.futureTask() }

        // Preparation for MBSupportKit
        mockkObject(MBSupportKit)
        every { MBSupportKit.cacMessageService().sendBotMessage(MY_TOKEN, any()) } answers { answerTaskObject.futureTask() }
        every { MBSupportKit.cacMessageService().sendSupportMail(MY_TOKEN, any()) } answers { answerTaskObject.futureTask() }

        // Others
        mockkStatic(BitmapFactory::class)
        every { BitmapFactory.decodeResource(any(), any()) } returns mockBitmap

        mockkObject(SupportUtils)
        every { SupportUtils.getMailSubject(any()) } returns TEST_SUBJECT
        every { SupportUtils.getMailBody(any(), any(), any(), any(), any(), any(), any()) } returns TEST_BODY
        every { SupportUtils.makeAttachmentList(any()) } returns emptyList()

        every { mockVehicle.finOrVin } returns "VIN1"
        every { mockVehicle.model } returns "Model1"
        every { mockVehicle.licensePlate } returns "LP-1"
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun onInit_withDefaultData_shouldShowMessageEnterVisibleAndAllOtherLayoutsGoneAndAskQuestionButtonDisable() {
        initTestObjectWithDefaultMockData()

        assertThat(testObject.messageEnterVisible.value).isTrue()
        assertThat(testObject.messageDetailsVisible.value).isFalse()
        assertThat(testObject.progressVisible.value).isFalse()
        assertThat(testObject.botAnswerVisible.value).isFalse()
        assertThat(testObject.cacSuccessVisible.value).isFalse()
        assertThat(testObject.cacErrorVisible.value).isFalse()

        assertThat(testObject.sendMessageEnabled.value).isFalse()

        assertThat(testObject.vehicleSelectionVisible.value).isTrue()
        assertThat(testObject.selectedCar.value).isEqualTo("-")
        assertThat(testObject.dateTime.value).isEqualTo("-")
        assertThat(testObject.imageList.value.size).isEqualTo(1)
    }

    @Test
    fun onInit_withNoCar_shouldShowCarSelectionGone() {
        vehicles = initVehicles(0)

        initTestObjectWithDefaultMockData()

        assertThat(testObject.vehicleSelectionVisible.value).isFalse()
    }

    @Test
    fun onInit_withOneCar_shouldShowCarSelectionGone() {
        vehicles = initVehicles(1)

        initTestObjectWithDefaultMockData()

        assertThat(testObject.vehicleSelectionVisible.value).isFalse()
    }

    @Test
    fun onQuestionChanged_withNotBlankString_shouldEnabledAskQuestionButton() {
        initTestObjectWithDefaultMockData()

        testObject.onQuestionChanged("Test", 0, 0, 0)

        assertThat(testObject.sendMessageEnabled.value).isTrue()
        assertThat(testObject.message.value).isEqualTo("Test")
    }

    @Test
    fun onQuestionChanged_withBlankString_shouldDisableAskQuestionButton() {
        initTestObjectWithDefaultMockData()

        testObject.onQuestionChanged("", 0, 0, 0)

        assertThat(testObject.sendMessageEnabled.value).isFalse()
        assertThat(testObject.message.value).isEqualTo("")
    }

    @Test
    fun onDateSelected_withDate_shouldSetCorrectDate() {
        val date = Date()

        initTestObjectWithDefaultMockData()

        testObject.onDateSelected(date)

        assertThat(testObject.dateTime.value).isEqualTo(date.toLocalDateTimeString())
    }

    @Test
    fun onDateSelected_withNull_shouldSetDash() {
        initTestObjectWithDefaultMockData()

        testObject.onDateSelected(null)

        assertThat(testObject.dateTime.value).isEqualTo("-")
    }

    @Test
    fun onCarSelection_withCar0_shouldSetSelectedCarAndSelectedVin() {
        initTestObjectWithDefaultMockData()

        testObject.onCarSelection(1)

        assertThat(testObject.selectedCar.value).isEqualTo("${vehicles.vehicles[1].model} (${vehicles.vehicles[1].licensePlate})")
        assertThat(testObject.selectedVin).isEqualTo(vehicles.vehicles[1].finOrVin)
        assertThat(testObject.selectedCarChoice).isEqualTo(1)
    }

    @Test
    fun onCarSelection_withCarNull_shouldSetSelectedCarAndSelectedVinToDefault() {
        initTestObjectWithDefaultMockData()

        testObject.onCarSelection(null)

        assertThat(testObject.selectedCar.value).isEqualTo("-")
        assertThat(testObject.selectedVin).isEqualTo("")
        assertThat(testObject.selectedCarChoice).isEqualTo(0)
    }

    @Test
    fun onAskQuestion_shouldShowOverviewAndInProgress_onSuccessShouldShowBotAnswer() {
        initTestObjectWithDefaultMockData()

        testObject.message.value = TEST_MESSAGE
        testObject.addImage(mockBitmap, mockBitmap)

        testObject.onAskButtonClick()

        assertThat(testObject.messageEnterVisible.value).isFalse()
        assertThat(testObject.messageDetailsVisible.value).isTrue()
        assertThat(testObject.imageGridVisible.value).isTrue()
        assertThat(testObject.progressVisible.value).isTrue()
        assertThat(testObject.message.value).isEqualTo(TEST_MESSAGE)

        tokenTaskObject.complete(mockToken)
        answerTaskObject.complete(MESSAGE_ANSWER_BOT)

        // We check for 3 because this function is called two times before
        verify(exactly = 3) { MBIngressKit.refreshTokenIfRequired() }
        verify(exactly = 1) { MBSupportKit.cacMessageService().sendBotMessage(MY_TOKEN, any()) }

        assertThat(testObject.progressVisible.value).isFalse()
        assertThat(testObject.botAnswerVisible.value).isTrue()
        assertThat(testObject.botAnswer.value).isEqualTo(TEST_ANSWER)
    }

    @Test
    fun onSendCacMail_shouldShowOverviewAndInProgress_onSuccessShouldShowSendLayout() {
        initTestObjectWithDefaultMockData()

        // Preparation for direct call of sendCacMail
        val cacDate = Date()
        testObject.message.value = TEST_MESSAGE
        testObject.messageDetailsVisible.value = true
        testObject.botAnswer.value = TEST_ANSWER
        testObject.selectedVin = "VIN"
        testObject.cacDate = cacDate

        testObject.retryCacMail()

        assertThat(testObject.cacErrorVisible.value).isFalse()
        assertThat(testObject.cacSuccessVisible.value).isFalse()
        assertThat(testObject.messageDetailsVisible.value).isTrue()
        assertThat(testObject.progressVisible.value).isTrue()
        assertThat(testObject.botAnswerVisible.value).isFalse()

        tokenTaskObject.complete(mockToken)
        answerTaskObject.complete(MESSAGE_ANSWER_CAC_SUCCESS)

        // We check for 3 because this function is called two times before
        verify(exactly = 3) { MBIngressKit.refreshTokenIfRequired() }
        verify(exactly = 1) { MBSupportKit.cacMessageService().sendSupportMail(MY_TOKEN, any()) }

        assertThat(testObject.cacSuccessVisible.value).isTrue()
        assertThat(testObject.progressVisible.value).isFalse()
        assertThat(testObject.cacErrorVisible.value).isFalse()
    }

    @Test
    fun onSendCacMail_shouldShowOverviewAndInProgress_onFailureShouldShowErrorLayout() {
        initTestObjectWithDefaultMockData()

        // Preparation for direct call of sendCacMail
        val cacDate = Date()
        testObject.message.value = TEST_MESSAGE
        testObject.messageDetailsVisible.value = true
        testObject.botAnswer.value = TEST_ANSWER
        testObject.selectedVin = "VIN"
        testObject.cacDate = cacDate

        testObject.retryCacMail()

        assertThat(testObject.cacErrorVisible.value).isFalse()
        assertThat(testObject.cacSuccessVisible.value).isFalse()
        assertThat(testObject.messageDetailsVisible.value).isTrue()
        assertThat(testObject.progressVisible.value).isTrue()
        assertThat(testObject.botAnswerVisible.value).isFalse()

        tokenTaskObject.complete(mockToken)
        answerTaskObject.fail(ResponseError.httpError(500, "pla", null))

        // We check for 3 because this function is called two times before
        verify(exactly = 3) { MBIngressKit.refreshTokenIfRequired() }
        verify(exactly = 1) { MBSupportKit.cacMessageService().sendSupportMail(MY_TOKEN, any()) }

        assertThat(testObject.cacSuccessVisible.value).isFalse()
        assertThat(testObject.progressVisible.value).isFalse()
        assertThat(testObject.cacErrorVisible.value).isTrue()
        assertThat(testObject.cacErrorMessage.value).isEqualTo(GENERAL_ERROR)
    }

    private fun initTestObjectWithDefaultMockData() {
        testObject = SupportMessageViewModel(mockApplication)
        answerTaskObject = TaskObject()
        tokenTaskObject.complete(mockToken)
        vehiclesTaskObject.complete(vehicles)
        tokenTaskObject = TaskObject()
        tokenTaskObject.complete(mockToken)
        userTaskObject.complete(mockUser)
        tokenTaskObject = TaskObject()
    }

    private fun initVehicles(vehicleCount: Int): Vehicles {
        val mutableVehicleList: MutableList<VehicleInfo> = mutableListOf()
        (1..vehicleCount).forEach {
            mutableVehicleList.add(getVehicle(it))
        }
        return Vehicles(mutableVehicleList.toList(), true)
    }

    private fun getVehicle(vehicleCount: Int): VehicleInfo {
        return mockVehicle
    }

    companion object {
        private const val MY_TOKEN = "MY_TOKEN"
        private const val TEST_MESSAGE = "Test Message"
        private const val TEST_ANSWER = "Test Answer"
        private const val TEST_SUBJECT = "Subject"
        private const val TEST_BODY = "Body"
        private const val TEST_SESSIONID = "1234"
        private const val GENERAL_ERROR = "General Error"
        private val USER_LOCALE = UserLocale("DE", "de")
        private val MESSAGE_ANSWER_BOT = MessageAnswer(MessageAnswerType.BOT_ANSWER, TEST_ANSWER, null)
        private val MESSAGE_ANSWER_CAC_SUCCESS = MessageAnswer(MessageAnswerType.CAC_MAIL_SEND, null, null)
    }
}