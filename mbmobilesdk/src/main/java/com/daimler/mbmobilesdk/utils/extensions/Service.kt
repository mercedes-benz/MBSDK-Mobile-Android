package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbcarkit.business.model.services.ServiceAction
import com.daimler.mbcarkit.business.model.services.ServiceRight
import com.daimler.mbcarkit.business.model.services.ServiceStatus

/**
 * Returns true if this service has an action and a right that point to the
 * opposite state. E.g. if this service's state is [ServiceStatus.ACTIVE] and its allowed actions'
 * contain [ServiceAction.SET_DESIRED_INACTIVE] and the rights contain [ServiceRight.DEACTIVATE].
 */
fun Service.isChangeable() = canSwapState()

/**
 * Returns true if this service's allowed actions and rights contain the opposite state of this service's
 * current state.
 */
private fun Service.canSwapState() = isActivationAllowed() || isDeactivationAllowed()

fun Service.isActivationAllowed() =
    !isActive() && allowedActions.contains(ServiceAction.SET_DESIRED_ACTIVE) &&
        rights.contains(ServiceRight.ACTIVATE)

fun Service.isDeactivationAllowed() =
    isActive() && allowedActions.contains(ServiceAction.SET_DESIRED_INACTIVE) &&
        rights.contains(ServiceRight.DEACTIVATE)

/**
 * Returns true if this service is active or activating.
 */
fun Service.isActive() = activationStatus == ServiceStatus.ACTIVE || activationStatus == ServiceStatus.ACTIVATION_PENDING

/**
 * Returns true if this service is currently activating.
 */
fun Service.isActivationPending() =
    activationStatus == ServiceStatus.ACTIVATION_PENDING

/**
 * Returns true if this service is currently deactivating.
 */
fun Service.isDeactivationPending() =
    activationStatus == ServiceStatus.DEACTIVATION_PENDING

/**
 * Returns if this service has any details that can be shown to the user.
 */
fun Service.hasDetails() = true // TODO

fun Service.needsPurchase() = allowedActions.contains(ServiceAction.PURCHASE_LICENSE)

// TODO consent?!
fun Service.hasPendingPreconditions() =
    prerequisiteChecks.firstOrNull { it.name?.toLowerCase() != "consent" && it.actions.isNotEmpty() } != null