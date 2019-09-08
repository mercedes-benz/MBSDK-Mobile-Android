package com.daimler.mbmobilesdk.familyapps

import androidx.databinding.ViewDataBinding
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.anyTargetOpened
import com.daimler.mbmobilesdk.utils.extensions.createAndroidViewModel
import com.daimler.mbmobilesdk.utils.extensions.createSimpleDialogObserver
import com.daimler.mbdeeplinkkit.MBDeepLinkKit
import com.daimler.mbdeeplinkkit.common.FallbackTarget
import com.daimler.mbdeeplinkkit.common.FamilyApp
import com.daimler.mbuikit.components.fragments.MBBaseMenuFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.toast

class FamilyAppsFragment : MBBaseMenuFragment<FamilyAppsViewModel>() {

    override fun getToolbarTitleRes(): Int = R.string.app_family_title

    override fun createViewModel(): FamilyAppsViewModel =
        createAndroidViewModel(FamilyAppsViewModel::class.java)

    override fun getLayoutRes(): Int = R.layout.fragment_family_apps

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        viewModel.onAppClickedEvent.observe(this, onAppClick())
        viewModel.onAppsErrorEvent.observe(this, onAppsError())
    }

    private fun onAppClick() = LiveEventObserver<FamilyApp> { app ->
        context?.let {
            val result = MBDeepLinkKit.openFamilyApp(it, app, FallbackTarget.STORE)
            if (!result.anyTargetOpened()) urlDoesNotExistError()
        }
    }

    private fun onAppsError() = createSimpleDialogObserver()

    private fun urlDoesNotExistError() {
        toast(String.format(getString(R.string.app_family_open_app_store_error), getString(R.string.app_family_play_store)))
    }

    companion object {
        fun newInstance() = FamilyAppsFragment()
    }
}
