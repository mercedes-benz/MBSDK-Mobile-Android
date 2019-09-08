package com.daimler.mbmobilesdk.vehicleassignment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.inputmethod.EditorInfo
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.dialogs.DealerActionBottomSheetDialogFragment
import com.daimler.mbmobilesdk.utils.extensions.isResolvable
import com.daimler.mbmobilesdk.utils.extensions.simpleTextObserver
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.merchants.Address
import com.daimler.mbcarkit.business.model.merchants.MerchantResponse
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.hideKeyboard
import kotlinx.android.synthetic.main.activity_search_merchant.*

class SearchMerchantActivity : MBBaseViewModelActivity<SearchMerchantViewModel>() {

    override fun createViewModel(): SearchMerchantViewModel {
        val factory = SearchMerchantViewModelFactory(application)
        return ViewModelProviders.of(this, factory).get(SearchMerchantViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_search_merchant

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        edit_merchant_search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                true
            } else {
                false
            }
        }

        viewModel.cancelClickedEvent.observe(this, onCancelClick())
        viewModel.closeClickedEvent.observe(this, onCloseClick())
        viewModel.onBackClickedEvent.observe(this, onBackClick())
        viewModel.onPhoneClickedEvent.observe(this, onCallClick())
        viewModel.onDirectionsClickedEvent.observe(this, onDirectionsClick())
        viewModel.onDealerActionDialogShowEvent.observe(this, onDealerActionDialogShow())
        viewModel.errorEvent.observe(this, onErrorEvent())
    }

    private fun onCloseClick() = LiveEventObserver<Unit> {
        edit_merchant_search.text?.clear()
    }

    private fun onCancelClick() = LiveEventObserver<Unit> {
        edit_merchant_search.text?.clear()
    }

    private fun onBackClick() = LiveEventObserver<Unit> {
        onBackPressed()
    }

    private fun onCallClick() = LiveEventObserver<String> {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${Uri.parse(it)}")
        startActivity(intent)
    }

    private fun onDirectionsClick() = LiveEventObserver<Address> {
        val gmmIntentUri = Uri.parse("geo:0,0?q=${it.street}, ${it.city}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.isResolvable(this)) {
            startActivity(mapIntent)
        }
    }

    private fun onDealerActionDialogShow() = LiveEventObserver<MerchantResponse> {
        if (it.communication?.phone != null || it.address != null || it.id != null) {
            DealerActionBottomSheetDialogFragment
                .newInstance(
                    it.id,
                    MBCarKit.selectedFinOrVin(),
                    it.legalName,
                    it.communication?.phone,
                    it.address,
                    arrayOf() // TODO: fix later
                )
                .show(supportFragmentManager, null)
        }
    }

    private fun onErrorEvent() = simpleTextObserver()

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, SearchMerchantActivity::class.java)
        }
    }
}
