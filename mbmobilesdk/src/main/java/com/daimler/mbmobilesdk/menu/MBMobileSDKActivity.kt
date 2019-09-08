package com.daimler.mbmobilesdk.menu

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.CallSuper
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.Slide
import com.bumptech.glide.Glide
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.login.error.ClientNotAuthorizedException
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbloggerkit.shake.LogOverlay
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.biometric.biometricSensorAvailable
import com.daimler.mbmobilesdk.databinding.ActivityMbMobileSdkBinding
import com.daimler.mbmobilesdk.familyapps.FamilyAppsFragment
import com.daimler.mbmobilesdk.featuretoggling.FLAG_SUPPORT_SHOW_MODULE
import com.daimler.mbmobilesdk.legal.LegalFragment
import com.daimler.mbmobilesdk.login.ProfileLogoutState
import com.daimler.mbmobilesdk.profile.ProfileCallback
import com.daimler.mbmobilesdk.profile.ProfileFragment
import com.daimler.mbmobilesdk.push.MBMobileSDKMessagingService
import com.daimler.mbmobilesdk.settings.SettingsFragment
import com.daimler.mbmobilesdk.support.SupportFragment
import com.daimler.mbmobilesdk.support.fragments.SupportHolderFragment
import com.daimler.mbmobilesdk.utils.extensions.*
import com.daimler.mbmobilesdk.vehicleselection.GarageFragment
import com.daimler.mbmobilesdk.vehicleselection.VehicleGarageListener
import com.daimler.mbuikit.components.activities.MBBaseMenuActivity
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.components.fragments.MBBaseMenuFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.menu.MBMenuItem
import com.daimler.mbuikit.menu.MenuProvider
import com.daimler.mbuikit.utils.extensions.replaceFragment
import com.daimler.mbuikit.utils.extensions.toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_mb_mobile_sdk.*

/**
 * Basic activity that provides a menu with default entries and a profile header and that
 * provides a basic toolbar with the navigation toggle.
 * This activity is the basic element of your Rising Stars App Family app.
 * Outside of the home screen, a back button is shown instead of the navigation toggle.
 *
 * @see LoginMenuProvider
 */
abstract class MBMobileSDKActivity : MBBaseMenuActivity<MBMobileSDKViewModel>(),
    VehicleGarageListener,
    FragmentManager.OnBackStackChangedListener,
    NavigationCallback,
    PushDataInteractor,
    ProfileCallback {

    open val showEndpointInMenuFooter = false

    /**
     * Called when the user tapped the logout button in the menu; after the logout
     * process finished.
     */
    abstract fun onLogout(success: Boolean)

    /**
     * Called when the user is not authorized/ not logged in.
     * Default implementation shows just a Toast.
     */
    open fun onNotAuthorized() = toast(getString(R.string.snack_bar_error))

    /**
     * Returns the initial fragment that shall be placed as content fragment.
     *
     * @return the initial fragment or null to not show a initial content fragment
     */
    abstract fun getContentFragment(): Fragment?

    /**
     * Returns the menu item to highlight at the start of this activity. This should usually be
     * the menu item that would navigate to the fragment returned by [getContentFragment].
     */
    open fun getInitialMenuItem(): MBMenuItem? = null

    /**
     * Returns the text to show in the footer of the menu.
     */
    open fun getFooterText(): String {
        val version = MBMobileSDK.appVersion()
        return if (showEndpointInMenuFooter) {
            val endpoint = MBMobileSDK.mobileSdkSettings().endPoint.get()
            "$version (${endpoint.region} - ${endpoint.stage})"
        } else {
            version
        }
    }

    final override fun getLayoutRes(): Int = R.layout.activity_mb_mobile_sdk

    final override fun getModelId(): Int = BR.model

    final override fun getDrawerLayout(): DrawerLayout =
        (binding as ActivityMbMobileSdkBinding).drawerLayout

    final override fun createToolbar(): Toolbar =
        (binding as ActivityMbMobileSdkBinding).toolbar

    override fun createMenuProvider(): MenuProvider = LoginMenuProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            showInitialContentState()
        }
    }

    override fun createViewModel(): MBMobileSDKViewModel =
        ViewModelProviders.of(this).get(MBMobileSDKViewModel::class.java)

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        supportFragmentManager.addOnBackStackChangedListener(this)

        ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)?.let {
            it.setColorFilter(ContextCompat.getColor(this, R.color.mb_white), PorterDuff.Mode.SRC_ATOP)
            toggle.setHomeAsUpIndicator(it)
            toggle.toolbarNavigationClickListener = View.OnClickListener { onBackPressed() }
        }

        viewModel.footerText.postValue(getFooterText())
        viewModel.userLoadedEvent.observe(this, onUserLoadedEvent())
        viewModel.garageVehiclesLoadedEvent.observe(this, onGarageLoadedEvent())
        viewModel.logoutEvent.observe(this, onLogoutEvent())
        viewModel.clickEvent.observe(this, onClickEvent())
        viewModel.showLogOverlayEvent.observe(this, onShowLogOverlay())
        viewModel.profilePictureBytes.observe(this, onProfilePictureChanged())
        viewModel.vehicleImageChangedEvent.observe(this, onVehicleImageChanged())
        viewModel.vehicleSelectedEvent.observe(this, onVehicleSelectedEvent())

        handlePushData()
    }

    // edit text focus clear on touch outside
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onBackPressed() {
        when {
            drawer.isDrawerOpen(GravityCompat.START) -> {
                // Close the drawer if it is opened.
                closeDrawer()
            }
            supportFragmentManager.backStackEntryCount != 0 -> {
                // Let Android handle the back press if there are fragments in the back stack.
                super.onBackPressed()
            }
            currentContentFragment() is MBMobileSDKHomeFragment -> {
                // Ask the user to exit the app if we are on the home screen.
                showAppExitDialog()
            }
            else -> {
                // Show the home content fragment if possible or the exit dialog if not.
                if (!showInitialContentState()) {
                    showAppExitDialog()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.removeOnBackStackChangedListener(this)
    }

    /* VehicleGarageListener */

    @CallSuper
    override fun onVehicleSelected(vehicle: VehicleInfo) = Unit

    @CallSuper
    override fun onAddNewVehicle() = Unit

    @CallSuper
    override fun onVehicleDeleted(finOrVin: String) = Unit

    @CallSuper
    override fun onVehiclesUnselected() {
        viewModel.unselectVehicleMenu()
    }

    final override fun onVehicleSelectedFromGarage(vehicle: VehicleInfo, autoSelected: Boolean) {
        viewModel.onVehicleSelected()
        onVehicleSelected(vehicle)

        if (autoSelected.not()) showHome()
    }

    /* NavigationCallback */

    override fun onShowSupport() {
        replaceContentFragment(SupportHolderFragment.newInstance().apply {
            enterTransition = Slide(Gravity.BOTTOM)
            exitTransition = Slide(Gravity.TOP)
        }, addToBackStack = true)
    }

    /* PushDataInteractor */

    override fun onShowUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        if (intent.isResolvable(this)) {
            startActivity(intent)
        }
    }

    override fun onRedirectToDeepLink(deepLinkReference: String) {
        MBLoggerKit.w("No handling for deep link $deepLinkReference.")
    }

    /* ProfileCallback */

    override fun onUserChanged(user: User) {
        viewModel.onUserChanged(user)
    }

    override fun onProfilePictureUpdated(pictureBytes: ByteArray) {
        viewModel.onUserPictureChanged(pictureBytes)
    }

    override fun onLogoutStateChanged(state: ProfileLogoutState) {
        viewModel.onLogoutStateChanged(state)
    }

    /**/

    @CallSuper
    override fun handleMenuItemClick(item: MBMenuItem, alreadySelected: Boolean): Boolean {
        // TODO implement other menu items as soon as the screens are implemented.
        // Currently there are just dummy screens shown from the mbuikit module.
        return when {
            alreadySelected -> {
                closeDrawer()
                true
            }
            item == LoginMenuProvider.APP_FAMILY -> {
                replaceFromMenu(FamilyAppsFragment.newInstance())
                true
            }
            item == LoginMenuProvider.SETTINGS -> {
                replaceFromMenu(SettingsFragment.newInstance(biometricSensorAvailable(this)))
                true
            }
            item == LoginMenuProvider.SUPPORT -> {
                if (MBMobileSDK.featureToggleService().isToggleEnabled(FLAG_SUPPORT_SHOW_MODULE)) {
                    replaceFromMenu(SupportHolderFragment.newInstance())
                } else {
                    replaceFromMenu(SupportFragment.newInstance())
                }
                true
            }
            item == LoginMenuProvider.LEGAL -> {
                replaceFromMenu(LegalFragment.newInstance())
                true
            }
            else -> false
        }
    }

    protected fun replaceFromMenu(fragment: Fragment) {
        closeDrawer()
        replaceContentFragment(fragment)
    }

    /**
     * Replaces the current content fragment with the given fragment.
     * [onFragmentChange] will be called after the fragment transaction.
     * @see replaceFragment
     */
    fun replaceContentFragment(
        fragment: Fragment,
        tag: String? = fragment::class.java.canonicalName,
        addToBackStack: Boolean = false,
        name: String? = null
    ) {
        replaceFragment(R.id.content_container, fragment, tag, addToBackStack, name)
        // onBackStackChanged() will be called if the fragment was added to the back stack.
        onFragmentChange(fragment, name)
    }

    fun currentContentFragment(): Fragment? =
        supportFragmentManager.findFragmentById(R.id.content_container)

    final override fun onBackStackChanged() {
        val manager = supportFragmentManager
        val count = manager.backStackEntryCount
        val name = if (count != 0) manager.getBackStackEntryAt(count - 1).name else null
        val fragment = manager.findFragmentById(R.id.content_container)
        fragment?.let { onFragmentChange(it, name) }
    }

    /**
     * Called after a back stack change or after [replaceContentFragment] was called.
     * The default implementation changes the toolbar title if the new fragment is an instance
     * of [MBBaseMenuFragment].
     *
     * @param fragment The new fragment.
     * @param name The name that was given for the back stack entry for the fragment.
     */
    @CallSuper
    open fun onFragmentChange(fragment: Fragment, name: String?) {
        when (fragment) {
            is MBMobileSDKHomeFragment -> {
                viewModel.showToolbarLogo()
                enableSideMenu()
            }
            is MBBaseMenuFragment<*> -> {
                fragment.getToolbarTitleRes().takeIf { it != 0 }?.let {
                    viewModel.showToolbarTitle(getString(it))
                } ?: viewModel.showToolbarTitle(null)
                disableSideMenu()
            }
            else -> {
                viewModel.showToolbarTitle(null)
                disableSideMenu()
            }
        }
    }

    /** Hides the menu icon and locks the menu. */
    private fun disableSideMenu() {
        toggle.isDrawerIndicatorEnabled = false
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    /** Shows the menu icon and unlocks the menu. */
    private fun enableSideMenu() {
        toggle.isDrawerIndicatorEnabled = true
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    private fun showInitialContentState(): Boolean {
        // initial content fragment
        val contentFragmentAdded = getContentFragment()?.let {
            replaceContentFragment(it)
            true
        } ?: false

        // highlight item
        getInitialMenuItem()?.let {
            menuProvider.selectItem(it)
        }

        return contentFragmentAdded
    }

    private fun handlePushData() {
        val data = MBMobileSDK.pushDataStorage().latestPushData
        MBMobileSDK.pushDataStorage().clear()
        data?.let {
            PushDataHandler(this).handlePushData(it)
            // TODO maybe apply notification id to data
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
            notificationManager?.cancel(MBMobileSDKMessagingService.NOTIFICATION_ID_SDK)
        }
    }

    private fun showAppExitDialog() {
        MBDialogFragment.Builder(0).apply {
            withMessage(getString(R.string.app_exit_dialog_message))
            withPositiveButtonText(getString(R.string.general_yes))
            withPositiveAction { finish() }
            withNegativeButtonText(getString(R.string.general_no))
        }.build().show(supportFragmentManager, null)
    }

    private fun onUserLoadedEvent() =
        LiveEventObserver<MBMobileSDKViewModel.UserLoadingResult> {
            val user = it.user
            if (user != null) {
                notifyUserLogin(user)
            } else {
                if (it.error is ClientNotAuthorizedException) {
                    onNotAuthorized()
                } else {
                    toast(getString(R.string.snack_bar_error))
                }
            }
        }

    private fun onGarageLoadedEvent() =
        LiveEventObserver<MBMobileSDKViewModel.GarageVehiclesLoadingResult> {
            it.error?.let { toast(getString(R.string.bottom_share_sheet_vehicles_error)) }
        }

    private fun onLogoutEvent() = LiveEventObserver<Boolean> { logoutSuccess ->
        onLogout(logoutSuccess)
    }

    private fun onClickEvent() = LiveEventObserver<Int> {
        when (it) {
            MBMobileSDKViewModel.EVENT_CLICK_PROFILE -> showProfile()
            MBMobileSDKViewModel.EVENT_CLICK_VEHICLE -> showGarage()
            MBMobileSDKViewModel.EVENT_MENU_ICON_CLICKED -> toast("Not implemented yet")
            else -> throw IllegalArgumentException("Unknown click event: $it.")
        }
    }

    private fun onShowLogOverlay() = LiveEventObserver<Unit> {
        LogOverlay.showChooser(this, LogOverlay.Order.CHRONOLOGICAL_DESCENDING)
    }

    private fun onProfilePictureChanged() = Observer<ByteArray> {
        Glide
            .with(this)
            .asBitmap()
            .load(it)
            .onResourceReady(profile_menu_header) { v, bmp ->
                v.setProfilePicture(bmp)
            }
    }

    private fun onVehicleImageChanged() = Observer<Bitmap> {
        profile_menu_header.updateVehiclePicture(it)
    }

    private fun onVehicleSelectedEvent() = LiveEventObserver<VehicleInfo> {
        onVehicleSelected(it)
    }

    private fun notifyUserLogin(user: User) {
        Snackbar.make(binding.root, String.format(getString(R.string.snack_bar_user), user.formatName()),
            Snackbar.LENGTH_LONG).showCentered()
    }

    private fun showProfile() {
        showExternalFragment(ProfileFragment.newInstance(), false)
    }

    private fun showGarage() {
        showExternalFragment(GarageFragment.newInstance(), false)
    }

    private fun showExternalFragment(
        fragment: Fragment,
        addToBackStack: Boolean = false
    ): Boolean {
        closeDrawer()
        return currentContentFragment()?.let {
            if (it::class.java.canonicalName != fragment::class.java.canonicalName) {
                deselectMenuItemsAndReplace(fragment, addToBackStack)
                true
            } else {
                false
            }
        } ?: {
            deselectMenuItemsAndReplace(fragment, addToBackStack)
            true
        }()
    }

    private fun deselectMenuItemsAndReplace(fragment: Fragment, addToBackStack: Boolean) {
        menuProvider.deselectAll()
        replaceContentFragment(fragment, addToBackStack = addToBackStack)
    }

    private fun showHome() {
        showInitialContentState()
    }
}