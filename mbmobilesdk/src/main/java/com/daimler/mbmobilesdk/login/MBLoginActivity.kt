package com.daimler.mbmobilesdk.login

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.transition.Slide
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.app.Region
import com.daimler.mbmobilesdk.app.Stage
import com.daimler.mbmobilesdk.legal.LegalLoginFragment
import com.daimler.mbmobilesdk.login.pin.PinVerificationFragment
import com.daimler.mbmobilesdk.menu.NavigationCallback
import com.daimler.mbmobilesdk.registration.LoginUser
import com.daimler.mbmobilesdk.registration.RegistrationFragment
import com.daimler.mbmobilesdk.registration.locale.UserLocaleLoginFragment
import com.daimler.mbmobilesdk.serviceactivation.ServiceOverviewActivity
import com.daimler.mbmobilesdk.stageselector.StageSelectorActivity
import com.daimler.mbmobilesdk.support.fragments.SupportHolderFragment
import com.daimler.mbmobilesdk.tou.natcon.NatconLoginFragment
import com.daimler.mbmobilesdk.utils.extensions.isLoggedIn
import com.daimler.mbmobilesdk.utils.ifNotNull
import com.daimler.mbmobilesdk.vehicleassignment.AssignVehicleActivity
import com.daimler.mbmobilesdk.vehiclestage.RegistrationStage
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.replaceFragment
import kotlinx.android.synthetic.main.activity_mb_login.*

/**
 * Basic login activity. Subclass this activity and implement the abstract methods.
 */
abstract class MBLoginActivity : MBBaseViewModelActivity<MBLoginViewModel>(),
    MBLoginCallback, NavigationCallback {

    /**
     * Called when the login process started, right before the library calls the
     * service to login.
     */
    open fun onLoginStarted() = Unit

    /**
     * Called when the user is authenticated.
     * If the user was already registered, this method is called after a successful login.
     * If the user just registered, this method is called after the completion of the vehicle
     * assignment and service activation.
     */
    abstract fun onUserAuthenticated()

    /**
     * Called when the login process failed.
     * The default implementation shows an error dialog.
     */
    open fun onLoginFailed(error: String) {
        MBDialogFragment.Builder(DIALOG_ERROR_ID)
            .withTitle(getString(R.string.general_error_title))
            .withMessage(error)
            .withPositiveButtonText(getString(R.string.general_okay))
            .build()
            .show(supportFragmentManager, null)
    }

    override fun createViewModel(): MBLoginViewModel {
        val factory = MBLoginViewModelFactory(application, MBMobileSDK.endpointSelectionEnabled())
        return ViewModelProviders.of(this, factory).get(MBLoginViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_mb_login

    override fun getModelId(): Int = BR.model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isLoggedIn()) onUserAuthenticated()

        if (savedInstanceState == null) {
            replaceFragment(R.id.content_container, CredentialsFragment.newInstance())
        }
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        viewModel.stageSelectorSelectedEvent.observe(this, onStageSelectorSelected())
        viewModel.showServiceActivationEvent.observe(this, onShowServiceActivation())
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentByTag(TAG_TAN_FRAGMENT)?.isVisible == true) {
            clearBackStack()
            replaceFragment(R.id.content_container, CredentialsFragment.newInstance())
        } else {
            super.onBackPressed()
        }
    }

    override fun setStageSelectorVisibility(isVisible: Boolean) {
        val enabled = MBMobileSDK.endpointSelectionEnabled() && isVisible
        viewModel.endpointSelectorEnabled.value = enabled
    }

    override fun hideToolbar() {
        viewModel.toolbarVisible.postValue(false)
    }

    override fun setToolbarTitle(title: String) {
        viewModel.toolbarTitle.postValue(title)
        viewModel.toolbarVisible.postValue(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_CODE_VEHICLE_ASSIGNMENT -> {
                val finOrVin = data?.getStringExtra(AssignVehicleActivity.ARG_ASSIGNED_VEHICLE_VIN)
                MBLoggerKit.d("VIN = $finOrVin.")
                finOrVin?.let {
                    viewModel.vehicleAssigned(it)
                } ?: onUserAuthenticated()
            }
            REQ_CODE_SERVICE_ACTIVATION -> onUserAuthenticated()
            REQ_CODE_STAGE_SELECTOR -> {
                val region: Region? = data?.getSerializableExtra(StageSelectorActivity.ARG_SELECTED_REGION) as? Region
                val stage: Stage? = data?.getSerializableExtra(StageSelectorActivity.ARG_SELECTED_STAGE) as? Stage
                ifNotNull(region, stage) { r, s ->
                    viewModel.updateSelectedEndpoint(r, s)
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onStarted() {
        MBMobileSDK.initModules(MBMobileSDK.mobileSdkSettings(), MBMobileSDK.trackingSettings())
        onLoginStarted()
    }

    override fun onSuccess(user: LoginUser, isRegistration: Boolean) {
        if (isRegistration) user.pendingNatconAgreements?.let { viewModel.updateAgreements(it) }
        viewModel.enablePushesInSettings()
        if (shouldShowVehicleAssignment(isRegistration)) {
            startActivityForResult(AssignVehicleActivity.getStartIntent(this, true,
                RegistrationStage.VehicleAssignment(this).toStageConfig()),
                REQ_CODE_VEHICLE_ASSIGNMENT)
        } else {
            onUserAuthenticated()
        }
    }

    override fun onError(error: String) = onLoginFailed(error)

    override fun onShowLocaleSelection(user: LoginUser) {
        replaceFragment(R.id.content_container, UserLocaleLoginFragment.newInstance(user.user, user.isMail),
            addToBackStack = true)
    }

    override fun onShowNatcon(user: LoginUser) {
        replaceFragment(R.id.content_container, NatconLoginFragment.newInstance(user), addToBackStack = true)
    }

    override fun onShowRegistration(user: LoginUser) {
        replaceFragment(
            R.id.content_container,
            RegistrationFragment.getInstance(user, RegistrationStage.MeId(this).toStageConfig()),
            addToBackStack = true
        )
    }

    override fun onShowLogin(user: LoginUser) {
        clearBackStack()
        replaceFragment(R.id.content_container, CredentialsFragment.newInstance(user.user))
    }

    override fun onShowPinVerification(user: LoginUser, isLogin: Boolean) {
        replaceFragment(R.id.content_container,
            PinVerificationFragment.getInstance(user, !isLogin),
            tag = TAG_TAN_FRAGMENT, addToBackStack = true)
    }

    override fun onShowLegal() {
        replaceFragment(R.id.content_container,
            LegalLoginFragment.newInstance(), addToBackStack = true)
    }

    override fun onShowSupport() {
        val supportFragment = SupportHolderFragment.newInstance()
        supportFragment.enterTransition = Slide(Gravity.BOTTOM)
        supportFragment.exitTransition = Slide(Gravity.TOP)

        setStageSelectorVisibility(false)
        setToolbarTitle(getString(supportFragment.getToolbarTitleRes()))

        replaceFragment(R.id.content_container,
            supportFragment, addToBackStack = true)
    }

    private fun isLoggedIn(): Boolean = MBIngressKit.authenticationService().isLoggedIn()

    private fun shouldShowVehicleAssignment(userJustRegistered: Boolean): Boolean = userJustRegistered

    private fun onStageSelectorSelected() = LiveEventObserver<Unit> {
        startActivityForResult(Intent(this, StageSelectorActivity::class.java),
            REQ_CODE_STAGE_SELECTOR)
    }

    private fun onShowServiceActivation() = LiveEventObserver<VehicleInfo> {
        startActivityForResult(ServiceOverviewActivity.getStartIntent(this, it, true),
            REQ_CODE_SERVICE_ACTIVATION)
    }

    private fun clearBackStack() {
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {

        private const val TAG_TAN_FRAGMENT = "ris.tag.fragment.tan"

        private const val REQ_CODE_VEHICLE_ASSIGNMENT = 11
        private const val REQ_CODE_SERVICE_ACTIVATION = 12
        private const val REQ_CODE_STAGE_SELECTOR = 13
        protected const val DIALOG_ERROR_ID = 1001
    }
}