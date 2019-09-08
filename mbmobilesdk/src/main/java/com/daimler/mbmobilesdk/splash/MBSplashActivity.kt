package com.daimler.mbmobilesdk.splash

import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity

open class MBSplashActivity : MBBaseViewModelActivity<SplashViewModel>() {

    final override fun createViewModel(): SplashViewModel {
        val factory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        return ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
    }

    final override fun getLayoutRes(): Int = R.layout.activity_splash

    final override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        finishSplash()
    }

    private fun finishSplash() {
        val redirect = MBMobileSDK.initialActivity()
        redirect?.let {
            startActivity(Intent(this, it))
            finish()
        } ?: onFinishSplashScreen()
    }

    /**
     * Only called if no redirect-class to the MBLoginActivity implementation was given.
     */
    protected open fun onFinishSplashScreen() = Unit
}