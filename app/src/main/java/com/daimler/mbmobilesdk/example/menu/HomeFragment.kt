package com.daimler.mbmobilesdk.example.menu

import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.example.BR
import com.daimler.mbmobilesdk.example.R
import com.daimler.mbmobilesdk.menu.MBMobileSDKHomeFragment
import com.daimler.mbuikit.components.fragments.MBBaseMenuFragment

class HomeFragment : MBBaseMenuFragment<HomeViewModel>(), MBMobileSDKHomeFragment {

    override fun createViewModel(): HomeViewModel =
        ViewModelProviders.of(this).get(HomeViewModel::class.java)

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun getModelId(): Int = BR.model

    override fun getToolbarTitleRes(): Int = R.string.home

    companion object {

        fun newInstance(): HomeFragment = HomeFragment()
    }
}