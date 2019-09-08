package com.daimler.mbmobilesdk.vehicleassignment

import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.createBasicViewModel

internal class AssignVehicleHelpFragment : BaseAssignVehicleFragment<AssignVehicleHelpViewModel>() {

    override fun createViewModel(): AssignVehicleHelpViewModel = createBasicViewModel()

    override fun getLayoutRes(): Int = R.layout.fragment_assign_vehicle_help

    override fun getModelId(): Int = BR.model

    companion object {

        fun newInstance(): AssignVehicleHelpFragment = AssignVehicleHelpFragment()
    }
}