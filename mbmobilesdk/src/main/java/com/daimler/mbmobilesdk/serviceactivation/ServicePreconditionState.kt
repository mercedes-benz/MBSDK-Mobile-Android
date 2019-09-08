package com.daimler.mbmobilesdk.serviceactivation

sealed class ServicePreconditionState {

    abstract fun showState(actions: PreconditionStateActions)

    object Pending : ServicePreconditionState() {
        override fun showState(actions: PreconditionStateActions) {
            actions.showPendingState()
        }
    }

    object Fulfilled : ServicePreconditionState() {
        override fun showState(actions: PreconditionStateActions) {
            actions.showFulfilledState()
        }
    }

    object NoRights : ServicePreconditionState() {
        override fun showState(actions: PreconditionStateActions) {
            actions.showNoRightsToChangeState()
        }
    }
}