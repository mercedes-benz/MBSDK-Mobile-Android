package com.daimler.mbmobilesdk.vehicleassignment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.showOkayDialog
import com.daimler.mbmobilesdk.utils.extensions.simpleTextObserver
import com.daimler.mbmobilesdk.utils.extensions.withPositiveAction
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.hideKeyboard
import com.daimler.mbuikit.utils.extensions.replaceFragment

internal class AssignmentCodeActivity : MBBaseViewModelActivity<AssignmentCodeViewModel>() {

    override fun createViewModel(): AssignmentCodeViewModel =
        ViewModelProviders.of(this).get(AssignmentCodeViewModel::class.java)

    override fun getLayoutRes(): Int = R.layout.activity_assignment_code

    override fun getModelId(): Int = BR.model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val vin = intent.getStringExtra(ARG_VIN)
            viewModel.vin.set(vin)
            val fragment = if (!vin.isNullOrBlank()) {
                CodeValidationFragment.newInstance()
            } else {
                CodeIdentificationFragment.newInstance()
            }
            replaceFragment(R.id.content_container, fragment)
        }
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        viewModel.progressVisible.observe(this, dataProgressEvent())
        viewModel.codeGeneratedEvent.observe(this, codeGenerationEvent())
        viewModel.codeValidationEvent.observe(this, codeValidationEvent())
        viewModel.noRifSupportEvent.observe(this, noRifSupportEvent())
        viewModel.legacyVehicleEvent.observe(this, legacyVehicleEvent())
        viewModel.invalidVinEvent.observe(this, invalidVinEvent())
        viewModel.alreadyAssignedEvent.observe(this, alreadyAssignedEvent())
        viewModel.errorEvent.observe(this, errorEvent())
    }

    private fun dataProgressEvent() = Observer<Boolean> {
        if (it == true) hideKeyboard()
    }

    private fun codeGenerationEvent() = LiveEventObserver<String> {
        setOkayResult(it, false, false)
        replaceFragment(R.id.content_container, CodeValidationFragment.newInstance())
    }

    private fun codeValidationEvent() = LiveEventObserver<String> {
        setOkayResult(it, true, false)
        showOkayDialog(getString(R.string.assign_success_title)) {
            finish()
        }
    }

    private fun invalidVinEvent() = LiveEventObserver<Unit> {
            MBDialogFragment.Builder(0).apply {
                withTitle(getString(R.string.assign_error_invalid_vin_title))
                withMessage(getString(R.string.assign_error_invalid_vin_message))
                withPositiveButtonText(getString(R.string.general_ok))
            }.build().show(supportFragmentManager, null)
    }

    private fun alreadyAssignedEvent() = LiveEventObserver<Unit> {
            MBDialogFragment.Builder(0).apply {
                withTitle(getString(R.string.assign_error_already_paired_message))
                withMessage(getString(R.string.assign_error_already_paired_message))
                withPositiveButtonText(getString(R.string.assign_instruction_retailer_button_title))
                withPositiveAction { startMerchantSearch() }
                withNegativeButtonText(getString(R.string.general_ok))
            }.build().show(supportFragmentManager, null)
    }

    private fun noRifSupportEvent() = LiveEventObserver<String> {
        setOkayResult(it, false, false)
        startActivity(RifActivity.getStartIntent(this, it, false))
        finish()
    }

    private fun legacyVehicleEvent() = LiveEventObserver<String> {
        setOkayResult(it, true, true)
        startActivity(AssignLegacyActivity.getStartIntent(this))
        finish()
    }

    private fun errorEvent() = simpleTextObserver()

    private fun startMerchantSearch() {
        startActivity(Intent(this, SearchMerchantActivity::class.java))
    }

    private fun setOkayResult(finOrVin: String, validated: Boolean, legacy: Boolean) {
        Intent().apply {
            putExtra(ARG_ASSIGNED_VEHICLE_VIN, finOrVin)
            putExtra(ARG_VIN_VALIDATED, validated)
            putExtra(ARG_IS_LEGACY_VEHICLE, legacy)
            setResult(RESULT_OK, this)
        }
    }

    companion object {

        const val ARG_ASSIGNED_VEHICLE_VIN = "arg.code.vehicle.assigned.vin"
        const val ARG_VIN_VALIDATED = "arg.code.vehicle.vin.validated"
        const val ARG_IS_LEGACY_VEHICLE = "arg.code.vehicle.legacy"
        private const val ARG_VIN = "arg.code.vin"

        fun getStartIntent(context: Context, vin: String?): Intent {
            val intent = Intent(context, AssignmentCodeActivity::class.java)
            intent.putExtra(ARG_VIN, vin)
            return intent
        }
    }
}