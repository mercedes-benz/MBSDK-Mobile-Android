package com.daimler.mbmobilesdk.support.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.support.viewmodels.SupportHolderFragmentViewModel
import com.daimler.mbmobilesdk.utils.extensions.hideKeyboard
import com.daimler.mbuikit.components.fragments.MBBaseMenuFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import kotlinx.android.synthetic.main.fragment_support_holder.*

class SupportHolderFragment : MBBaseMenuFragment<SupportHolderFragmentViewModel>() {

    var chatFragment: Fragment? = null
    var phoneFragment: Fragment? = null

    override fun getLayoutRes(): Int = R.layout.fragment_support_holder

    override fun getModelId(): Int = BR.model

    override fun getToolbarTitleRes(): Int = R.string.rssm_navbar_title

    override fun createViewModel(): SupportHolderFragmentViewModel =
            ViewModelProviders.of(this).get(SupportHolderFragmentViewModel::class.java)

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        viewModel.clickEvent.observe(this, clickEvent())
    }

    private fun clickEvent() = LiveEventObserver<Int> {
        when (it) {
            SupportHolderFragmentViewModel.WIDGETS -> {}
            SupportHolderFragmentViewModel.MENU -> {}
            SupportHolderFragmentViewModel.RECYCLER -> {}
            SupportHolderFragmentViewModel.VIEWS -> {}
            SupportHolderFragmentViewModel.DIALOGS -> {}
        }
    }

    /**
     * The [androidx.viewpager.widget.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * androidx.fragment.app.FragmentStatePagerAdapter.
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_support_holder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mb_support_view_pager.adapter = mSectionsPagerAdapter
        mb_support_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                hideKeyboard()
            }

            override fun onPageSelected(position: Int) {}
        })
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    chatFragment = chatFragment ?: SupportMessageFragment.newInstance()
                    chatFragment!!
                }
                1 -> {
                    phoneFragment = phoneFragment ?: SupportPhoneFragment.newInstance()
                    phoneFragment!!
                }
                else -> Fragment() // should never happen
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            context?.let {
                return when (position) {
                    0 -> getString(R.string.rssm_tab_message)
                    1 -> getString(R.string.rssm_tab_call)
                    else -> "" // should never happen
                }
            }
            return null
        }

        override fun getCount(): Int = 2
    }

    companion object {
        fun newInstance() = SupportHolderFragment()
    }
}