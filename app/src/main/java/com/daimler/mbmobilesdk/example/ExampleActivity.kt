package com.daimler.mbmobilesdk.example

import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbmobilesdk.example.car.SendToCarError
import com.daimler.mbmobilesdk.example.databinding.ActivityExampleBinding
import com.daimler.mbmobilesdk.example.login.LoginActivity
import com.daimler.mbmobilesdk.example.utils.ViewModelActivity
import com.daimler.mbmobilesdk.example.utils.observe
import com.daimler.mbmobilesdk.example.vehicleSelection.VehicleSelectionActivity

class ExampleActivity : ViewModelActivity<ExampleViewModel, ActivityExampleBinding>() {

    override fun createViewModel(): ExampleViewModel =
        ViewModelProvider(this).get(ExampleViewModel::class.java)

    override fun getLayoutResId(): Int = R.layout.activity_example

    override fun getModelId(): Int = BR.viewModel

    override fun onBindingCreated(viewModel: ExampleViewModel, binding: ActivityExampleBinding) {
        super.onBindingCreated(viewModel, binding)
        setupUI()
    }

    private fun setupUI() {
        subscribeToLoginEvent()
        setupSelectVehicleButton()
        viewModel.requestVehicleImage()
        viewModel.sendToCarError.observe(this@ExampleActivity) { onSendToCarErrorEvent(it) }
        viewModel.sendToCarSuccess.observe(this@ExampleActivity) { onSendToCarSuccessEvent() }
    }

    private fun onSendToCarErrorEvent(error: SendToCarError) {
        AlertDialog.Builder(this)
            .setMessage(error.text)
            .setPositiveButton(R.string.ok, null)
            .create()
            .show()
    }

    private fun onSendToCarSuccessEvent() {
        AlertDialog.Builder(this)
            .setMessage(R.string.s2c_poi_successfully_sent)
            .setPositiveButton(R.string.ok, null)
            .create()
            .show()
    }

    private fun subscribeToLoginEvent() {
        viewModel.onLoginEvent.observe(this) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(
                intent,
                LOGIN_REQUEST
            )
        }
    }

    private fun setupSelectVehicleButton() {
        viewModel.onSelectVehicleEvent.observe(this) {
            val intent = Intent(this, VehicleSelectionActivity::class.java)
            startActivityForResult(
                intent,
                VEHICLE_REQUEST
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOGIN_REQUEST -> {
                viewModel.isUserLoggedIn.postValue(MBIngressKit.authenticationService().isLoggedIn())
            }
            VEHICLE_REQUEST -> {
                viewModel.requestVehicleImage()
                viewModel.requestCurrentVehicleStatus()
            }
            else -> {
                // Unknown request code
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.disconnect()
    }

    companion object {
        private const val LOGIN_REQUEST = 1
        private const val VEHICLE_REQUEST = 2
    }
}
