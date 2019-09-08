package com.daimler.mbmobilesdk.serviceactivation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.databinding.ActivityServiceOverviewBinding
import com.daimler.mbmobilesdk.jumio.identitycheckhint.IdentityCheckHintActivity.Companion.getIdentityCheckIntent
import com.daimler.mbmobilesdk.serviceactivation.precondition.ServicePreconditionActivity
import com.daimler.mbmobilesdk.tou.AgreementsActivity
import com.daimler.mbmobilesdk.utils.extensions.getUpDrawable
import com.daimler.mbmobilesdk.utils.extensions.simpleTextObserver
import com.daimler.mbmobilesdk.utils.extensions.simpleToastObserver
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.toast

internal class ServiceOverviewActivity : MBBaseViewModelActivity<ServiceOverviewViewModel>() {

    private var serviceToggledEvent: ServiceToggledEvent? = null

    override fun createViewModel(): ServiceOverviewViewModel {
        val vehicle: VehicleInfo = intent.getParcelableExtra(ARG_VEHICLE)
            ?: throw IllegalArgumentException("You need to provide a vehicle.")
        val isRegistration = intent.getBooleanExtra(ARG_IS_REGISTRATION, false)
        val factory = GenericViewModelFactory {
            ServiceOverviewViewModel(application, vehicle, isRegistration)
        }
        return ViewModelProviders.of(this, factory).get(ServiceOverviewViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_service_overview

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        val toolbar = (binding as ActivityServiceOverviewBinding).toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setHomeAsUpIndicator(getUpDrawable())

        if (!viewModel.servicesLoaded) viewModel.loadItems()
        viewModel.serviceNotChangeableEvent.observe(this, onServiceNotChangeable())
        viewModel.serviceDesireError.observe(this, onServiceDesireError())
        viewModel.purchaseServiceEvent.observe(this, onPurchaseServiceEvent())
        viewModel.servicesNotAvailableError.observe(this, onServicesNotAvailableError())
        viewModel.serviceSelectedEvent.observe(this, onServiceSelected())
        viewModel.finishEvent.observe(this, onFinishEvent())
        viewModel.errorEvent.observe(this, onErrorEvent())
        viewModel.showPreconditionsEvent.observe(this, onShowPreconditions())
        viewModel.showAgreementsEvent.observe(this, onShowAgreements())
        viewModel.startJumioEvent.observe(this, onStartJumioEvent())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == JUMIO_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> viewModel.updateJumioResult(true, serviceToggledEvent)
                Activity.RESULT_CANCELED -> viewModel.updateJumioResult(false, serviceToggledEvent)
            }
        }
    }

    private fun onServiceNotChangeable() = simpleTextObserver()

    private fun onServiceDesireError() = simpleTextObserver()

    private fun onServicesNotAvailableError() = simpleTextObserver()

    private fun onServiceSelected() =
        LiveEventObserver<ServiceOverviewViewModel.ServiceSelectionEvent> {
            startActivity(ServiceDetailActivity.getStartIntent(this, it.service, it.vehicle.finOrVin))
        }

    private fun onPurchaseServiceEvent() = LiveEventObserver<String> {
        toast("Purchase: $it")
    }

    private fun onFinishEvent() = LiveEventObserver<Unit> {
        finish()
    }

    private fun onErrorEvent() = simpleToastObserver()

    private fun onShowPreconditions() =
        LiveEventObserver<ServiceOverviewViewModel.PreconditionsEvent> {
            MBDialogFragment.Builder(0).apply {
                withMessage(getString(R.string.activate_services_preconditions_missing))
                withPositiveButtonText(getString(R.string.general_ok))
                withNegativeButtonText(getString(R.string.general_no))
                withListener(object : MBDialogFragment.DialogListener {
                    override fun onNegativeAction(id: Int) = Unit

                    override fun onPositiveAction(id: Int) {
                        val intent = ServicePreconditionActivity.getStartIntent(
                            this@ServiceOverviewActivity, it.finOrVin, it.services)
                        startActivity(intent)
                    }
                })
            }.build().show(supportFragmentManager, null)
        }

    private fun onShowAgreements() = LiveEventObserver<Unit> {
        startActivity(AgreementsActivity.getStartIntent(this, AgreementsActivity.Type.SOE))
    }

    private fun onStartJumioEvent() = LiveEventObserver<ServiceOverviewViewModel.StartJumioEvent> {
        serviceToggledEvent = it.event
        startActivityForResult(getIdentityCheckIntent(), JUMIO_REQUEST_CODE)
    }

    companion object {
        private const val JUMIO_REQUEST_CODE = 1
        private const val ARG_VEHICLE = "arg.vehicle"
        private const val ARG_IS_REGISTRATION = "arg.is.registration"

        fun getStartIntent(context: Context, vehicle: VehicleInfo, isRegistration: Boolean = false): Intent {
            val intent = Intent(context, ServiceOverviewActivity::class.java)
            intent.putExtra(ARG_VEHICLE, vehicle)
            intent.putExtra(ARG_IS_REGISTRATION, isRegistration)
            return intent
        }
    }
}