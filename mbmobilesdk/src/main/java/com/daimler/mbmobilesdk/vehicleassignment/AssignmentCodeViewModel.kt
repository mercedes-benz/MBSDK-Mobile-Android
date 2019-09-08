package com.daimler.mbmobilesdk.vehicleassignment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.ifValid
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.rif.Rifability
import com.daimler.mbnetworkkit.networking.HttpError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.connectors.EditTextConnector
import com.daimler.mbuikit.utils.extensions.getString

class AssignmentCodeViewModel(app: Application) : AndroidViewModel(app) {

    val vin = EditTextConnector { !it.isNullOrBlank() }
    val validationCode = EditTextConnector { !it.isNullOrBlank() }
    val progressVisible = MutableLiveData<Boolean>()

    internal val codeGeneratedEvent = MutableLiveEvent<String>()

    internal val invalidVinEvent = MutableLiveUnitEvent()
    internal val alreadyAssignedEvent = MutableLiveUnitEvent()

    internal val codeValidationEvent = MutableLiveEvent<String>()

    internal val noRifSupportEvent = MutableLiveEvent<String>()
    internal val legacyVehicleEvent = MutableLiveEvent<String>()

    internal val errorEvent = MutableLiveEvent<String>()

    private val isLoading: Boolean
        get() = progressVisible.value == true

    fun onGenerateCodeClicked() {
        vin.ifValid {
            generateCode(it)
        }
    }

    fun onValidateCodeClicked() {
        if (vin.isValid() && validationCode.isValid()) {
            validateVac(vin.value!!, validationCode.value!!)
        }
    }

    private fun generateCode(vin: String) {
        if (isLoading) return
        if (vin.length != VIN_LENGTH) {
            errorEvent.sendEvent(String.format(
                getString(R.string.assign_code_validation_length), VIN_LENGTH
            ))
            return
        }
        validateVin(vin)
    }

    private fun validateVin(vin: String) {
        onLoadingStarted()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBCarKit.assignmentService().assignVehicleByVin(jwt, vin)
                    .onComplete {
                        MBLoggerKit.d("Valid vin, rif = ${it.isRifable}")
                        notifyVinValidation(it, vin)
                    }.onFailure {
                        MBLoggerKit.re("Failed to assign vehicle.", it)
                        notifyVinValidationError(it)
                    }.onAlways { _, _, _ ->
                        onLoadingFinished()
                    }
            }.onFailure {
                MBLoggerKit.e("Failed to refresh token.", throwable = it)
                onDefaultError(it)
                onLoadingFinished()
            }
    }

    private fun validateVac(vin: String, vac: String) {
        if (isLoading) return
        onLoadingStarted()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBCarKit.assignmentService().confirmVehicleAssignmentWithVac(jwt, vin, vac)
                    .onComplete {
                        MBLoggerKit.d("Assigned vehicle $vin.")
                        notifyVacValidation(vin)
                    }
                    .onFailure {
                        MBLoggerKit.re("Failed to assign vehicle.", it)
                        onDefaultError(it)
                    }
                    .onAlways { _, _, _ -> onLoadingFinished() }
            }
            .onFailure {
                MBLoggerKit.e("Failed to refresh token.")
                onLoadingFinished()
                onDefaultError(it)
            }
    }

    private fun notifyVinValidation(rifability: Rifability, vin: String) {
        when {
            rifability.isRifable -> codeGeneratedEvent.sendEvent(vin)
            rifability.isConnectVehicle -> noRifSupportEvent.sendEvent(vin)
            else -> legacyVehicleEvent.sendEvent(vin)
        }
    }

    private fun notifyVinValidationError(error: ResponseError<out RequestError>?) {
        when {
            // 404 -> VIN not found
            error?.requestError is HttpError.NotFound -> invalidVinEvent.sendEvent()
            // 409 -> VIN already assigned
            error?.requestError is HttpError.Conflict -> alreadyAssignedEvent.sendEvent()
            else -> onDefaultError(error)
        }
    }

    private fun notifyVacValidation(vin: String) {
        codeValidationEvent.sendEvent(vin)
    }

    private fun onDefaultError(throwable: Throwable?) {
        errorEvent.sendEvent(defaultErrorMessage(throwable))
    }

    private fun onDefaultError(error: ResponseError<out RequestError>?) {
        errorEvent.sendEvent(defaultErrorMessage(error))
    }

    private fun onLoadingStarted() {
        progressVisible.postValue(true)
    }

    private fun onLoadingFinished() {
        progressVisible.postValue(false)
    }

    private companion object {
        private const val VIN_LENGTH = 17
    }
}