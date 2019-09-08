package com.daimler.mbmobilesdk.vehicleassignment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.support.fragments.SupportHolderFragment
import com.daimler.mbmobilesdk.utils.extensions.createBasicViewModel
import com.daimler.mbmobilesdk.vehiclestage.StageConfig
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.utils.extensions.hasPermission
import com.daimler.mbuikit.utils.extensions.replaceFragment
import com.daimler.mbuikit.utils.extensions.requestPermission

class AssignVehicleActivity :
    MBBaseViewModelActivity<AssignVehicleViewModel>(),
    AssignVehicleActionsCallback {

    override fun createViewModel(): AssignVehicleViewModel = createBasicViewModel()

    override fun getLayoutRes(): Int = R.layout.activity_assign_vehicle

    override fun getModelId(): Int = BR.model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val initialAssignment = intent.getBooleanExtra(ARG_INITIAL_ASSIGNMENT, true)
            val stageConfig: StageConfig? = intent.getParcelableExtra(ARG_STAGE_CONFIG)
            replaceFragment(R.id.content_container, AssignVehicleActionsFragment.newInstance(initialAssignment, stageConfig))
        }
    }

    override fun onShowQrCode() {
        qrSelected()
    }

    override fun onAssignWithVin(vin: String?) {
        startActivityForResult(AssignmentCodeActivity.getStartIntent(this, vin),
            REQ_CODE_ASSIGNMENT)
    }

    override fun onShowHelp() {
        showHelpFragment()
    }

    override fun onInfoCall() {
        showSupport()
    }

    override fun onSearchRetailer() {
        startMerchantSearch()
    }

    override fun onCancel() {
        finish()
    }

    private fun showQr() {
        startActivityForResult(Intent(this, AssignVehicleQRActivity::class.java),
            REQ_CODE_QR)
    }

    private fun qrSelected() {
        if (hasPermission(Manifest.permission.CAMERA)) {
            showQr()
        } else {
            requestPermission(Manifest.permission.CAMERA, REQ_CODE_PERM_CAMERA)
        }
    }

    private fun showSupport() {
        replaceFragment(R.id.content_container, SupportHolderFragment.newInstance(),
            addToBackStack = true)
    }

    private fun startMerchantSearch() {
        startActivity(Intent(this, SearchMerchantActivity::class.java))
    }

    private fun showHelpFragment() {
        replaceFragment(R.id.content_container, AssignVehicleHelpFragment.newInstance(),
            addToBackStack = true)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQ_CODE_PERM_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showQr()
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_CODE_QR -> {
                val finOrVin = data?.getStringExtra(AssignVehicleQRActivity.ARG_ASSIGNED_VEHICLE_VIN)
                if (resultCode == RESULT_OK && finOrVin != null) {
                    MBLoggerKit.d("Assigned $finOrVin through QR.")
                    finishWithResult(finOrVin, true, false)
                }
            }
            REQ_CODE_ASSIGNMENT -> {
                val finOrVin = data?.getStringExtra(AssignmentCodeActivity.ARG_ASSIGNED_VEHICLE_VIN)
                val validated = data?.getBooleanExtra(AssignmentCodeActivity.ARG_VIN_VALIDATED, false) == true
                val legacy = data?.getBooleanExtra(AssignmentCodeActivity.ARG_IS_LEGACY_VEHICLE, false) ?: false
                if (resultCode == RESULT_OK && finOrVin != null) {
                    MBLoggerKit.d("Assigned $finOrVin through VAC.")
                    finishWithResult(finOrVin, validated, legacy)
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun finishWithResult(finOrVin: String, validated: Boolean, isLegacyVehicle: Boolean) {
        Intent().apply {
            putExtra(ARG_ASSIGNED_VEHICLE_VIN, finOrVin)
            putExtra(ARG_IS_VALIDATED, validated)
            putExtra(ARG_IS_LEGACY_VEHICLE, isLegacyVehicle)
            setResult(RESULT_OK, this)
        }
        finish()
    }

    companion object {
        private const val REQ_CODE_PERM_CAMERA = 11
        private const val REQ_CODE_QR = 21
        private const val REQ_CODE_ASSIGNMENT = 22

        private const val ARG_INITIAL_ASSIGNMENT = "arg.vehicle.assignment.is.initial"
        private const val ARG_STAGE_CONFIG = "arg.vehicle.assignment.stage.config"

        const val ARG_ASSIGNED_VEHICLE_VIN = "arg.vehicle.assigned.vin"
        const val ARG_IS_VALIDATED = "arg.vehicle.validated"
        const val ARG_IS_LEGACY_VEHICLE = "arg.vehicle.legacy"

        fun getStartIntent(
            context: Context,
            initialAssignment: Boolean,
            stageConfig: StageConfig? = null
        ): Intent {
            return Intent(context, AssignVehicleActivity::class.java).apply {
                putExtra(ARG_INITIAL_ASSIGNMENT, initialAssignment)
                putExtra(ARG_STAGE_CONFIG, stageConfig)
            }
        }
    }
}