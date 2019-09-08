package com.daimler.mbmobilesdk.dialogs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.databinding.DialogBottomActionDealerBinding
import com.daimler.mbmobilesdk.utils.extensions.isResolvable
import com.daimler.mbcarkit.business.model.merchants.Address
import com.daimler.mbcarkit.business.model.vehicle.VehicleDealer
import com.daimler.mbuikit.components.ViewModelOwner
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DealerActionBottomSheetDialogFragment : BottomSheetDialogFragment(), ViewModelOwner<DealerActionDialogViewModel> {

    lateinit var viewModel: DealerActionDialogViewModel
        private set

    lateinit var binding: ViewDataBinding
        private set

    override fun createViewModel(): DealerActionDialogViewModel {

        val factory = object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DealerActionDialogViewModel(
                    arguments?.getString(ARG_DEALER_ID),
                    arguments?.getString(ARG_VEHICLE_FIN_OR_VIN),
                    arguments?.getString(ARG_DEALER_NAME),
                    arguments?.getString(ARG_PHONE),
                    arguments?.getString(ARG_ADDRESS_CITY),
                    arguments?.getString(ARG_ADDRESS_STREET),
                    arguments?.getParcelableArray(ARG_DEALERS) as Array<VehicleDealer>
                    ) as T
            }
        }

        return ViewModelProviders.of(this, factory).get(DealerActionDialogViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.dialog_bottom_action_dealer

    override fun getModelId(): Int = BR.model

    override fun provideLifecycleOwner(): LifecycleOwner = this

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = createViewModel()
        binding = DataBindingUtil.inflate<DialogBottomActionDealerBinding>(
            inflater,
            getLayoutRes(),
            container,
            false
        )
        binding.apply {
            setVariable(getModelId(), viewModel)
            lifecycleOwner = provideLifecycleOwner()
            onBindingCreated(binding)
        }
        return binding.root
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        viewModel.onNavigationClickEvent.observe(this, onNavigationClicked())
        viewModel.onCallClickEvent.observe(this, onCallClicked())
        viewModel.onCancelClickEvent.observe(this, onCancelClicked())
        viewModel.onSaveClickEvent.observe(this, onSaveClicked())
        viewModel.onSaveErrorEvent.observe(this, onSaveErrorEvent())
    }

    private fun onNavigationClicked() = LiveEventObserver<Pair<String?, String?>> {
        val gmmIntentUri = Uri.parse("geo:0,0?q=${it.second}, ${it.first}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context?.let { context ->
            if (mapIntent.isResolvable(context)) {
                startActivity(mapIntent)
            }
        }
    }

    private fun onCallClicked() = LiveEventObserver<String> {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${Uri.parse(it)}")
        startActivity(intent)
    }

    private fun onCancelClicked() = LiveEventObserver<Unit> {
        dismiss()
    }

    private fun onSaveClicked() = LiveEventObserver<Unit> {
        dismiss()
    }

    private fun onSaveErrorEvent() = LiveEventObserver<String?> {
        MBDialogFragment.Builder()
            .withTitle(getString(R.string.general_error_title))
            .withMessage(it ?: getString(R.string.general_error_msg))
            .withPositiveButton(getString(R.string.general_ok)) { }
    }

    companion object {
        private const val ARG_DEALER_ID = "arg.dialog.dealer.id"
        private const val ARG_VEHICLE_FIN_OR_VIN = "arg.dialog.vehicle.finOrVin"
        private const val ARG_DEALER_NAME = "arg.dialog.dealer.name"
        private const val ARG_PHONE = "arg.dialog.phone"
        private const val ARG_ADDRESS_CITY = "arg.dialog.address.city"
        private const val ARG_ADDRESS_STREET = "arg.dialog.address.street"
        private const val ARG_DEALERS = "arg.dialog.dealers"

        fun newInstance(
            dealerId: String?,
            finOrVin: String?,
            dealerName: String?,
            phone: String?,
            address: Address?,
            dealers: Array<VehicleDealer>?
        ): DealerActionBottomSheetDialogFragment =
            DealerActionBottomSheetDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DEALER_ID, dealerId)
                    putString(ARG_VEHICLE_FIN_OR_VIN, finOrVin)
                    putString(ARG_DEALER_NAME, dealerName)
                    putString(ARG_PHONE, phone)
                    putString(ARG_ADDRESS_CITY, address?.city)
                    putString(ARG_ADDRESS_STREET, address?.street)
                    putParcelableArray(ARG_DEALERS, dealers)
                }
            }
    }
}