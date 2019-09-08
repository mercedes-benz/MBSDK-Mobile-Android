package com.daimler.mbmobilesdk.serviceactivation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.*
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbcarkit.business.model.services.ServiceStatusDesire
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.utils.extensions.getString
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

class ServiceDetailViewModel(
    app: Application,
    private var service: Service,
    private val vin: String
) : AndroidViewModel(app), PreconditionStateActions {

    private var active = service.isActive()

    val toolbarTitle = mutableLiveDataOf(service.shortName ?: service.name)
    val description = mutableLiveDataOf(service.description ?: "")
    val state = mutableLiveDataOf(service.activationStatus.name)
    val allowedActions = mutableLiveDataOf(formatAllowedActions())
    val rights = mutableLiveDataOf(formatRights())
    val prerequisites = mutableLiveDataOf(formatPrerequisites())

    val progressVisible = MutableLiveData<Boolean>()
    val preconditionsVisible = mutableLiveDataOf(false)
    val noRightsVisible = mutableLiveDataOf(false)
    val activationButtonText = mutableLiveDataOf(getToggleButtonText(active))
    val activationButtonVisible = mutableLiveDataOf(false)

    internal val missingInformationEvent = MutableLiveEvent<PreconditionEvent>()

    init {
        val preconditionState = when {
            !service.isChangeable() -> ServicePreconditionState.NoRights
            service.hasPendingPreconditions() -> ServicePreconditionState.Pending
            else -> ServicePreconditionState.Fulfilled
        }
        preconditionState.showState(this)
    }

    fun onToggleServiceClicked() {
        MBLoggerKit.d("Toggle Service: ${service.name}.")
        progressVisible.value = true
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBCarKit.serviceService().requestServiceUpdate(
                    jwt, vin, listOf(ServiceStatusDesire(service.id, !active))
                ).onComplete {
                    MBLoggerKit.d("Updated service.")
                    swapState(active)
                }.onFailure {
                    MBLoggerKit.re("Could not swapState service status.", it)
                }.onAlways { _, _, _ -> progressVisible.value = false }
            }.onFailure {
                MBLoggerKit.e("Could not refresh token.")
                progressVisible.value = false
            }
    }

    fun onShowPreconditionsClicked() {
        missingInformationEvent.sendEvent(PreconditionEvent(vin, service))
    }

    override fun showPendingState() {
        activationButtonVisible.postValue(false)
        preconditionsVisible.postValue(true)
        noRightsVisible.postValue(false)
    }

    override fun showFulfilledState() {
        activationButtonVisible.postValue(true)
        preconditionsVisible.postValue(false)
        noRightsVisible.postValue(false)
    }

    override fun showNoRightsToChangeState() {
        activationButtonVisible.postValue(false)
        preconditionsVisible.postValue(false)
        noRightsVisible.postValue(true)
    }

    private fun swapState(oldState: Boolean) {
        active = !oldState
        activationButtonText.postValue(getToggleButtonText(active))
    }

    private fun getToggleButtonText(active: Boolean) =
        if (active) {
            getString(R.string.activate_services_deactivate_single_service)
        } else {
            getString(R.string.activate_services_activate_single_service)
        }

    private fun formatAllowedActions(): String = createStringOf(service.allowedActions) { it.name }

    private fun formatRights(): String = createStringOf(service.rights) { it.name }

    private fun formatPrerequisites(): String {
        val builder = StringBuilder()
        builder.apply {
            service.prerequisiteChecks.forEach {
                append(it.name ?: "-")
                if (it.actions.isNotEmpty()) {
                    append(": ")
                    append(it.actions.joinToString { action -> action.name })
                }
                if (it.missingFields.isNotEmpty()) {
                    newLine()
                    append("(")
                    append(it.missingFields.joinToString())
                    append(")")
                }
                newLine()
            }
        }
        return builder.toString()
    }

    internal data class PreconditionEvent(val finOrVin: String, val service: Service)
}