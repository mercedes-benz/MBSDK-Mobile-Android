package com.daimler.mbmobilesdk.example

import android.graphics.Bitmap
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.vehicle.DoorLockOverallStatus
import com.daimler.mbcarkit.business.model.vehicle.Tank
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.business.model.vehicle.WindowsOverallStatus
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbmobilesdk.MBMobileSDK
import com.daimler.mbmobilesdk.example.car.CarKitConnectionListener
import com.daimler.mbmobilesdk.example.car.CarKitRepository
import com.daimler.mbmobilesdk.example.car.CarKitVehicleStatusListener
import com.daimler.mbmobilesdk.example.car.SendToCarError
import com.daimler.mbmobilesdk.example.car.SendToCarStatusListener
import com.daimler.mbmobilesdk.example.ingress.IngressKitRepository
import com.daimler.mbmobilesdk.example.utils.MutableLiveEvent
import com.daimler.mbmobilesdk.example.utils.MutableLiveUnitEvent
import com.daimler.mbmobilesdk.example.utils.getBitmap
import com.daimler.mbnetworkkit.socket.ConnectionState
import com.google.common.collect.Maps
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

class ExampleViewModel : ViewModel(), CarKitConnectionListener, CarKitVehicleStatusListener {

    private val carKitRepository = CarKitRepository()
    private val ingressKitRepository = IngressKitRepository()
    val onLoginEvent = MutableLiveUnitEvent()
    val onSelectVehicleEvent = MutableLiveUnitEvent()
    val isUserLoggedIn = MutableLiveData(false)
    val fin = MutableLiveData<String>()
    val vehicleStatusText = MutableLiveData<CharSequence>()
    val isVehicleConnected = MutableLiveData(false)
    val isDoorsLocked = MutableLiveData(false)
    val doorStateText = MutableLiveData<String>()
    val isDoorStateValid = MutableLiveData(false)
    val isWindowsLocked = MutableLiveData(false)
    val windowStateText = MutableLiveData<String>()
    val isWindowStateValid = MutableLiveData(false)
    val vehicleImage = MutableLiveData<Bitmap>()
    val gasRangeText = MutableLiveData<String>()
    val liquidRangeText = MutableLiveData<String>()
    val electricRangeText = MutableLiveData<String>()
    val isSendToCarInProgress = MutableLiveData<Boolean>()
    val sendToCarError = MutableLiveEvent<SendToCarError>()
    val sendToCarSuccess = MutableLiveEvent<Unit>()

    private var lastKnownVehicleStatus: VehicleStatus? = null

    init {
        carKitRepository.addStatusListener(this)
        carKitRepository.addConnectionListener(this)
        fin.postValue(MBCarKit.selectedFinOrVin())
        if (MBIngressKit.authenticationService().isLoggedIn()) {
            ingressKitRepository.loadUser()
            isUserLoggedIn.postValue(true)
        } else {
            isDoorsLocked.postValue(false)
        }
    }

    fun onLoginClicked() {
        onLoginEvent.sendEvent()
    }

    fun onLogoutClicked() {
        isUserLoggedIn.postValue(false)
        MBMobileSDK.logoutAndCleanUp {
            reset()
            carKitRepository.disconnect()
        }
    }

    fun onSelectVehicleClicked() {
        onSelectVehicleEvent.sendEvent()
    }

    fun onCarConnectClicked() {
        carKitRepository.connectToSocket()
        requestCurrentVehicleStatus()
    }

    fun onCarDisconnectClicked() {
        resetCarTextInfo()
        carKitRepository.disconnect()
    }

    fun onUnlockDoorsClicked() {
        carKitRepository.unlockDoors()
    }

    fun onLockDoorsClicked() {
        carKitRepository.lockDoors()
    }

    fun onOpenWindowsClicked() {
        carKitRepository.openWindows()
    }

    fun onCloseWindowsClicked() {
        carKitRepository.closeWindows()
    }

    fun requestVehicleImage() {
        carKitRepository.fetchVehicleImages().onComplete { vehicleImages ->
            vehicleImage.postValue(vehicleImages.first().getBitmap())
        }
    }

    fun requestCurrentVehicleStatus() {
        lastKnownVehicleStatus = null
        fin.postValue(MBCarKit.selectedFinOrVin())
        carKitRepository.loadCurrentVehicleStatus()
    }

    fun disconnect() {
        carKitRepository.disconnect()
    }

    fun onSendPoiToCarClicked() {
        isSendToCarInProgress.value = true
        carKitRepository.sendPoiToCar(object : SendToCarStatusListener {
            override fun onSuccess() {
                sendToCarSuccess.sendEvent(Unit)
                isSendToCarInProgress.value = false
            }

            override fun onFailed(error: SendToCarError) {
                sendToCarError.sendEvent(error)
                isSendToCarInProgress.value = false
            }
        })
    }

    override fun onConnectionStateChanged(connectionState: ConnectionState) {
        isVehicleConnected.postValue(connectionState is ConnectionState.Connected)
        isUserLoggedIn.postValue(MBIngressKit.authenticationService().isLoggedIn())
    }

    override fun onDoorLockOverallStatusChanged(doorLockOverallStatus: DoorLockOverallStatus) {
        isDoorsLocked.postValue(doorLockOverallStatus == DoorLockOverallStatus.LOCKED)
        doorStateText.postValue(doorLockOverallStatus.name)
        isDoorStateValid.postValue(doorLockOverallStatus != DoorLockOverallStatus.UNKNOWN)
    }

    override fun onWindowOverallStatusChanged(windowsOverallStatus: WindowsOverallStatus) {
        isWindowsLocked.postValue(windowsOverallStatus == WindowsOverallStatus.CLOSED)
        windowStateText.postValue(windowsOverallStatus.name)
        isWindowStateValid.postValue(windowsOverallStatus != WindowsOverallStatus.UNKNOWN)
    }

    override fun onTankInformationChanged(tank: Tank) {
        tank.gasRange.value?.let { gasRangeValue ->
            gasRangeText.postValue(gasRangeValue.toString())
        } ?: run { gasRangeText.postValue(String()) }

        tank.liquidRange.value?.let { liquidRangeValue ->
            liquidRangeText.postValue(liquidRangeValue.toString())
        } ?: run { liquidRangeText.postValue(String()) }

        tank.electricRange.value?.let { electricRangeValue ->
            electricRangeText.postValue(electricRangeValue.toString())
        } ?: run { electricRangeText.postValue(String()) }
    }

    override fun onVehicleStatusChanged(vehicleStatus: VehicleStatus) {
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create()

        val newText = gson.toJson(vehicleStatus)
        lastKnownVehicleStatus?.let { oldVehicleStatus ->
            val differences =
                differ(gson.toJson(oldVehicleStatus), gson.toJson(vehicleStatus), gson)
            val spannable = SpannableStringBuilder(newText)
            var currentPosition = 0
            differences.forEach {
                val startOf = newText.subSequence(currentPosition, newText.length).indexOf(it)
                if (startOf >= 0) {
                    spannable.setSpan(
                        BackgroundColorSpan(Color.GREEN),
                        currentPosition + startOf,
                        currentPosition + startOf + it.length,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    currentPosition = startOf
                }
            }
            vehicleStatusText.value = spannable
        } ?: run {
            vehicleStatusText.value = newText
        }

        lastKnownVehicleStatus = vehicleStatus
    }

    private fun differ(
        leftJson: String,
        rightJson: String,
        gson: Gson
    ): List<String> {
        val result = mutableListOf<String>()
        if (!isValidJson(leftJson) || !isValidJson(rightJson)) {
            result.add(rightJson)
            return result
        }

        val leftMap: Map<String, Any> = gson.fromJson(
            leftJson,
            object : TypeToken<Map<String?, Any?>?>() {}.type
        )
        val rightMap: Map<String, Any> = gson.fromJson(
            rightJson,
            object : TypeToken<Map<String?, Any?>?>() {}.type
        )

        Maps.difference(leftMap, rightMap).entriesDiffering().forEach { (key, valueDifference) ->
            result.add(key)
            if (valueDifference.leftValue() != null && valueDifference.rightValue() != null) {
                result.addAll(
                    differ(
                        gson.toJson(valueDifference.leftValue()),
                        gson.toJson(valueDifference.rightValue()),
                        gson
                    )
                )
            }
        }
        return result
    }

    private fun isValidJson(jsonText: String) = try {
        val jsonElement = JsonParser.parseString(jsonText)
        jsonElement.isJsonObject
    } catch (e: Exception) {
        false
    }

    override fun onCleared() {
        super.onCleared()
        carKitRepository.removeStatusListener(this)
        carKitRepository.removeConnectionListener(this)
    }

    private fun reset() {
        isVehicleConnected.value = false
        isDoorsLocked.value = false
        isWindowsLocked.value = false
        vehicleImage.value = null
        resetCarTextInfo()
    }

    private fun resetCarTextInfo() {
        doorStateText.value = String()
        windowStateText.value = String()
        gasRangeText.value = String()
        liquidRangeText.value = String()
        electricRangeText.value = String()
        isDoorStateValid.value = false
        isWindowStateValid.value = false
    }
}
