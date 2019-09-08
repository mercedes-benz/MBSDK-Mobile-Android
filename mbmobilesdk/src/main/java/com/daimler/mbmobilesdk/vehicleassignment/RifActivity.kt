package com.daimler.mbmobilesdk.vehicleassignment

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.databinding.ActivityRifBinding
import com.daimler.mbmobilesdk.utils.extensions.simpleTextObserver
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

internal class RifActivity : MBBaseViewModelActivity<RifViewModel>(), MBDialogFragment.DialogListener {

    override fun createViewModel(): RifViewModel {
        if (!intent.hasExtra(ARG_SHOW_CHECK_ACTIVATION_BUTTON) || !intent.hasExtra(ARG_VEHICLE_VIN)) {
            throw IllegalArgumentException("Create the intent for this activity through getStartIntent()!")
        }
        val vehicle = intent.getStringExtra(ARG_VEHICLE_VIN)
        val showCheckActivationButton =
            intent.getBooleanExtra(ARG_SHOW_CHECK_ACTIVATION_BUTTON, false)
        val factory = RifViewModelFactory(application, vehicle, showCheckActivationButton)
        return ViewModelProviders.of(this, factory).get(RifViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_rif

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        overridePendingTransition(R.anim.slide_up, R.anim.stay)

        val toolbar = (binding as ActivityRifBinding).toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel.closeScreenEvent.observe(this, onCloseScreenEvent())
        viewModel.vehicleActivationEvent.observe(this, onVehicleActivationEvent())
        viewModel.errorEvent.observe(this, onError())
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) overridePendingTransition(R.anim.stay, R.anim.slide_down)
    }

    override fun onPositiveAction(id: Int) {
        finish()
    }

    override fun onNegativeAction(id: Int) = Unit

    private fun onCloseScreenEvent() = LiveEventObserver<Unit> {
        finish()
    }

    private fun onVehicleActivationEvent() =
        LiveEventObserver<RifViewModel.VehicleActivationEvent> { event ->
            event.vehicle?.let {
                if (event.isActivated) {
                    finish()
                } else {
                    MBDialogFragment.Builder(ID_DIALOG_NOT_ACTIVATED).apply {
                        withMessage(getString(R.string.assign_activation_process))
                        withPositiveButtonText(getString(R.string.general_okay))
                        withListener(this@RifActivity)
                    }.build().show(supportFragmentManager, null)
                }
            }
        }

    private fun onError() = simpleTextObserver()

    companion object {

        private const val ARG_VEHICLE_VIN = "arg.rif.vehicle.vin"
        private const val ARG_SHOW_CHECK_ACTIVATION_BUTTON = "arg.rif.check.button.activation"

        private const val ID_DIALOG_NOT_ACTIVATED = 1

        fun getStartIntent(
            context: Context,
            finOrVin: String,
            showCheckActivationButton: Boolean
        ): Intent {
            val intent = Intent(context, RifActivity::class.java)
            intent.putExtra(ARG_VEHICLE_VIN, finOrVin)
            intent.putExtra(ARG_SHOW_CHECK_ACTIVATION_BUTTON, showCheckActivationButton)
            return intent
        }
    }
}