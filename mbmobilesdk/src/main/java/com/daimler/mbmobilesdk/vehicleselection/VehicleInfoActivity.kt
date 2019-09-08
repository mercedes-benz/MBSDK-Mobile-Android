package com.daimler.mbmobilesdk.vehicleselection

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.serviceactivation.GenericViewModelFactory
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbuikit.components.activities.MBBaseToolbarActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

class VehicleInfoActivity : MBBaseToolbarActivity<VehicleInfoViewModel>() {

    override val buttonMode: Int = BUTTON_BACK

    override val toolbarTitle: String by lazy { getString(R.string.vehicle_information_title) }

    override fun createViewModel(): VehicleInfoViewModel {
        val vehicle: VehicleInfo = intent.getParcelableExtra(ARG_VEHICLE)
            ?: throw IllegalArgumentException("You need to provide a vehicle.")
        val factory = GenericViewModelFactory {
            VehicleInfoViewModel(application, vehicle)
        }
        return ViewModelProviders.of(this, factory).get(VehicleInfoViewModel::class.java)
    }

    override fun getContentLayoutRes(): Int = R.layout.activity_vehicle_info

    override fun getContentModelId(): Int = BR.model

    override fun onContentBindingCreated(binding: ViewDataBinding) {
        super.onContentBindingCreated(binding)
        viewModel.errorEvent.observe(this, onError())
    }

    private fun onError() = LiveEventObserver<String> {
    }

    companion object {
        private const val ARG_VEHICLE = "arg.vehicle"

        fun getStartIntent(context: Context, vehicle: VehicleInfo): Intent {
            val intent = Intent(context, VehicleInfoActivity::class.java)
            intent.putExtra(ARG_VEHICLE, vehicle)
            return intent
        }
    }
}