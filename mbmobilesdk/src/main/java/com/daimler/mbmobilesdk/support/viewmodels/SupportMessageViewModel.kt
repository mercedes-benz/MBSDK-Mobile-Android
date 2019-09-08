package com.daimler.mbmobilesdk.support.viewmodels

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.BuildConfig
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.app.Stage
import com.daimler.mbmobilesdk.app.format
import com.daimler.mbmobilesdk.support.SupportUtils
import com.daimler.mbmobilesdk.support.models.ImageItem
import com.daimler.mbmobilesdk.support.models.RealImage
import com.daimler.mbmobilesdk.utils.extensions.toLocalDateTimeString
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.User
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbcarkit.business.model.vehicle.Vehicles
import com.daimler.mbsupportkit.MBSupportKit
import com.daimler.mbsupportkit.common.MessageAnswer
import com.daimler.mbsupportkit.common.MessageAnswerType
import com.daimler.mbsupportkit.common.MessageQuestion
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.utils.extensions.getString
import java.util.*

class SupportMessageViewModel(application: Application) : AndroidViewModel(application), ImageItem.GridItemListener {
    val clickEvent = MutableLiveEvent<Int>()

    // the icon to add
    val ADD_IMAGE_ICON = ImageItem(
            BitmapFactory.decodeResource(application.resources, R.drawable.plus_button),
            this)

    // a list of attached images (including the "add image" icon)
    val imageList = MutableLiveArrayList<ImageItem>()

    val messageEnterVisible = MutableLiveData<Boolean>()
    val messageDetailsVisible = MutableLiveData<Boolean>()
    val progressVisible = MutableLiveData<Boolean>()
    val botAnswerVisible = MutableLiveData<Boolean>()
    val imageGridVisible = MutableLiveData<Boolean>()
    val cacErrorVisible = MutableLiveData<Boolean>()
    val cacSuccessVisible = MutableLiveData<Boolean>()

    val sendMessageEnabled = MutableLiveData<Boolean>()
    val vehicleSelectionVisible = MutableLiveData<Boolean>()
    val vehicleOptionList = MutableLiveData<Array<CharSequence>>()

    val selectedCar = MutableLiveData<String>()
    val dateTime = MutableLiveData<String>()
    val message = MutableLiveData<String>()
    val botAnswer = MutableLiveData<String>()
    val cacErrorMessage = MutableLiveData<String>()

    var vehicles: Vehicles? = null
    var user: User? = null
    var selectedVin = ""
    var cacDate: Date? = null
    var selectedCarChoice: Int = 0

    init {
        vehicleSelectionVisible.postValue(false)
        vehiclesLoad()
        clear()
    }

    fun clear() {
        imageList.value.clear()
        imageList.addAllAndDispatch(listOf(ADD_IMAGE_ICON))
        messageEnterVisible.postValue(true)
        messageDetailsVisible.postValue(false)
        progressVisible.postValue(false)
        botAnswerVisible.postValue(false)
        imageGridVisible.postValue(false)
        cacSuccessVisible.postValue(false)
        cacErrorVisible.postValue(false)

        sendMessageEnabled.postValue(false)

        dateTime.postValue("-")
        message.postValue("")
        selectedCar.postValue("-")
        botAnswer.postValue("")
        cacErrorMessage.postValue("")
        selectedVin = ""
        cacDate = null
        selectedCarChoice = 0
    }

    override fun onAddImageClicked() {
        MBLoggerKit.d("Add image clicked!")
        clickEvent.sendEvent(HIDE_KEYBOARD)
        clickEvent.sendEvent(REQ_PERMISSION)
    }

    override fun onAttachedImageCloserClicked(imageItem: ImageItem) {
        clickEvent.sendEvent(HIDE_KEYBOARD)
        removeImage(imageItem)
    }

    fun onQuestionChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        message.postValue(s.toString())
        if (s.isNotBlank()) {
            sendMessageEnabled.value = true
            return
        }
        sendMessageEnabled.value = false
    }

    fun onDataDialogClick() {
        clickEvent.sendEvent(HIDE_KEYBOARD)
        clickEvent.sendEvent(DATA_DIALOG)
    }

    fun onDateTimeClick() {
        clickEvent.sendEvent(HIDE_KEYBOARD)
        clickEvent.sendEvent(DATE_SELECTION)
    }

    fun onAskButtonClick() {
        clickEvent.sendEvent(HIDE_KEYBOARD)
        askBotQuestion()
    }

    fun onShareClick() = clickEvent.sendEvent(SHARE_ANSWER)

    fun resetAll() = clear()

    fun retryCacMail() = sendCacMail()

    // ---- Handle dialog results
    fun onDateSelected(selectedDate: Date?) {
        selectedDate?.let {
            dateTime.postValue(selectedDate.toLocalDateTimeString())
            cacDate = selectedDate
        } ?: apply {
            dateTime.postValue("-")
            cacDate = null
        }
    }

    fun onCarSelection(choice: Int?) {
        val veh = vehicles ?: return
        choice?.let {
            selectedCar.postValue(getCarName(veh.vehicles[choice]))
            selectedVin = veh.vehicles[choice].finOrVin
            selectedCarChoice = choice
        } ?: apply {
            selectedCar.postValue("-")
            selectedVin = ""
            selectedCarChoice = 0
        }
    }

    /**
     * Sets the correct visibility for the layouts
     * Sets the question and locale
     * Calls MBSupportKit to sendBotMessage
     */
    private fun askBotQuestion() {
        messageEnterVisible.postValue(false)
        progressVisible.postValue(true)
        removeAddImageIconFromList()
        imageList.value.forEach { it.hideRemoveForOverview() }
        imageGridVisible.postValue(imageList.value.size > 0)
        messageDetailsVisible.postValue(true)
        val question = MessageQuestion(message.value!!, MBMobileSDK.userLocale().format())
        MBIngressKit.refreshTokenIfRequired()
                .onComplete { token ->
                    MBSupportKit.cacMessageService().sendBotMessage(token.jwtToken.plainToken, question)
                            .onComplete { answer -> onBotComplete(answer) }
                            .onFailure { sendCacMail() }
                }.onFailure { sendCacMail() }
    }

    private fun onBotComplete(messageAnswer: MessageAnswer) {
        if (messageAnswer.messageAnswerType == MessageAnswerType.BOT_ANSWER) {
            botAnswer.postValue(messageAnswer.answer)
            progressVisible.postValue(false)
            botAnswerVisible.postValue(true)
            return
        }
        sendCacMail()
    }

    /**
     * Sets the correct visibility for the layouts
     * Sets the E-Mail data
     * Calls the MBSupportKit to send mail
     */
    private fun sendCacMail() {
        cacSuccessVisible.postValue(false)
        cacErrorVisible.postValue(false)
        botAnswerVisible.postValue(false)
        progressVisible.postValue(true)
        val subject = SupportUtils.getMailSubject(getApplication())
        val body = SupportUtils.getMailBody(getApplication(),
                message.value ?: "",
                botAnswer.value ?: "",
                selectedVin,
                cacDate,
                user,
                MBMobileSDK.appSessionId())
        val attachments = SupportUtils.makeAttachmentList(imageList)
        val messageQuestion = MessageQuestion(message.value ?: "",
                MBMobileSDK.userLocale().format(),
                subject,
                body,
                getSendToEmail(),
                attachments)
        MBIngressKit.refreshTokenIfRequired()
                .onComplete { token ->
                    MBSupportKit.cacMessageService().sendSupportMail(token.jwtToken.plainToken, messageQuestion)
                            .onComplete { answer -> onSendCacMailComplete(answer) }
                            .onFailure { onSendCacMailFailed(getString(R.string.default_error_msg)) }
                }.onFailure { onSendCacMailFailed(getString(R.string.default_error_msg)) }
    }

    private fun onSendCacMailComplete(answer: MessageAnswer) {
        if (answer.messageAnswerType == MessageAnswerType.CAC_MAIL_SEND) {
            cacSuccessVisible.postValue(true)
            progressVisible.postValue(false)
            return
        }
        val error: String = when (answer.messageAnswerType) {
            MessageAnswerType.ERROR_NETWORK -> getString(R.string.rssm_message_chat_sent_error_connection)
            MessageAnswerType.ERROR_REQUEST -> getString(R.string.rssm_message_chat_sent_error_server)
            else -> getString(R.string.default_error_msg)
        }
        onSendCacMailFailed(error)
    }

    private fun onSendCacMailFailed(message: String) {
        cacErrorMessage.postValue(message)
        progressVisible.postValue(false)
        cacErrorVisible.postValue(true)
    }

    /**
     * Loads the vehicles from MBCarKit
     * onComplete/onFailure calls on vehiclesLoaded with an (empty) array list of vehicles
     */
    private fun vehiclesLoad() {
        MBIngressKit.refreshTokenIfRequired()
                .onComplete { token ->
                    MBCarKit.vehicleService().fetchVehicles(token.jwtToken.plainToken)
                            .onComplete { vehicles -> onVehiclesLoaded(vehicles) }
                            .onFailure { onVehiclesLoaded(Vehicles(emptyList())) }
                }
                .onFailure { onVehiclesLoaded(Vehicles(emptyList())) }
    }

    /**
     * Sets the correct LiveData for the loaded Vehicles
     * if 0 or 1 vehicle is in list the car selection will be set to invisible
     * else the car selection is visible
     */
    private fun onVehiclesLoaded(vehicles: Vehicles) {
        userLoad()
        this.vehicles = vehicles
        selectedVin = ""
        selectedCar.value = "-"
        if (vehicles.vehicles.size <= 1) {
            vehicleSelectionVisible.postValue(false)
            if (vehicles.vehicles.size == 1) {
                selectedVin = vehicles.vehicles[0].finOrVin
                selectedCar.value = getCarName(vehicles.vehicles[0])
            }
        } else {
            val tmpList: Array<CharSequence> = vehicles.vehicles.map { getCarName(it) }.toTypedArray()
            vehicleOptionList.postValue(tmpList)
            vehicleSelectionVisible.postValue(true)
        }
    }

    /**
     * Loads the user for additional data that will be send
     * to the CAC
     */
    private fun userLoad() {
        MBIngressKit.refreshTokenIfRequired()
                .onComplete { token ->
                    MBIngressKit.userService().loadUser(token.jwtToken.plainToken)
                            .onComplete { user -> this.user = user }
                            .onFailure { MBLoggerKit.e("Error loading user") }
                }
                .onFailure { MBLoggerKit.e("Error getting token") }
    }

    // Images
    /**
     * Add an image to list, remove "add" icon from the end if list is now full.
     */
    fun addImage(thumb: Bitmap, image: Bitmap) {
        val realImage = RealImage(thumb, image, this)
        if (imageList.value.size >= MAX_NUMBER_OF_ICONS) {
            // we have max number of icons, remove the "add" icon
            imageList.removeAndDispatch(MAX_NUMBER_OF_ICONS - 1)
        }
        imageList.addAndDispatch(0, realImage)
    }

    /**
     * Remove the image at <code>position</code> from the list. If "add" icon was missing, add it.
     */
    fun removeImage(imageItem: ImageItem) {
        imageList.removeAndDispatch(imageItem)
        // if add icon button is missing, re-add it
        if ((imageList.value.size < MAX_NUMBER_OF_ICONS) &&
                (!imageList.value.contains(ADD_IMAGE_ICON))) {
            imageList.addAndDispatch(ADD_IMAGE_ICON)
        }
    }

    private fun removeAddImageIconFromList() {
        if (!imageList.value.contains(ADD_IMAGE_ICON)) return
        imageList.value.remove(ADD_IMAGE_ICON)
        imageList.dispatchChange()
    }

    // ----- Helper functions
    /**
     * Returns a String with the vehicle model and if exists the vehicle license plate
     * Format: Model (LicensePlate)
     */
    private fun getCarName(vehicleInfo: VehicleInfo): String {
        var carName = vehicleInfo.model
        if (vehicleInfo.licensePlate.isNotEmpty()) {
            carName = "$carName (${vehicleInfo.licensePlate})"
        }
        return carName
    }

    /**
     * If we are non PROD-Stages we don't want to send an email to CAC.
     * If the developer entered a email to local.properties this one will be used for non PROD-Stages.
     * If no email is available in local.properties we try to use the email from the logged-in account.
     * If the E-Mail is empty and we are non PROD-Stages we give the user a hint about it, via a Toast
     */
    private fun getSendToEmail(): String {
        return when (MBMobileSDK.selectedStage()) {
            Stage.PROD -> "mercedes_me@cac.mercedes-benz.com"
            else -> {
                val email = getNonProdMail()
                if (email.isBlank() && MBMobileSDK.selectedStage() != Stage.PROD) {
                    MBLoggerKit.d("Please set a email, f.e. in local.properties or for the user")
                }
                return email
            }
        }
    }

    private fun getNonProdMail(): String {
        if (BuildConfig.TEST_EMAIL.isNotBlank() && BuildConfig.TEST_EMAIL != "null") return BuildConfig.TEST_EMAIL
        if (MBMobileSDK.selectedStage() != Stage.MOCK) return user?.email ?: ""
        return ""
    }

    companion object {
        const val MAX_NUMBER_OF_ICONS = 3
        const val HIDE_KEYBOARD = 1
        const val DATA_DIALOG = 3
        const val DATE_SELECTION = 4
        const val SHARE_ANSWER = 5
        const val REQ_PERMISSION = 6
    }
}
