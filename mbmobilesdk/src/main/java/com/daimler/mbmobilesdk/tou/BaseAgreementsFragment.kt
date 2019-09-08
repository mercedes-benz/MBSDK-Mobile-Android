package com.daimler.mbmobilesdk.tou

import android.content.Context
import androidx.lifecycle.ViewModel
import com.daimler.mbuikit.components.fragments.MBBaseViewModelFragment

internal abstract class BaseAgreementsFragment<T : ViewModel> : MBBaseViewModelFragment<T>() {

    private var callback: MBAgreementsCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as? MBAgreementsCallback
            ?: throw IllegalArgumentException("Host must implement MBAgreementsCallback!")
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    protected fun notifySoeUpdated() {
        callback?.onSoeUpdated()
    }

    protected fun notifySoeCancelled() {
        callback?.onSoeCancelled()
    }

    protected fun notifyNatconUpdated() {
        callback?.onNatconUpdated()
    }

    protected fun notifyNatconCancelled() {
        callback?.onNatconCancelled()
    }

    protected fun notifyAgreementsTitle(title: String) {
        callback?.onUpdateAgreementsTitle(title)
    }
}