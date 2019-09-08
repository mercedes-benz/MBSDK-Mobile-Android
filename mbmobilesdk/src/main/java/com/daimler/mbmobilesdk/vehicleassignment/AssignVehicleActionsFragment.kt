package com.daimler.mbmobilesdk.vehicleassignment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.application
import com.daimler.mbmobilesdk.vehiclestage.StageConfig
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

internal class AssignVehicleActionsFragment : BaseAssignVehicleFragment<AssignVehicleActionsViewModel>() {

    override fun createViewModel(): AssignVehicleActionsViewModel {
        val initialAssignment = arguments?.getBoolean(EXTRA_INITIAL_ASSIGNMENT, true) == true
        val stageConfig: StageConfig = arguments?.getParcelable(EXTRA_STAGE_CONFIG) ?: StageConfig.EMPTY
        val factory = AssignVehicleActionsViewModelFactory(application, initialAssignment, stageConfig)
        return ViewModelProviders.of(this, factory).get(AssignVehicleActionsViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_assign_vehicle_actions

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        viewModel.apply {
            assignWithQrEvent.observe(this@AssignVehicleActionsFragment, onAssignWithQr())
            assignWithVinEvent.observe(this@AssignVehicleActionsFragment, onAssignWithVin())
            infoCallEvent.observe(this@AssignVehicleActionsFragment, onInfoCall())
            searchRetailerEvent.observe(this@AssignVehicleActionsFragment, onSearchRetailer())
            helpEvent.observe(this@AssignVehicleActionsFragment, onHelp())
            laterEvent.observe(this@AssignVehicleActionsFragment, onLater())
        }
    }

    private fun onAssignWithQr() = LiveEventObserver<Unit> {
        notifyShowQrCode()
    }

    private fun onAssignWithVin() = LiveEventObserver<Unit> {
        notifyAssignWithVin(null)
    }

    private fun onInfoCall() = LiveEventObserver<Unit> {
        notifyInfoCall()
    }

    private fun onSearchRetailer() = LiveEventObserver<Unit> {
        notifySearchRetailer()
    }

    private fun onHelp() = LiveEventObserver<Unit> {
        notifyShowHelp()
    }

    private fun onLater() = LiveEventObserver<Unit> {
        notifyCancel()
    }

    companion object {
        private const val EXTRA_INITIAL_ASSIGNMENT = "extra.vehicle.assignment.actions.is.initial"
        private const val EXTRA_STAGE_CONFIG = "extra.vehicle.assignment.actions.stages.config"

        fun newInstance(initialAssignment: Boolean, stageConfig: StageConfig? = null): AssignVehicleActionsFragment {
            val fragment = AssignVehicleActionsFragment()
            Bundle().apply {
                putBoolean(EXTRA_INITIAL_ASSIGNMENT, initialAssignment)
                putParcelable(EXTRA_STAGE_CONFIG, stageConfig)
                fragment.arguments = this
            }
            return fragment
        }
    }
}