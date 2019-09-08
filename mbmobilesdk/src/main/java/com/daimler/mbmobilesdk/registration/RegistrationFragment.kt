package com.daimler.mbmobilesdk.registration

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.databinding.FragmentRegistrationBinding
import com.daimler.mbmobilesdk.login.BaseLoginFragment
import com.daimler.mbmobilesdk.picker.SearchAndPickActivity
import com.daimler.mbmobilesdk.profile.edit.PickerEvent
import com.daimler.mbmobilesdk.profile.edit.ProfilePickerOptions
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.searchAndPickResultReceived
import com.daimler.mbmobilesdk.tou.HtmlUserAgreementActivity
import com.daimler.mbmobilesdk.utils.UserValuePolicy
import com.daimler.mbmobilesdk.utils.checkParameterNotNull
import com.daimler.mbmobilesdk.utils.extensions.*
import com.daimler.mbmobilesdk.vehiclestage.StageConfig
import com.daimler.mbingresskit.common.CiamUserAgreement
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.toast
import kotlinx.android.synthetic.main.fragment_registration.*
import java.util.*

internal class RegistrationFragment : BaseLoginFragment<RegistrationViewModel>() {

    override fun createViewModel(): RegistrationViewModel {
        val user: LoginUser? = arguments?.getParcelable(ARG_USER)
        val stageConfig: StageConfig? = arguments?.getParcelable(ARG_STAGE_CONFIG)
        checkParameterNotNull("user", user)
        checkParameterNotNull("stageConfig", stageConfig)
        val factory = RegistrationViewModelFactory(application, user!!, stageConfig!!)
        return ViewModelProviders.of(this, factory).get(RegistrationViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_registration

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        viewModel.onFragmentInit()

        notifyEndpointVisibilityChanged(false)
        notifyToolbarTitle(getString(R.string.registration_title))

        viewModel.registrationError.observe(this, registrationEvent())
        viewModel.alreadyRegisteredError.observe(this, alreadyRegistered())
        viewModel.showMmeIdInfoEvent.observe(this, showMmeIdInfo())
        viewModel.navigateToVerification.observe(this, navigateToVerification())
        viewModel.itemsCreatedEvent.observe(this, itemsCreated())
        viewModel.birthdayPickerEvent.observe(this, showBirthdayPicker())
        viewModel.showPickerEvent.observe(this, showExternalPicker())
        viewModel.inputErrorEvent.observe(this, inputErrorEvent())
        viewModel.showAgreementsEvent.observe(this, onShowAgreements())
        viewModel.legalErrorEvent.observe(this, onLegalError())
        viewModel.showLegalEvent.observe(this, onShowLegal())

        applyClickableSpans(binding as FragmentRegistrationBinding)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_CODE_PICKER_LANGUAGE ->
                searchAndPickResultReceived(resultCode, data)?.let {
                    viewModel.languageCodeSet(it.first)
                }
            REQ_CODE_PICKER_ADDRESS_STATE ->
                searchAndPickResultReceived(resultCode, data)?.let {
                    viewModel.addressStateSet(it.first, it.second)
                    viewModel.onUpdateProfileItems<ProfileField.AddressState>()
                }
            REQ_CODE_PICKER_ADDRESS_PROVINCE ->
                searchAndPickResultReceived(resultCode, data)?.let {
                    viewModel.addressProvinceSet(it.first, it.second)
                    viewModel.onUpdateProfileItems<ProfileField.AddressProvince>()
                }
            REQ_CODE_PICKER_ADDRESS_COUNTRY ->
                searchAndPickResultReceived(resultCode, data)?.let {
                    viewModel.addressCountryCodeSet(it.first)
                    viewModel.onUpdateProfileItems<ProfileField.AddressState>()
                }
            REQ_CODE_PICKER_ADDRESS_CITY -> {
                searchAndPickResultReceived(resultCode, data)?.let {
                    viewModel.addressCitySet(it.first, it.second)
                    viewModel.onUpdateProfileItems<ProfileField.AddressCity>()
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun applyClickableSpans(binding: FragmentRegistrationBinding) {
        val context = context ?: return
        val text = getString(R.string.registration_disclaimer_gtc)
        val partEula = getString(R.string.registration_disclamer_link_access_conditions)
        val partData = getString(R.string.registration_disclamer_link_service_conditions)
        val clickActions = listOf(
            ClickAction(partEula) { viewModel.onEulaSelected() },
            ClickAction(partData) { viewModel.onDataProtectionSelected() }
        )
        binding.tvDisclaimer.setClickableSpans(text, clickActions,
            ContextCompat.getColor(context, R.color.mb_accent_primary))
    }

    private fun registrationEvent() = LiveEventObserver<String> {
        // Registration success is already handled in ViewModel
        toast(it)
    }

    private fun alreadyRegistered() = LiveEventObserver<LoginUser> { user ->
        activity?.let {
            val meIdText = if (user.isMail) {
                getString(R.string.registration_already_registered_dialog_message_mail)
            } else {
                getString(R.string.registration_already_registered_dialog_message_phone)
            }
            MBDialogFragment.Builder(0).apply {
                // TODO string res withTitle(getString(R.string.registration_already_registered_dialog_title))
                withMessage(String.format(getString(R.string.registration_already_registered_dialog_message_template), meIdText))
                withPositiveButtonText(getString(R.string.registration_already_registered_close))
                withNegativeButtonText(getString(R.string.registration_already_registered_back_to_login))
                withNegativeAction { notifyShowLogin(user) }
            }.build().show(it.supportFragmentManager, null)
        }
    }

    private fun showMmeIdInfo() = LiveEventObserver<Unit> {
        activity?.let {
            MBDialogFragment.Builder(0).apply {
                withTitle(getString(R.string.registration_your_id))
                withMessage(getString(R.string.registration_your_id_description))
                withPositiveButtonText(getString(R.string.general_ok))
            }.build().show(it.supportFragmentManager, null)
        }
    }

    private fun navigateToVerification() =
        LiveEventObserver<LoginUser> {
            notifyShowPinVerification(it, false)
        }

    private fun onShowAgreements() = LiveEventObserver<CiamUserAgreement> {
        context?.let { ctx ->
            startActivity(HtmlUserAgreementActivity.getStartIntent(ctx, it.displayName, it.htmlContent.orEmpty()))
        }
    }

    private fun onShowLegal() = LiveEventObserver<Unit> {
        notifyShowLegal()
    }

    private fun onLegalError() = simpleToastObserver()

    private fun itemsCreated() =
        LiveEventObserver<RegistrationViewModel.ItemsCreatedEvent> { event ->
            context?.let {
                val creator = RegistrationSectionsViewCreator(it)
                val view = creator.createLayoutStructure(event.items)
                items_root.removeAllViews()
                items_root.addView(view)
            }
        }

    private fun showBirthdayPicker() = LiveEventObserver<Long> {
        context?.let { ctx ->
            val calendar = Date(it).toCalendar()

            val maxDate = Calendar.getInstance().apply {
                add(Calendar.YEAR, -UserValuePolicy.MIN_AGE)
            }.timeInMillis

            DatePickerDialog(ctx,
                R.style.MBDatePickerDialogTheme,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    viewModel.birthdaySet(calendar.timeInMillis)
                    viewModel.onUpdateProfileItems<ProfileField.Birthday>()
                    focusNextView()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                datePicker.maxDate = maxDate
                setOnCancelListener {
                    viewModel.birthdayCancel()
                    focusNextView()
                }
            }.show()
        }
    }

    private fun focusNextView() {
        activity?.currentFocus?.focusSearch(View.FOCUS_DOWN)?.requestFocus()
    }

    private fun showExternalPicker() =
        LiveEventObserver<ProfilePickerOptions> {
            context?.let { ctx ->
                val reqCode = when (it.event) {
                    PickerEvent.LANGUAGE -> REQ_CODE_PICKER_LANGUAGE
                    PickerEvent.ADDRESS_COUNTRY -> REQ_CODE_PICKER_ADDRESS_COUNTRY
                    PickerEvent.ADDRESS_STATE -> REQ_CODE_PICKER_ADDRESS_STATE
                    PickerEvent.ADDRESS_PROVINCE -> REQ_CODE_PICKER_ADDRESS_PROVINCE
                    PickerEvent.ADDRESS_CITY -> REQ_CODE_PICKER_ADDRESS_CITY
                }
                startActivityForResult(
                    SearchAndPickActivity.getStartIntent(
                        ctx,
                        it.title,
                        it.values,
                        it.initialValue
                    ),
                    reqCode
                )
            }
        }

    private fun inputErrorEvent() = simpleTextObserver()

    companion object {

        private const val ARG_USER = "arg.registration.user"
        private const val ARG_STAGE_CONFIG = "arg.registration.stage.config"

        private const val REQ_CODE_PICKER_LANGUAGE = 21
        private const val REQ_CODE_PICKER_ADDRESS_COUNTRY = 22
        private const val REQ_CODE_PICKER_ADDRESS_STATE = 23
        private const val REQ_CODE_PICKER_ADDRESS_PROVINCE = 24
        private const val REQ_CODE_PICKER_ADDRESS_CITY = 25

        private const val FIELD_POSITION_COUNTRY = 0
        private const val FIELD_POSITION_LANGUAGE = 1
        private const val FIELD_POSITION_IDENTIFIER = 2

        fun getInstance(user: LoginUser, stageConfig: StageConfig): RegistrationFragment {
            val registrationFragment = RegistrationFragment()
            val arguments = Bundle()
            arguments.apply {
                putParcelable(ARG_USER, user)
                putParcelable(ARG_STAGE_CONFIG, stageConfig)
            }
            registrationFragment.arguments = arguments
            return registrationFragment
        }
    }
}