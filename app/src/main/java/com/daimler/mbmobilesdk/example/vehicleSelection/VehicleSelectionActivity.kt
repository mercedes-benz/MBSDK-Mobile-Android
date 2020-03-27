package com.daimler.mbmobilesdk.example.vehicleSelection

import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.daimler.mbmobilesdk.example.BR
import com.daimler.mbmobilesdk.example.R
import com.daimler.mbmobilesdk.example.databinding.ActivityVehicleSelectionBinding
import com.daimler.mbmobilesdk.example.utils.ViewModelActivity
import com.daimler.mbmobilesdk.example.utils.observe

class VehicleSelectionActivity : ViewModelActivity<VehicleSelectionViewModel, ActivityVehicleSelectionBinding>() {

    override fun createViewModel(): VehicleSelectionViewModel =
        ViewModelProvider(this).get(VehicleSelectionViewModel::class.java)

    override fun getLayoutResId(): Int = R.layout.activity_vehicle_selection

    override fun getModelId(): Int = BR.viewModel

    override fun onBindingCreated(
        viewModel: VehicleSelectionViewModel,
        binding: ActivityVehicleSelectionBinding
    ) {
        super.onBindingCreated(viewModel, binding)
        subscribeToSelectionEvent()
        binding.fragmentVehicleSelectionRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayout.VERTICAL
            )
        )
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchVehiclesForSelection()
    }

    private fun subscribeToSelectionEvent() {
        viewModel.onVehicleSelectedEvent.observe(this) {
            setResult(RESULT_OK)
            finish()
        }
    }
}
