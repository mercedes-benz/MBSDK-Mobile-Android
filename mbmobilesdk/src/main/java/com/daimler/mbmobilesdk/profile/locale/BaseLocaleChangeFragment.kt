package com.daimler.mbmobilesdk.profile.locale

import android.content.Context
import androidx.lifecycle.ViewModel
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbuikit.components.fragments.MBBaseViewModelFragment

internal abstract class BaseLocaleChangeFragment<T : ViewModel> : MBBaseViewModelFragment<T>() {

    private var callback: LocaleChangeCallback? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? LocaleChangeCallback
        if (callback == null) {
            MBLoggerKit.w("Host does not implement LocaleChangeCallback.")
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    protected fun notifyLocaleSelected(userLocale: UserLocale) {
        callback?.onLocaleSelected(userLocale)
    }

    protected fun notifyShowNatcon(userLocale: UserLocale) {
        callback?.onShowNatcon(userLocale)
    }

    protected fun notifyCancel() {
        callback?.onCancel()
    }

    protected fun notifyUpdateToolbarTitle(title: String) {
        callback?.onUpdateTitle(title)
    }
}