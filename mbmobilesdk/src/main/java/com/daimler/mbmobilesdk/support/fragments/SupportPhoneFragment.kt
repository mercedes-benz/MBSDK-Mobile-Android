package com.daimler.mbmobilesdk.support.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.dialogs.SupportAdditionalDataDialog
import com.daimler.mbmobilesdk.support.SupportUtils
import com.daimler.mbmobilesdk.support.viewmodels.SupportPhoneViewModel
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbuikit.components.fragments.MBBaseViewModelFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

class SupportPhoneFragment : MBBaseViewModelFragment<SupportPhoneViewModel>() {

    override fun getLayoutRes(): Int = R.layout.fragment_support_phone

    override fun getModelId(): Int = BR.model

    override fun createViewModel(): SupportPhoneViewModel =
            ViewModelProviders.of(this,
                    ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))
                    .get(SupportPhoneViewModel::class.java)

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        viewModel.clickEvent.observe(this, clickEvent())
    }

    override fun onPause() {
        viewModel.storeState()
        super.onPause()
    }

    private fun clickEvent() = LiveEventObserver<Int> {
        when (it) {
            SupportPhoneViewModel.CALL_CAC -> callCac()
            SupportPhoneViewModel.DATA_INFORMATION -> showDataDialog()
        }
    }

    private fun callCac() {
        val canDoPhoneCall = (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
        if (canDoPhoneCall) {
            try {
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:${viewModel.cacPhoneNumber.value}")))
                viewModel.sendAdditionalCallData()
            } catch (e: SecurityException) {
                MBLoggerKit.e(e.localizedMessage)
            }
        } else {
            SupportUtils.requestPermissions(activity!!, MB_REQUEST_PERMISSIONS, Manifest.permission.CALL_PHONE)
        }
    }

    private fun showDataDialog() {
        SupportAdditionalDataDialog(getString(R.string.rssm_call_data_hint_content)).show(fragmentManager, null)
    }

    companion object {
        const val MB_REQUEST_PERMISSIONS = 7
        const val mbsupportkit_CALL_EXTRA_INFO_DIALOG = 12424
        fun newInstance() = SupportPhoneFragment()
    }
}
