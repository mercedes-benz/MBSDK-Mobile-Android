package com.daimler.mbmobilesdk.legal

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.daimler.mbmobilesdk.login.MBLoginCallback

internal class LegalLoginFragment : LegalFragment() {

    private var callback: MBLoginCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as? MBLoginCallback
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        callback?.apply {
            setStageSelectorVisibility(false)
            setToolbarTitle(getString(getToolbarTitleRes()))
        }
    }

    companion object {

        fun newInstance(): LegalLoginFragment = LegalLoginFragment()
    }
}