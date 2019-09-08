package com.daimler.mbmobilesdk.serviceactivation.precondition

import android.content.Context
import android.content.Intent
import android.text.InputType
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.serviceactivation.views.ButtonMissingFieldView
import com.daimler.mbmobilesdk.serviceactivation.views.EditTextMissingFieldView
import com.daimler.mbmobilesdk.serviceactivation.views.FieldResolvable
import com.daimler.mbmobilesdk.serviceactivation.views.TextMissingFieldView
import com.daimler.mbmobilesdk.tou.AgreementsActivity
import com.daimler.mbmobilesdk.utils.checkParameterNotBlank
import com.daimler.mbmobilesdk.utils.extensions.forAllChildrenIndexed
import com.daimler.mbmobilesdk.utils.extensions.simpleToastObserver
import com.daimler.mbmobilesdk.vehicleassignment.SearchMerchantActivity
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_service_precondition.*

internal class ServicePreconditionActivity :
    MBBaseViewModelActivity<BaseServicePreconditionViewModel>(),
    MissingFieldResolvableCreator {

    private var mode = Mode.SINGLE
    private var pendingType: ServicePreconditionType? = null

    override fun createViewModel(): BaseServicePreconditionViewModel {
        val finOrVin = intent.getStringExtra(ARG_VIN)
        checkParameterNotBlank("finOrVin", finOrVin)

        val serviceJson = intent.getStringExtra(ARG_SERVICE)
        val servicesJson = intent.getStringExtra(ARG_SERVICES)
        return when {
            !serviceJson.isNullOrBlank() -> {
                mode = Mode.SINGLE
                val factory = SingleServicePreconditionViewModelFactory(application,
                    finOrVin,
                    Gson().fromJson(serviceJson, Service::class.java))
                ViewModelProviders.of(this, factory).get(SingleServicePreconditionViewModel::class.java)
            }
            !servicesJson.isNullOrBlank() -> {
                mode = Mode.MULTIPLE
                val factory = MultipleServicesViewModelFactory(application,
                    finOrVin,
                    Gson().fromJson(servicesJson, object : TypeToken<List<Service>>() {}.type))
                ViewModelProviders.of(this, factory).get(MultipleServicesPreconditionViewModel::class.java)
            }
            else -> throw IllegalArgumentException("You need to provide one or multiple services.")
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_service_precondition

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        viewModel.successEvent.observe(this, onSuccessEvent())
        viewModel.errorEvent.observe(this, onErrorEvent())
        viewModel.backEvent.observe(this, onBackEvent())
        viewModel.selectMerchantEvent.observe(this, onSelectMerchant())
        viewModel.purchaseLicenseEvent.observe(this, onPurchaseLicense())
        viewModel.confirmIdentityEvent.observe(this, onConfirmIdentity())
        viewModel.signUserAgreementsEvent.observe(this, onShowUserAgreements())
        viewModel.preconditionResolvedEvent.observe(this, onPreconditionResolved())

        viewModel.preparePreconditions(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_CODE_AGREEMENTS -> {
                pendingType?.let {
                    if (resultCode == RESULT_OK &&
                        it is ServicePreconditionType.SignUserAgreement) {
                        viewModel.onAgreementsUpdated(it)
                    }
                }
                pendingType = null
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.SignUserAgreement) {
        MBLoggerKit.d("handle SignUserAgreement")
        val resolvable = baseButtonResolvable(
            getString(R.string.service_preconditions_sign_user_agreement_message),
            getString(R.string.service_preconditions_sign_user_agreement_button)
        )
        resolveAndAddResolvable(preconditionType, resolvable)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.PurchaseLicense) {
        MBLoggerKit.d("handle PurchaseLicense")
        val text = when (mode) {
            Mode.SINGLE -> getString(R.string.service_preconditions_license_message)
            Mode.MULTIPLE -> String.format(
                getString(R.string.service_preconditions_license_name_message),
                viewModel.serviceForId(preconditionType.serviceId)?.name.orEmpty()
            )
        }
        val resolvable = baseButtonResolvable(
            text,
            getString(R.string.service_preconditions_license_purchase)
        )
        resolveAndAddResolvable(preconditionType, resolvable)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.UpdateTrustLevel) {
        MBLoggerKit.d("handle UpdateTrustLevel")
        val resolvable = baseButtonResolvable(
            getString(R.string.service_preconditions_trust_level_message),
            getString(R.string.service_preconditions_trust_level_button)
        )
        resolveAndAddResolvable(preconditionType, resolvable)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.UserMail) {
        MBLoggerKit.d("handle UserMail")
        val resolvable = baseEditTextResolvable(
            getString(R.string.service_preconditions_mail_message),
            getString(R.string.service_preconditions_mail_hint)
        )
        resolveAndAddResolvable(preconditionType, resolvable)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.UserMailVerified) {
        MBLoggerKit.d("handle UserMailVerified")
        val resolvable = baseTextResolvable(
            getString(R.string.service_preconditions_verify_mail)
        )
        resolveAndAddResolvable(preconditionType, resolvable)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.UserPhone) {
        MBLoggerKit.d("handle UserPhone")
        val resolvable = baseEditTextResolvable(
            getString(R.string.service_preconditions_phone_message),
            getString(R.string.service_preconditions_phone_hint),
            InputType.TYPE_CLASS_PHONE
        )
        resolveAndAddResolvable(preconditionType, resolvable)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.UserPhoneVerified) {
        MBLoggerKit.d("handle UserPhoneVerified")
        val resolvable = baseTextResolvable(
            getString(R.string.service_preconditions_verify_phone)
        )
        resolveAndAddResolvable(preconditionType, resolvable)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.LicensePlate) {
        MBLoggerKit.d("handle LicensePlate")
        val resolvable = baseEditTextResolvable(
            getString(R.string.service_preconditions_license_plate_message),
            getString(R.string.service_preconditions_license_plate_hint)
        )
        resolveAndAddResolvable(preconditionType, resolvable)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.VehicleServiceDealer) {
        MBLoggerKit.d("handle VehicleServiceDealer")
        val resolvable = baseButtonResolvable(
            getString(R.string.service_preconditions_merchant),
            getString(R.string.service_preconditions_merchant_button)
        )
        resolveAndAddResolvable(preconditionType, resolvable)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.UserContactMethod) {
        MBLoggerKit.d("No handling for UserContactMethod")
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.CpIncredit) {
        MBLoggerKit.d("No handling for CpIncredit")
    }

    private fun resolveAndAddResolvable(preconditionType: ServicePreconditionType, resolvable: FieldResolvable) {
        preconditionType.resolve(viewModel, resolvable)
        resolvable.view()?.let {
            it.tag = preconditionType
            scroll_root.addView(it)
        }
    }

    private fun baseEditTextResolvable(title: String, hint: String, inputType: Int = InputType.TYPE_CLASS_TEXT): FieldResolvable =
        EditTextMissingFieldView(this).apply {
            setTitle(title)
            setHint(hint)
            setInputType(inputType)
            setDefaultMargins()
        }

    private fun baseButtonResolvable(title: String, buttonText: String): FieldResolvable =
        ButtonMissingFieldView(this).apply {
            setTitle(title)
            setButtonText(buttonText)
            setDefaultMargins()
        }

    private fun baseTextResolvable(text: String): FieldResolvable =
        TextMissingFieldView(this).apply {
            setText(text)
            setDefaultMargins()
        }

    private fun View.setDefaultMargins() {
        val marginDefault = resources.getDimension(R.dimen.mb_margin_default).toInt()
        val marginSmall = resources.getDimension(R.dimen.mb_margin_small).toInt()
        val marginLarge = resources.getDimension(R.dimen.mb_margin_large).toInt()
        setPadding(marginDefault, marginSmall, marginDefault, marginLarge)
    }

    private fun onSuccessEvent() = simpleToastObserver()

    private fun onErrorEvent() = simpleToastObserver()

    private fun onBackEvent() = LiveEventObserver<Unit> {
        onBackPressed()
    }

    private fun onSelectMerchant() = LiveEventObserver<Unit> {
        startActivity(SearchMerchantActivity.getStartIntent(this))
    }

    private fun onPurchaseLicense() = LiveEventObserver<Service> {
        toast("Purchase License.")
    }

    private fun onConfirmIdentity() = LiveEventObserver<Unit> {
        toast("Jumio")
    }

    private fun onShowUserAgreements() =
        LiveEventObserver<ServicePreconditionType.SignUserAgreement> {
            pendingType = it
            startActivityForResult(
                AgreementsActivity.getStartIntent(this, AgreementsActivity.Type.SOE),
                REQ_CODE_AGREEMENTS
            )
        }

    private fun onPreconditionResolved() = LiveEventObserver<ServicePreconditionType> {
        scroll_root.apply {
            forAllChildrenIndexed { index, child ->
                if (child?.tag == it) {
                    removeViewAt(index)
                    return@forAllChildrenIndexed
                }
            }
        }
    }

    private enum class Mode { SINGLE, MULTIPLE }

    private data class PendingResultAction(
        val requestCode: Int,
        val action: () -> Unit
    )

    companion object {

        private const val ARG_SERVICE = "arg.services.preconditions.service"
        private const val ARG_SERVICES = "arg.services.preconditions.services"
        private const val ARG_VIN = "arg.services.preconditions.vin"

        private const val REQ_CODE_AGREEMENTS = 21

        fun getStartIntent(context: Context, finOrVin: String, service: Service): Intent {
            val intent = Intent(context, ServicePreconditionActivity::class.java)
            intent.putExtra(ARG_VIN, finOrVin)
            // TODO parcelable
            val json = Gson().toJson(service)
            intent.putExtra(ARG_SERVICE, json)
            return intent
        }

        fun getStartIntent(context: Context, finOrVin: String, services: List<Service>): Intent {
            val intent = Intent(context, ServicePreconditionActivity::class.java)
            intent.putExtra(ARG_VIN, finOrVin)
            // TODO parcelable
            val json = Gson().toJson(services)
            intent.putExtra(ARG_SERVICES, json)
            return intent
        }
    }
}