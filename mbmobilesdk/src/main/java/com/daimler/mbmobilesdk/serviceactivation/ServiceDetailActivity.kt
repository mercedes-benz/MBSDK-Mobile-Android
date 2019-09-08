package com.daimler.mbmobilesdk.serviceactivation

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.serviceactivation.precondition.ServicePreconditionActivity
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.google.gson.Gson

class ServiceDetailActivity : MBBaseViewModelActivity<ServiceDetailViewModel>() {

    override fun createViewModel(): ServiceDetailViewModel {
        val json = intent.getStringExtra(ARG_SERVICE)
        val vin = intent.getStringExtra(ARG_VIN)
        if (json.isNullOrBlank() || vin.isNullOrBlank()) {
            throw IllegalArgumentException("You need to provide a service and a VIN.")
        }
        val service = Gson().fromJson<Service>(json, Service::class.java)
        val factory = GenericViewModelFactory {
            ServiceDetailViewModel(application, service, vin)
        }
        return ViewModelProviders.of(this, factory).get(ServiceDetailViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_service_detail

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        viewModel.missingInformationEvent.observe(this, onMissingInformationEvent())
    }

    private fun onMissingInformationEvent() =
        LiveEventObserver<ServiceDetailViewModel.PreconditionEvent> {
            startActivity(ServicePreconditionActivity.getStartIntent(this, it.finOrVin, it.service))
            finish()
        }

    companion object {
        private const val ARG_SERVICE = "arg.detail.service"
        private const val ARG_VIN = "arg.detail.vin"

        fun getStartIntent(context: Context, service: Service, vin: String): Intent {
            val intent = Intent(context, ServiceDetailActivity::class.java)
            // TODO parcelable
            val json = Gson().toJson(service)
            intent.putExtra(ARG_SERVICE, json)
            intent.putExtra(ARG_VIN, vin)
            return intent
        }
    }
}