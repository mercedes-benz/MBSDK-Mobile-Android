package com.daimler.mbmobilesdk.services

import com.daimler.mbmobilesdk.serviceactivation.PreconditionStateActions
import com.daimler.mbmobilesdk.serviceactivation.ServicePreconditionState
import org.junit.Before
import org.junit.Test

class ServicePreconditionStateTest : PreconditionStateActions {

    private var stateText: String? = null

    @Before
    fun setup() {
        stateText = null
    }

    @Test
    fun testActionsCalled() {
        assertActionCalled(ServicePreconditionState.Pending, PENDING)
        assertActionCalled(ServicePreconditionState.Fulfilled, FULFILLED)
        assertActionCalled(ServicePreconditionState.NoRights, NO_RIGHTS)
    }

    private fun assertActionCalled(state: ServicePreconditionState, text: String) {
        state.showState(this)
        assert(stateText == text)
    }

    override fun showPendingState() {
        stateText = PENDING
    }

    override fun showFulfilledState() {
        stateText = FULFILLED
    }

    override fun showNoRightsToChangeState() {
        stateText = NO_RIGHTS
    }

    private companion object {
        private const val PENDING = "PENDING"
        private const val FULFILLED = "FULFILLED"
        private const val NO_RIGHTS = "NO_RIGHTS"
    }
}