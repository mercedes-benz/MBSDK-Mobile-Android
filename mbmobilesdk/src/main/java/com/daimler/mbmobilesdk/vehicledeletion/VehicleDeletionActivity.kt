package com.daimler.mbmobilesdk.vehicledeletion

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.checkParameterNotNull
import com.daimler.mbmobilesdk.utils.extensions.simpleTextObserver
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import kotlinx.android.synthetic.main.activity_vehicle_deletion.*

internal class VehicleDeletionActivity : MBBaseViewModelActivity<VehicleDeletionViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_vehicle_deletion

    override fun getModelId(): Int = BR.model

    override fun createViewModel(): VehicleDeletionViewModel {
        val vehicles: List<DeletableVehicle>? = intent.getParcelableArrayListExtra(ARG_VEHICLES)
        checkParameterNotNull("vehicles", vehicles)
        val factory = VehicleDeletionViewModelFactory(application, vehicles!!)
        return ViewModelProviders.of(this, factory).get(VehicleDeletionViewModel::class.java)
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        overridePendingTransition(R.anim.slide_up, R.anim.stay)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel.apply {
            cancelEvent.observe(this@VehicleDeletionActivity, onCancel())
            deleteEvent.observe(this@VehicleDeletionActivity, onDeleteVehicle())
            vehicleDeletedEvent.observe(this@VehicleDeletionActivity, onVehicleDeleted())
            errorEvent.observe(this@VehicleDeletionActivity, onError())
        }
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) overridePendingTransition(R.anim.stay, R.anim.slide_down)
    }

    private fun onCancel() = LiveEventObserver<Unit> {
        finish()
    }

    private fun onDeleteVehicle() = LiveEventObserver<DeletableVehicle> {
        MBDialogFragment.Builder(0).apply {
            withMessage(String.format(getString(R.string.selector_remove_car), it.model))
            withPositiveButtonText(getString(R.string.general_yes))
            withNegativeButtonText(getString(R.string.general_no))
            withListener(object : MBDialogFragment.DialogListener {
                override fun onNegativeAction(id: Int) = Unit

                override fun onPositiveAction(id: Int) {
                    viewModel.deleteVehicle(it.finOrVin)
                }
            })
        }.build().show(supportFragmentManager, null)
    }

    private fun onVehicleDeleted() = LiveEventObserver<String> {
        finishWithDeletedVehicle(it)
    }

    private fun onError() = simpleTextObserver()

    private fun finishWithDeletedVehicle(finOrVin: String) {
        Intent().apply {
            putExtra(ARG_DELETED_VEHICLE_VIN, finOrVin)
            setResult(RESULT_OK, this)
        }
        finish()
    }

    companion object {

        const val ARG_DELETED_VEHICLE_VIN = "arg.deletion.vehicle.vin"

        private const val ARG_VEHICLES = "arg.vehicles.deletable"

        fun getStartIntent(context: Context, vehicles: List<DeletableVehicle>): Intent {
            return Intent(context, VehicleDeletionActivity::class.java).apply {
                putExtra(ARG_VEHICLES, ArrayList(vehicles))
            }
        }
    }
}