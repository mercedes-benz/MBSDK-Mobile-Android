package com.daimler.mbmobilesdk.vehicleassignment

import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.components.fragments.MBBaseViewModelFragment

internal class CodeValidationFragment : MBBaseViewModelFragment<AssignmentCodeViewModel>() {

    override fun createViewModel(): AssignmentCodeViewModel =
        ViewModelProviders.of(activity!!).get(AssignmentCodeViewModel::class.java)

    override fun getLayoutRes(): Int = R.layout.fragment_code_validation

    override fun getModelId(): Int = BR.model

    companion object {
        fun newInstance() = CodeValidationFragment()
    }
}