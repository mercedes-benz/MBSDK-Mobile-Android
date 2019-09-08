package com.daimler.mbmobilesdk.serviceactivation

interface PreconditionStateActions {

    fun showPendingState()
    fun showFulfilledState()
    fun showNoRightsToChangeState()
}