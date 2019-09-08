package com.daimler.mbmobilesdk.vehicleassignment

import android.app.Activity
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

class AssignVehicleQRActivity : QRCodeReaderActivity<AssignVehicleQRViewModel>() {

    override fun createViewModel(): AssignVehicleQRViewModel {
        val factory = AssignVehicleQRViewModelFactory(getString(R.string.assign_qr_description))
        return ViewModelProviders.of(this, factory)
            .get(AssignVehicleQRViewModel::class.java)
    }

    override fun onQrCodeFound(qr: String) {
        viewModel.sendUrl(qr)
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        viewModel.vehicleAssignedEvent.observe(this, onVehicleAssigned())
        viewModel.invalidCodeEvent.observe(this, onInvalidCode())
        viewModel.authErrorEvent.observe(this, onAuthenticationError())
        viewModel.networkErrorEvent.observe(this, onNetworkError())
    }

    private fun onVehicleAssigned() = LiveEventObserver<String> {
        val intent = Intent()
        intent.putExtra(ARG_ASSIGNED_VEHICLE_VIN, it)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun onInvalidCode() = LiveEventObserver<String> {
        MBDialogFragment.Builder(0).apply {
            withMessage(getString(R.string.assign_qr_invalid))
            withPositiveButtonText(getString(R.string.general_okay))
            withListener(object : MBDialogFragment.DialogListener {
                override fun onNegativeAction(id: Int) = Unit

                override fun onPositiveAction(id: Int) {
                    resetCamera()
                }
            })
        }.build().show(supportFragmentManager, null)
    }

    private fun onAuthenticationError() = LiveEventObserver<Unit> {
        MBDialogFragment.Builder(0).apply {
            withMessage(getString(R.string.general_error_msg))
            withPositiveButtonText(getString(R.string.general_ok))
            withListener(object : MBDialogFragment.DialogListener {
                override fun onNegativeAction(id: Int) = Unit

                override fun onPositiveAction(id: Int) {
                    resetCamera()
                }
            })
        }.build().show(supportFragmentManager, null)
    }

    private fun onNetworkError() = LiveEventObserver<Unit> {
        MBDialogFragment.Builder(0).apply {
            withMessage(getString(R.string.general_error_network_msg))
            withPositiveButtonText(getString(R.string.general_ok))
            withListener(object : MBDialogFragment.DialogListener {
                override fun onNegativeAction(id: Int) = Unit

                override fun onPositiveAction(id: Int) {
                    resetCamera()
                }
            })
        }.build().show(supportFragmentManager, null)
    }

    companion object {
        const val ARG_ASSIGNED_VEHICLE_VIN = "arg.qr.assigned.vehicle.vin"
    }
}