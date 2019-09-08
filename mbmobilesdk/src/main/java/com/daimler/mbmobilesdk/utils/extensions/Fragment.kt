package com.daimler.mbmobilesdk.utils.extensions

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.toast

fun <T : ViewModel> Fragment.createAndroidViewModel(cls: Class<T>): T {
    val factory =
        ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
    return ViewModelProviders.of(this, factory).get(cls)
}

fun Fragment.createSimpleDialogObserver() = LiveEventObserver<String> {
    activity?.showOkayDialog(message = it)
}

internal fun Fragment.showSimpleTextDialog(msg: String) {
    activity?.showOkayDialog(message = msg)
}

internal fun Fragment.simpleTextObserver() =
    LiveEventObserver<String> {
        if (canShowDialog()) {
            showSimpleTextDialog(it)
        } else {
            MBLoggerKit.e("Did not show dialog since fragment is not visible.")
            MBLoggerKit.e("Message was: $it")
        }
    }

internal fun Fragment.canShowDialog() = isVisible && isAdded

internal fun Fragment.simpleToastObserver() =
    LiveEventObserver<String> { context?.toast(it) }

internal val Fragment.application: Application
    get() = activity?.application ?: throw IllegalArgumentException("Activity is null!")

internal inline fun <reified T : ViewModel> Fragment.createBasicViewModel(): T {
    return ViewModelProviders.of(this).get(T::class.java)
}

internal inline fun <reified T : AndroidViewModel> Fragment.createAndroidViewModel(): T {
    val app = activity?.application ?: throw IllegalArgumentException("Activity is null!")
    val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(app)
    return ViewModelProviders.of(this, factory).get(T::class.java)
}