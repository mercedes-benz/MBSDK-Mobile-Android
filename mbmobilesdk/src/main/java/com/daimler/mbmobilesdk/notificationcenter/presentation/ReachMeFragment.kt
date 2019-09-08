package com.daimler.mbmobilesdk.notificationcenter.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.daimler.mbmobilesdk.notificationcenter.model.Message
import com.daimler.mbuikit.components.fragments.MBBaseViewModelFragment
import java.lang.IllegalArgumentException

abstract class ReachMeFragment<T : ViewModel> : MBBaseViewModelFragment<T>() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if ((context is Navigation).not()) {
            throw IllegalArgumentException("Parent activity of ${ReachMeFragment::class.java.simpleName} must implement ${Navigation::class.java}")
        }
    }

    protected fun close() {
        navigation().onClose()
    }

    protected fun showMessage(message: Message) {
        navigation().onShowMessage(message)
    }

    internal fun navigation(): Navigation {
        return context as Navigation
    }
}