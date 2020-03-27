package com.daimler.mbcarkit

import com.daimler.mbcarkit.business.model.services.ServiceAction
import com.daimler.mbcarkit.business.model.services.ServiceStatus
import com.daimler.mbcarkit.implementation.ServiceActivationStateProcessorImpl
import com.daimler.mbcarkit.utils.createService
import junit.framework.Assert.assertEquals
import org.junit.Test

class ServiceActivationStateProcessorImplTest {

    private val processor = ServiceActivationStateProcessorImpl()

    private val currentActivationPending = createService(
        activationStatus = ServiceStatus.ACTIVATION_PENDING,
        desiredActivationStatus = ServiceStatus.ACTIVE
    )

    private val newActive = currentActivationPending.copy(
        activationStatus = ServiceStatus.ACTIVE,
        allowedActions = listOf(ServiceAction.SET_DESIRED_INACTIVE)
    )

    private val newOldStateInactive = currentActivationPending.copy(
        activationStatus = ServiceStatus.INACTIVE
    )

    private val currentDeactivationPending = createService(
        activationStatus = ServiceStatus.DEACTIVATION_PENDING,
        desiredActivationStatus = ServiceStatus.INACTIVE
    )

    private val newInactive = currentDeactivationPending.copy(
        activationStatus = ServiceStatus.INACTIVE,
        allowedActions = listOf(ServiceAction.SET_DESIRED_ACTIVE)
    )

    private val newOldStateActive = currentDeactivationPending.copy(
        activationStatus = ServiceStatus.ACTIVE
    )

    @Test
    fun testActivationFinalized() {
        val updatedService = processor.processActivationState(currentActivationPending, newActive)
        assertEquals(updatedService.activationStatus, ServiceStatus.ACTIVE)
    }

    @Test
    fun testActivationPending() {
        val updatedService = processor.processActivationState(currentActivationPending, newOldStateInactive)
        assertEquals(updatedService.activationStatus, ServiceStatus.ACTIVATION_PENDING)
    }

    @Test
    fun testDesiredStateRemainActivationPending() {
        val updatedService = processor.processActivationState(currentActivationPending, newInactive)
        assertEquals(updatedService.activationStatus, ServiceStatus.ACTIVATION_PENDING)
    }

    @Test
    fun testDeactivationFinalized() {
        val updatedService = processor.processActivationState(currentDeactivationPending, newInactive)
        assertEquals(updatedService.activationStatus, ServiceStatus.INACTIVE)
    }

    @Test
    fun testDeactivationPending() {
        val updatedService = processor.processActivationState(currentDeactivationPending, newOldStateActive)
        assertEquals(updatedService.activationStatus, ServiceStatus.DEACTIVATION_PENDING)
    }

    @Test
    fun testDesiredStateRemainDeactivationPending() {
        val updatedService = processor.processActivationState(currentDeactivationPending, newActive)
        assertEquals(updatedService.activationStatus, ServiceStatus.DEACTIVATION_PENDING)
    }
}
